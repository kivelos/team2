package dev.java.controller;

import dev.java.DateProcessor;
import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Candidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Controller
public class HelloController {

    @Value("${application.version}")
    private String buildVersion;

    private Logging logging = new Logging();

    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    public ModelAndView getAllCandidates(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("candidates");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            List<Candidate> candidates = candidateDao.getAllEntities();
            modelAndView.addObject("candidates_list", candidates);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;

    }

    @RequestMapping(value = "/candidates", method = RequestMethod.POST)
    public ModelAndView addCandidate(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView();
        try (Connection connection = ConnectorDB.getConnection()) {
            String surname = request.getParameter("surname").trim();
            String name = request.getParameter("name").trim();
            if (name.equals("")) {
                throw new IllegalArgumentException("Field Name is empty");
            }
            Date date = Date.valueOf(request.getParameter("birthday"));
            Candidate candidate;
            float salaryInDollars = Float.parseFloat(request.getParameter("salary_in_dollars"));
            CandidateDao candidateDao = new CandidateDao(connection);
            candidate = new Candidate(name, surname, date, salaryInDollars);
            candidateDao.createEntity(candidate);
            modelAndView.setViewName("redirect:" + "/candidates/" + candidate.getId());
            return modelAndView;
        } catch (SQLException e) {
            modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return modelAndView;
        }
        catch (Exception e) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

    }

    @RequestMapping(value = "/candidates/{id:\\d+}/edit", method = RequestMethod.GET)
    public ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getCandidate(id, request);
        modelAndView.setViewName("candidate_edit");
        return modelAndView;
    }

    @RequestMapping(value = "/candidates/{id:\\d+}", method = RequestMethod.GET)
    public ModelAndView getCandidate(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("candidate");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            Candidate candidate = candidateDao.getEntityById(id);
            modelAndView.addObject("candidate", candidate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
