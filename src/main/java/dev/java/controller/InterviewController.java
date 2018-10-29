package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.CandidateStateDao;
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
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

@Controller
public class InterviewController {
    private Logging logging = new Logging();
    private static boolean sortType = true;
    private static String sortedField = "plan_date";
    private static int itemsInPage = 3;

    @RequestMapping(value = "/interviews", method = RequestMethod.GET)
    public ModelAndView getAllVacancies(HttpServletRequest request) {
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
            List<Interview> interviews = interviewDao.getSortedEntitiesPage(1, sortedField, sortType, itemsInPage);
            CandidateDao candidateDao = new CandidateDao(connection);
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(1, "surname", true, 100);
            VacancyDao vacancyDao = new VacancyDao(connection);
            List<Vacancy> vacancies = vacancyDao.getSortedEntitiesPage(1, "position", true, 100);
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
    public ModelAndView nextPage(@PathVariable int page, HttpServletRequest request) {
        ModelAndView modelAndView;
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewDao interviewDao = new InterviewDao(connection);
            if (page == 0) {
                page = 1;
                modelAndView = new ModelAndView("redirect:/interviews/page/" + page);
                return modelAndView;
            }
            List<Interview> interviews = interviewDao.getSortedEntitiesPage(page, sortedField, sortType, itemsInPage);
            if(interviews.isEmpty() && page != 1) {
                page--;
                modelAndView = new ModelAndView("redirect:/interviews/page/" + page);
                return modelAndView;
            }
            CandidateDao candidateDao = new CandidateDao(connection);
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(1, "surname", true, 100);
            VacancyDao vacancyDao = new VacancyDao(connection);
            List<Vacancy> vacancies = vacancyDao.getSortedEntitiesPage(1, "position", true, 100);
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

    /*@RequestMapping(value = "/vacancies", method = RequestMethod.POST)
    public ModelAndView addCandidate(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String position = request.getParameter("position");
            position = position == null ? "" : position.trim();
            if (position.equals("")) {
                throw new IllegalArgumentException("Field Position is empty");
            }
            float salaryInDollarsFrom;
            try {
                salaryInDollarsFrom= Float.parseFloat(request.getParameter("salary_in_dollars_from"));
            }
            catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsFrom = 0;
            }
            float salaryInDollarsTo;
            try {
                salaryInDollarsTo= Float.parseFloat(request.getParameter("salary_in_dollars_to"));
            }
            catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsTo = 0;
            }
            VacancyState vacancyState;
            try {
                vacancyState = VacancyState.valueOf(request.getParameter("state"));
            }
            catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            float experienceYearsRequire;
            try {
                experienceYearsRequire= Float.parseFloat(request.getParameter("experience_years_require"));
            }
            catch (NumberFormatException | NullPointerException e) {
                experienceYearsRequire = 0;
            }
            User developer;
            try {
                long idUser = Long.parseLong(request.getParameter("developer"));
                developer = new User(idUser);
            }
            catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = new Vacancy(position, salaryInDollarsFrom, salaryInDollarsTo,
                    vacancyState, experienceYearsRequire, developer);
            vacancyDao.createEntity(vacancy);
            modelAndView = new ModelAndView("redirect:" + "/vacancies/" + vacancy.getId());
        }
        catch (IllegalArgumentException e) {
            modelAndView = getAllVacancies(request);
            modelAndView.addObject("error", e.getMessage());
        }
        catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/{id:\\d+}/edit", method = RequestMethod.GET)
    public ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getCandidate(id, request);
        try (Connection connection = ConnectorDB.getConnection()) {
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, 100);
            VacancyState[] vacancyStates = VacancyState.values();
            modelAndView.addObject("states", vacancyStates);
            modelAndView.addObject("users", allUsers);
            modelAndView.setViewName("vacancies/vacancy_edit");
        }
        catch (Exception e) {
            logging.runMe(e);
            modelAndView.setViewName("errors/500");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/{id:\\d+}/edit", method = RequestMethod.POST)
    public ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String position = request.getParameter("position");
            position = position == null ? "" : position.trim();
            if (position.equals("")) {
                throw new IllegalArgumentException("Field Position is empty");
            }
            float salaryInDollarsFrom;
            try {
                salaryInDollarsFrom= Float.parseFloat(request.getParameter("salary_in_dollars_from"));
            }
            catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsFrom = 0;
            }
            float salaryInDollarsTo;
            try {
                salaryInDollarsTo= Float.parseFloat(request.getParameter("salary_in_dollars_to"));
            }
            catch (NumberFormatException | NullPointerException e) {
                salaryInDollarsTo = 0;
            }
            VacancyState vacancyState;
            try {
                vacancyState = VacancyState.valueOf(request.getParameter("state"));
            }
            catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            float experienceYearsRequire;
            try {
                experienceYearsRequire= Float.parseFloat(request.getParameter("experience_years_require"));
            }
            catch (NumberFormatException | NullPointerException e) {
                experienceYearsRequire = 0;
            }

            User developer;
            try {
                long idUser = Long.parseLong(request.getParameter("developer"));
                developer = new User(idUser);
            }
            catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field State is empty");
            }
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = new Vacancy(position, salaryInDollarsFrom, salaryInDollarsTo,
                    vacancyState, experienceYearsRequire, developer);
            vacancy.setId(id);
            vacancyDao.updateEntity(vacancy);
            modelAndView = new ModelAndView("redirect:" + "/vacancies/" + id);
        } catch (IllegalArgumentException e) {
            modelAndView = getCandidate(id, request);
            modelAndView.addObject("error", "Name must be filled");
        }
        catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/{id:\\d+}", method = RequestMethod.GET)
    public ModelAndView getCandidate(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("vacancies/vacancy");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            Vacancy vacancy = vacancyDao.getEntityById(id);
            UserDao userDao = new UserDao(connection);
            User user = userDao.getEntityById(vacancy.getDeveloper().getId());
            vacancy.setDeveloper(user);
            modelAndView.addObject("vacancy", vacancy);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/vacancies/filtering", method = RequestMethod.POST)
    public ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("vacancies/vacancies");
        try (Connection connection = ConnectorDB.getConnection()) {
            VacancyDao vacancyDao = new VacancyDao(connection);
            String position = request.getParameter("position").trim();
            String salaryInDollarsFrom = request.getParameter("salary_in_dollars_from").trim();
            String salaryInDollarsTo = request.getParameter("salary_in_dollars_to").trim();
            String vacancyState = request.getParameter("state").trim();
            String experienceYearsRequire = request.getParameter("experience_years_require");
            String developerId = request.getParameter("developer");
            System.out.println(developerId);
            List<Vacancy> vacancies = vacancyDao.getFilteredEntitiesPage(position, salaryInDollarsFrom,
                    salaryInDollarsTo, vacancyState, experienceYearsRequire, developerId);
            VacancyState[] vacancyStates = VacancyState.values();
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, 100);
            modelAndView.addObject("users_list", allUsers);
            modelAndView.addObject("states", vacancyStates);
            modelAndView.addObject("vacancies_list", vacancies);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }*/
}
