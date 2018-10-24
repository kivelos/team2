package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.CandidateStateDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.CandidateState;
import org.springframework.stereotype.Controller;
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
public class CandidateController {

    private Logging logging = new Logging();

    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    public ModelAndView getAllCandidates(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("candidates/candidates");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            String sort = request.getParameter("sort");
            boolean sortType = true;
            if (sort != null) {
                sortType = !sort.equals("desc");
            }
            String sortedField = request.getParameter("field");
            if (sortedField == null) {
                sortedField = "surname";
            }
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(1, sortedField, sortType, 10);
            CandidateStateDao candidateStateDao = new CandidateStateDao(connection);
            List<CandidateState> candidateStates = candidateStateDao.getSortedEntitiesPage();
            modelAndView.addObject("states", candidateStates);
            modelAndView.addObject("candidates_list", candidates);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;

    }

    @RequestMapping(value = "/candidates", method = RequestMethod.POST)
    public ModelAndView addCandidate(HttpServletRequest request, HttpServletResponse response) {
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
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return modelAndView;
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return modelAndView;
        }

    }

    @RequestMapping(value = "/candidates/{id:\\d+}/edit", method = RequestMethod.GET)
    public ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getCandidate(id, request);
        try (Connection connection = ConnectorDB.getConnection()){
            CandidateStateDao candidateStateDao = new CandidateStateDao(connection);
            List<CandidateState> candidateStates = candidateStateDao.getSortedEntitiesPage();
            modelAndView.addObject("states", candidateStates);
        }
        catch (SQLException e) {

        }
        modelAndView.setViewName("candidates/candidate_edit");

        return modelAndView;
    }

    @RequestMapping(value = "/candidates/{id:\\d+}/edit", method = RequestMethod.POST)
    public ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/candidates/" + id);
        try (Connection connection = ConnectorDB.getConnection()) {
            String surname = request.getParameter("surname").trim();
            String name = request.getParameter("name").trim();
            if (name.equals("")) {
                throw new IllegalArgumentException("Field Name is empty");
            }
            Date date = Date.valueOf(request.getParameter("birthday"));
            float salaryInDollars = Float.parseFloat(request.getParameter("salary_in_dollars"));
            String state = request.getParameter("state");
            CandidateDao candidateDao = new CandidateDao(connection);
            Candidate candidate = new Candidate(name, surname, date, salaryInDollars);
            candidate.setId(id);
            candidate.setCandidateState(state);
            candidateDao.updateEntity(candidate);
            return modelAndView;
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return modelAndView;
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return modelAndView;
        }

    }

    @RequestMapping(value = "/candidates/{id:\\d+}", method = RequestMethod.GET)
    public ModelAndView getCandidate(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("candidates/candidate");
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

    @RequestMapping(value = "/candidates/filtering", method = RequestMethod.POST)
    public ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("candidates/candidates");
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            String surname = request.getParameter("surname").trim();
            String name = request.getParameter("name").trim();
            String date = request.getParameter("birthday");
            String salaryInDollars = request.getParameter("salary_in_dollars");
            String candidateState = request.getParameter("state");

            List<Candidate> candidates = candidateDao.getSortedFilteredEntitiesPage(name, surname, date,
                    salaryInDollars, candidateState);
            CandidateStateDao candidateStateDao = new CandidateStateDao(connection);
            List<CandidateState> candidateStates = candidateStateDao.getSortedEntitiesPage();
            modelAndView.addObject("states", candidateStates);
            modelAndView.addObject("candidates_list", candidates);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
}
