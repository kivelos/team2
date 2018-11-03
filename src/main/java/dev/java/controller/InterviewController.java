package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.InterviewDao;
import dev.java.db.daos.VacancyDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.Interview;
import dev.java.db.model.Vacancy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class InterviewController {
    private Logging logging = new Logging();
    private static boolean sortType = true;
    private static String sortedField = "plan_date";

    @RequestMapping(value = "/interviews", method = RequestMethod.GET)
    public final ModelAndView getAllInterviews(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewDao interviewDao = new InterviewDao(connection);
            String sort = request.getParameter("sort");
            boolean sortType = true;
            if (sort != null) {
                sortType = !sort.equals("desc");
            }
            String sortedField = request.getParameter("field");
            if (sortedField == null) {
                sortedField = "plan_date";
            }
            List<Interview> interviews = interviewDao.getSortedEntitiesPage(1, sortedField, sortType,GeneralConstant.itemsInPage);
            CandidateDao candidateDao = new CandidateDao(connection);
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(1, "surname", true, GeneralConstant.filteringItemsInPage);
            VacancyDao vacancyDao = new VacancyDao(connection);
            List<Vacancy> vacancies = vacancyDao.getSortedEntitiesPage(1, "position", true, GeneralConstant.filteringItemsInPage);
            modelAndView = new ModelAndView("interviews/interviews");
            modelAndView.addObject("interviews_list", interviews);
            modelAndView.addObject("candidates_list", candidates);
            modelAndView.addObject("vacancies_list", vacancies);
            modelAndView.addObject("page",1);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/interviews/page/{page:\\d+}", method = RequestMethod.GET)
    public final ModelAndView nextPage(@PathVariable int page,
                                 HttpServletRequest request) {
        ModelAndView modelAndView;
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewDao interviewDao = new InterviewDao(connection);
            if (page == 0) {
                page = 1;
                modelAndView = new ModelAndView("redirect:/interviews/page/"
                        + page);
                return modelAndView;
            }
            List<Interview> interviews = interviewDao.getSortedEntitiesPage(page,
                    sortedField, sortType, GeneralConstant.itemsInPage);
            if (interviews.isEmpty() && page != 1) {
                page--;
                modelAndView = new ModelAndView("redirect:/interviews/page/"
                        + page);
                return modelAndView;
            }
            CandidateDao candidateDao = new CandidateDao(connection);
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(1,
                    "surname", true, GeneralConstant.filteringItemsInPage);
            VacancyDao vacancyDao = new VacancyDao(connection);
            List<Vacancy> vacancies = vacancyDao.getSortedEntitiesPage(1,
                    "position", true, GeneralConstant.filteringItemsInPage);
            modelAndView = new ModelAndView("interviews/interviews");
            modelAndView.addObject("interviews_list", interviews);
            modelAndView.addObject("candidates_list", candidates);
            modelAndView.addObject("vacancies_list", vacancies);
            modelAndView.addObject("page",1);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/interviews", method = RequestMethod.POST)
    public final ModelAndView addInterview(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            Timestamp planDate;
            try {
                String datetime = request.getParameter("plan_date");
                datetime = datetime.replace('T', ' ') + ":00";
                planDate = Timestamp.valueOf(datetime);
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Field Plan_date is empty");
            }
            Timestamp factDate;
            try {
                String datetime = request.getParameter("fact_date");
                datetime = datetime.replace('T', ' ') + ":00";
                factDate = Timestamp.valueOf(datetime);
            }
            catch (IllegalArgumentException e) {
                factDate = null;
            }
            Candidate candidate;
            try {
                long idCandidate = Long.parseLong(request.getParameter("candidate"));
                candidate = new Candidate(idCandidate);
            }
            catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field candidate is empty");
            }
            Vacancy vacancy;
            try {
                long idVacancy = Long.parseLong(request.getParameter("vacancy"));
                vacancy = new Vacancy(idVacancy);
            }
            catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field candidate is empty");
            }
            InterviewDao interviewDao = new InterviewDao(connection);
            Interview interview = new Interview(candidate, vacancy, planDate, factDate);
            interviewDao.createEntity(interview);
            modelAndView = new ModelAndView("redirect:" + "/interviews/" + interview.getId());
        } catch (IllegalArgumentException e) {
            modelAndView = getAllInterviews(request);
            modelAndView.addObject("error", e.getMessage());
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/interviews/{id:\\d+}/edit", method = RequestMethod.GET)
    public final ModelAndView editInterview(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("interviews/interview_edit");
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewDao interviewDao = new InterviewDao(connection);
            Interview interview = interviewDao.getEntityById(id);
            CandidateDao candidateDao = new CandidateDao(connection);
            List<Candidate> allCandidates = candidateDao.getSortedEntitiesPage(1, "surname", true, 100);
            VacancyDao vacancyDao = new VacancyDao(connection);
            List<Vacancy> allVacancies = vacancyDao.getSortedEntitiesPage(1, "position", true, 100);
            modelAndView.addObject("candidates", allCandidates);
            modelAndView.addObject("vacancies", allVacancies);
            modelAndView.addObject("interview", interview);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView.setViewName("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/interviews/{id:\\d+}/edit", method = RequestMethod.POST)
    public final ModelAndView editInterview(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            Timestamp planDate;
            try {
                String datetime = request.getParameter("plan_date");
                datetime = datetime.replace('T', ' ') + ":00";
                planDate = Timestamp.valueOf(datetime);
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Field Plan_date is empty");
            }
            Timestamp factDate;
            try {
                String datetime = request.getParameter("fact_date");
                datetime = datetime.replace('T', ' ') + ":00";
                factDate = Timestamp.valueOf(datetime);
            }
            catch (IllegalArgumentException e) {
                factDate = null;
            }
            Candidate candidate;
            try {
                long idCandidate = Long.parseLong(request.getParameter("candidate"));
                candidate = new Candidate(idCandidate);
            }
            catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field candidate is empty");
            }
            Vacancy vacancy;
            try {
                long idVacancy = Long.parseLong(request.getParameter("vacancy"));
                vacancy = new Vacancy(idVacancy);
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field candidate is empty");
            }
            InterviewDao interviewDao = new InterviewDao(connection);
            Interview interview = new Interview(candidate, vacancy, planDate, factDate);
            interview.setId(id);
            interviewDao.updateEntity(interview);
            modelAndView = new ModelAndView("redirect:" + "/interviews/" + id);
        } catch (IllegalArgumentException e) {
            modelAndView = getInterview(id, request);
            modelAndView.addObject("error", "Name must be filled");
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/interviews/{id:\\d+}", method = RequestMethod.GET)
    public final ModelAndView getInterview(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("interviews/interview");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewDao interviewDao = new InterviewDao(connection);
            Interview interview = interviewDao.getEntityById(id);
            CandidateDao candidateDao = new CandidateDao(connection);
            Candidate candidate = candidateDao.getEntityById(interview.getCandidate().getId());
            interview.setCandidate(candidate);
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = vacancyDao.getEntityById(interview.getVacancy().getId());
            interview.setVacancy(vacancy);
            modelAndView.addObject("interview", interview);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/interviews/filtering", method = RequestMethod.POST)
    public ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("interviews/interviews");
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewDao interviewDao = new InterviewDao(connection);
            String planDate = request.getParameter("plan_date").trim();

            String factDate =  request.getParameter("fact_date").trim();

            String candidateId = request.getParameter("candidate").trim();
            System.out.println(candidateId);
            String vacancyId = request.getParameter("vacancy").trim();
            System.out.println(vacancyId);
            List<Interview> interviews = interviewDao.getFilteredEntitiesPage(candidateId,vacancyId,planDate,factDate);
            CandidateDao candidateDao = new CandidateDao(connection);
            List<Candidate> allCandidates = candidateDao.getSortedEntitiesPage(1,"surname",true,100);
            modelAndView.addObject("candidates_list",allCandidates);
            VacancyDao vacancyDao = new VacancyDao(connection);
            List<Vacancy> allVacancies = vacancyDao.getSortedEntitiesPage(1,"position",true,100);
            modelAndView.addObject("vacancies_list",allVacancies);
            modelAndView.addObject("interviews_list",interviews);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }
}
