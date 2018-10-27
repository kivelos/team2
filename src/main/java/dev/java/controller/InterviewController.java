package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.InterviewDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.Interview;
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

public class InterviewController {

  private Logging logging = new Logging();
  static boolean sortType = true;
  static String sortedField = "vacancy";

  @RequestMapping(value = "/interviews", method = RequestMethod.GET)
  public ModelAndView getAllInterviews(HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("interviews/interviews");
    logging.runMe(request);
    try (Connection connection = ConnectorDB.getConnection()) {
      InterviewDao interviewDao = new InterviewDao(connection);
      /*String sort = request.getParameter("sort");
      if (sort != null) {
        sortType = !sort.equals("desc");
      }
      sortedField = request.getParameter("field");
      if (sortedField == null) {
        sortedField = "vacancy";
      }*/
      List<Interview> interviews = interviewDao.getSortedEntitiesPage(1, sortedField, sortType,3);
      modelAndView.addObject("interviews_list", interviews);
      modelAndView.addObject("page",1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return modelAndView;
  }

  /*
  @RequestMapping(value = "/interviews", method = RequestMethod.POST)
  public ModelAndView addInterview(HttpServletRequest request, HttpServletResponse response) {
    logging.runMe(request);
    ModelAndView modelAndView = new ModelAndView();
    try (Connection connection = ConnectorDB.getConnection()) {
      Date planDate = Date.valueOf(request.getParameter("planDate"));
      Date factDate = Date.valueOf(request.getParameter("factDate"));
      Candidate candidate = new Candidate();

      InterviewDao interviewDao = new InterviewDao(connection);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return modelAndView;
  }*/

}
