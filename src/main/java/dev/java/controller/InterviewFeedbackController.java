package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.InterviewFeedbackDao;
import dev.java.db.model.InterviewFeedback;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Controller
public class InterviewFeedbackController {

    private Logging logging = new Logging();
    static boolean sortType = true;
    static String sortedField = "vacancy";

    @RequestMapping(value = "/interviews_feedback", method = RequestMethod.GET)
    public ModelAndView getAllInterviews(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("interviewsfeedback/interviews_feedback");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            InterviewFeedbackDao interviewFeedbackDao = new InterviewFeedbackDao(connection);
            String sort = request.getParameter("sort");
            if (sort != null) {
                sortType = !sort.equals("desc");
            }
            sortedField = request.getParameter("field");
            if (sortedField == null) {
                sortedField = "surname";
            }
            List<InterviewFeedback> users = interviewFeedbackDao.getSortedEntitiesPage(1,sortedField,sortType,3);
            modelAndView.addObject("interviews_feedback_list", users);
            modelAndView.addObject("page",1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

}