package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.InterviewDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.Interview;
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
  static boolean sortType = true;
  static String sortedField = "";
  static final String DEFAULT_SORT_FIELD = "id_vacancy";
  static final String DEFAULT_SORT_DIRECT = "desc";

  @RequestMapping(value = "/interviews", method = RequestMethod.GET)
  public ModelAndView getAllInterviews(HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("interviews/interviews");
    logging.runMe(request);
    try (Connection connection = ConnectorDB.getConnection()) {
      InterviewDao interviewDao = new InterviewDao(connection);
      String sort = request.getParameter("sort");
      if (sort != null) {
        sortType = !sort.equals(DEFAULT_SORT_DIRECT);
      }
      sortedField = request.getParameter("field");
      if (sortedField == null) {
        sortedField = DEFAULT_SORT_FIELD;
      }
      List<Interview> interviews = interviewDao.getSortedEntitiesPage(1, sortedField, sortType,100);
      modelAndView.addObject("interviews_list", interviews);
      modelAndView.addObject("page",1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return modelAndView;
  }


  @RequestMapping(value = "/interviews", method = RequestMethod.POST)
  public ModelAndView addInterview(HttpServletRequest request, HttpServletResponse response) {
    logging.runMe(request);
    ModelAndView modelAndView = new ModelAndView();
    try (Connection dbConn = ConnectorDB.getConnection()) {
      String s_planDate = request.getParameter("planDate").trim();
      String s_factDate = request.getParameter("factDate").trim();
      String s_id_candidate = request.getParameter("id_candidate").trim();
      String s_id_vacancy = request.getParameter("id_vacancy").trim();
      //
      Date planDate = s_planDate.isEmpty() ? null : Date.valueOf(s_planDate);
      Date factDate = s_factDate.isEmpty() ? null : Date.valueOf(s_factDate);
      int candidateId = s_id_candidate.isEmpty() ? 0 : Integer.parseInt(s_id_candidate);
      int vacancyId = s_id_vacancy.isEmpty() ? 0 : Integer.parseInt(s_id_vacancy);
      //String candidateText = request.getParameter("candidate_text");
      //String vacancyText = request.getParameter("candidate_text");
      Interview interview = new Interview(planDate,factDate,candidateId, vacancyId, "","");
      InterviewDao interviewDao = new InterviewDao(dbConn);
      interviewDao.createEntity(interview);
      modelAndView.setViewName("redirect:" + "/interviews");
    }  catch (SQLException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return modelAndView;
    }
    catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return modelAndView;
    }
    return modelAndView;
  }

}
