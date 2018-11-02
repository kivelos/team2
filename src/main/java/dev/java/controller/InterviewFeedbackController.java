package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.FeedbackStateDao;
import dev.java.db.daos.InterviewDao;
import dev.java.db.daos.InterviewFeedbackDao;
import dev.java.db.daos.UserDao;
import dev.java.db.model.FeedbackState;
import dev.java.db.model.Interview;
import dev.java.db.model.InterviewFeedback;
import dev.java.db.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;

@Controller
public class InterviewFeedbackController {
    private Logging logging = new Logging();
    private static boolean sortType = true;
    private static String sortedField = "id";

    @RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
    public final ModelAndView getAllFeedbacks(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewFeedbackDao interviewFeedbackDao
                    = new InterviewFeedbackDao(connection);
            String sort = request.getParameter("sort");
            boolean sortType = true;
            if (sort != null) {
                sortType = !sort.equals("desc");
            }
            String sortedField = request.getParameter("field");
            if (sortedField == null) {
                sortedField = "id_interview";
            }
            List<InterviewFeedback> interviewFeedbacks
                    = interviewFeedbackDao.getSortedEntitiesPage(1, sortedField,
                    sortType, GeneralConstant.itemsInPage);

            FeedbackStateDao feedbackStateDao = new FeedbackStateDao(connection);
            List<FeedbackState> feedbackStates = feedbackStateDao.getSortedEntitiesPage();
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1,
                    "surname", true, GeneralConstant.filteringItemsInPage);
            InterviewDao interviewDao = new InterviewDao(connection);
            List<Interview> allInterviews = interviewDao.getSortedEntitiesPage(1,
                    "plan_date", true, GeneralConstant.filteringItemsInPage);
            modelAndView = new ModelAndView("interview_feedbacks/interview_feedbacks");
            modelAndView.addObject("states", feedbackStates);
            modelAndView.addObject("feedbacks_list", interviewFeedbacks);
            modelAndView.addObject("users_list", allUsers);
            modelAndView.addObject("interviews_list", allInterviews);
            modelAndView.addObject("page", 1);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

//    @RequestMapping(value = "/vacancies/page/{page:\\d+}", method = RequestMethod.GET)
//    public ModelAndView nextPage(@PathVariable int page, HttpServletRequest request) {
//        ModelAndView modelAndView;
//        logging.runMe(request);
//        try (Connection connection = ConnectorDB.getConnection()) {
//            VacancyDao vacancyDao = new VacancyDao(connection);
//            if (page == 0) {
//                page = 1;
//                modelAndView = new ModelAndView("redirect:/vacancies/page/" + page);
//                return modelAndView;
//            }
//            List<Vacancy> vacancies = vacancyDao.getSortedEntitiesPage(page, sortedField, sortType, itemsInPage);
//            if (vacancies.isEmpty() && page != 1) {
//                page--;
//                modelAndView = new ModelAndView("redirect:/vacancies/page/" + page);
//                return modelAndView;
//            }
//            UserDao userDao = new UserDao(connection);
//            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, 100);
//            VacancyState[] vacanciesStates = VacancyState.values();
//            modelAndView = new ModelAndView("vacancies/vacancies");
//            modelAndView.addObject("vacancies_list", vacancies);
//            modelAndView.addObject("states", vacanciesStates);
//            modelAndView.addObject("users_list", allUsers);
//            modelAndView.addObject("page", page);
//        } catch (Exception e) {
//            logging.runMe(e);
//            modelAndView = new ModelAndView("errors/500");
//        }
//        return modelAndView;
//    }
//
    @RequestMapping(value = "/feedbacks", method = RequestMethod.POST)
    public final ModelAndView addFeedback(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String reason = request.getParameter("reason");
            if (reason == null)
                reason = "";
            else
                reason = reason.trim();
//            reason = reason == null ? "" : reason.trim();
            if (reason.equals("")) {
                throw new IllegalArgumentException("Field reason is empty");
            }
            User developer;
            try {
                long idUser = Long.parseLong(request.getParameter("interviewer"));
                developer = new User(idUser);
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field interviewer is empty");
            }
            Interview interview;
            try {
                long idInterview = Long.parseLong(request.getParameter("interview"));
                interview = new Interview(idInterview);
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field interview is empty");
            }
            String state = request.getParameter("state");
            if (state == null){
                state = "";}
            else{
                state = state.trim();}
//            state = state == null ? "" : state.trim();
            if (state.equals("")) {
                throw new IllegalArgumentException("Field state is empty");
            }
            InterviewFeedbackDao interviewFeedbackDao = new InterviewFeedbackDao(connection);
            InterviewFeedback interviewFeedback = new InterviewFeedback(interview,developer,reason,state);
            interviewFeedbackDao.createEntity(interviewFeedback);
            modelAndView = new ModelAndView("redirect:" + "/feedbacks/" + interviewFeedback.getId());

        } catch (IllegalArgumentException e) {
            modelAndView = getAllFeedbacks(request);
            modelAndView.addObject("error", e.getMessage());
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/feedbacks/{id:\\d+}/edit", method = RequestMethod.GET)
    public final ModelAndView editInterviewFeedback(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getFeedback(id, request);
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewDao interviewDao = new InterviewDao(connection);
            List<Interview> allInterviews = interviewDao.getSortedEntitiesPage(1,"plan_date",true, GeneralConstant.filteringItemsInPage);
            UserDao interviewers = new UserDao(connection);
            List<User> allDevelopers = interviewers.getSortedEntitiesPage(1,"surname",true, GeneralConstant.filteringItemsInPage);
            FeedbackStateDao feedbackStateDao = new FeedbackStateDao(connection);
            List<FeedbackState> allstates = feedbackStateDao.getSortedEntitiesPage();
            modelAndView.addObject("all_interviews", allInterviews);
            modelAndView.addObject("all_developers", allDevelopers);
            modelAndView.addObject("all_states", allstates);
            modelAndView.setViewName("interview_feedbacks/interview_feedback_edit");
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView.setViewName("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/feedbacks/{id:\\d+}/edit", method = RequestMethod.POST)
    public final ModelAndView editInterviewFee(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String reason = request.getParameter("reason");
            reason = reason == null ? "" : reason.trim();
            if (reason.equals("")) {
                throw new IllegalArgumentException("Field reason is empty");
            }
            User developer;
            try {
                long idUser = Long.parseLong(request.getParameter("developer"));
                developer = new User(idUser);
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field interviewer is empty");
            }
            Interview interview;
            try {
                long idInterview = Long.parseLong(request.getParameter("interview"));
                interview = new Interview(idInterview);
            } catch (NumberFormatException | NullPointerException e) {
                throw new IllegalArgumentException("Field interview is empty");
            }
            String state = request.getParameter("state");
            state = state == null ? "" : state.trim();
            if (state.equals("")) {
                throw new IllegalArgumentException("Field state is empty");
            }
            InterviewFeedbackDao interviewFeedbackDao = new InterviewFeedbackDao(connection);
            InterviewFeedback interviewFeedback = new InterviewFeedback(interview, developer, reason, state);
            interviewFeedback.setId(id);
            interviewFeedbackDao.updateEntity(interviewFeedback);
            modelAndView = new ModelAndView("redirect:" + "/feedbacks/" + id);
        } catch (IllegalArgumentException e) {
            modelAndView = getFeedback(id, request);
            modelAndView.addObject("error", "Name must be filled");
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/feedbacks/{id:\\d+}", method = RequestMethod.GET)
    public final ModelAndView getFeedback(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("interview_feedbacks/interview_feedback");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewFeedbackDao interviewFeedbackDao = new InterviewFeedbackDao(connection);
            InterviewFeedback interviewFeedback = interviewFeedbackDao.getEntityById(id);
            modelAndView.addObject("interview_feedback", interviewFeedback);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/feedbacks/filtering", method = RequestMethod.POST)
    public final ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("interview_feedbacks/interview_feedbacks");
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewFeedbackDao interviewFeedbackDao = new InterviewFeedbackDao(connection);
            String reason = request.getParameter("reason").trim();
            String state = request.getParameter("state").trim();
            String developerId = request.getParameter("interviewer");
            String interviewId = request.getParameter("interview");
            System.out.println(developerId);
            List<InterviewFeedback> interviewFeedbackList = interviewFeedbackDao.getFilteredEntitiesPage
                    (interviewId, developerId, reason, state);
            UserDao userDao = new UserDao(connection);
            List<User> allUsers = userDao.getSortedEntitiesPage(1, "surname", true, GeneralConstant.filteringItemsInPage);
            InterviewDao interviewDao = new InterviewDao(connection);
            List<Interview> allInterviews = interviewDao.getSortedEntitiesPage(1,"plan_date",true, GeneralConstant.filteringItemsInPage);
            FeedbackStateDao feedbackStateDao = new FeedbackStateDao(connection);
            List<FeedbackState> feedbackStates = feedbackStateDao.getSortedEntitiesPage();
            modelAndView.addObject("users_list", allUsers);
            modelAndView.addObject("interviews_list", allInterviews);
            modelAndView.addObject("states", feedbackStates);
            modelAndView.addObject("feedbacks_list", interviewFeedbackList);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }
}
