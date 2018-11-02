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
    private static boolean sortType = true;
    private static String sortedField = "surname";

    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    public final ModelAndView getAllCandidates(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView;
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
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(1, sortedField, sortType, GeneralConstant.itemsInPage);
            CandidateStateDao candidateStateDao = new CandidateStateDao(connection);
            List<CandidateState> candidateStates = candidateStateDao.getSortedEntitiesPage();
            modelAndView = new ModelAndView("candidates/candidates");
            modelAndView.addObject("states", candidateStates);
            modelAndView.addObject("candidates_list", candidates);
            modelAndView.addObject("page",1);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/candidates/page/{page:\\d+}", method = RequestMethod.GET)
    public final ModelAndView nextPage(@PathVariable int page, HttpServletRequest request) {
        ModelAndView modelAndView;
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            if (page == 0) {
                page = 1;
                modelAndView = new ModelAndView("redirect:/candidates/page/" + page);
                return modelAndView;
            }
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(page, sortedField, sortType, GeneralConstant.itemsInPage);
            if(candidates.isEmpty() && page != 1) {
                page--;
                modelAndView = new ModelAndView("redirect:/candidates/page/" + page);
                return modelAndView;
                //candidates = candidateDao.getSortedEntitiesPage(page, sortedField,sortType, itemsInPage);
            }
            CandidateStateDao candidateStateDao = new CandidateStateDao(connection);
            List<CandidateState> candidateStates = candidateStateDao.getSortedEntitiesPage();
            modelAndView = new ModelAndView("candidates/candidates");
            modelAndView.addObject("states", candidateStates);
            modelAndView = new ModelAndView("candidates/candidates");
            modelAndView.addObject("candidates_list", candidates);
            modelAndView.addObject("page", page);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/candidates", method = RequestMethod.POST)
    public final ModelAndView addCandidate(HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String surname = request.getParameter("surname");
            surname = surname == null ? "" : surname.trim();
            String name = request.getParameter("name");
            if (name.equals("")) {
                throw new IllegalArgumentException("Field Name is empty");
            }

            Date date;
            try {
                date = Date.valueOf(request.getParameter("birthday"));
            }
            catch (IllegalArgumentException e) {
                date = null;
            }
            float salaryInDollars;
            try {
                salaryInDollars= Float.parseFloat(request.getParameter("salary_in_dollars"));
            }
            catch (IllegalArgumentException e) {
                salaryInDollars = 0;
            }
            CandidateDao candidateDao = new CandidateDao(connection);
            Candidate candidate = new Candidate(name, surname, date, salaryInDollars);
            candidateDao.createEntity(candidate);
            modelAndView = new ModelAndView("redirect:" + "/candidates/" + candidate.getId());
        }
        catch (IllegalArgumentException e) {
            modelAndView = getAllCandidates(request);
            modelAndView.addObject("error", "Name must be filled");
        }
        catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/candidates/{id:\\d+}/edit", method = RequestMethod.GET)
    public final ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = getCandidate(id, request);
        try (Connection connection = ConnectorDB.getConnection()){
            CandidateStateDao candidateStateDao = new CandidateStateDao(connection);
            List<CandidateState> candidateStates = candidateStateDao.getSortedEntitiesPage();
            modelAndView.addObject("states", candidateStates);
            modelAndView.setViewName("candidates/candidate_edit");
        }
        catch (Exception e) {
            logging.runMe(e);
            modelAndView.setViewName("errors/500");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/candidates/{id:\\d+}/edit", method = RequestMethod.POST)
    public final ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        logging.runMe(request);
        ModelAndView modelAndView;
        try (Connection connection = ConnectorDB.getConnection()) {
            String surname = request.getParameter("surname").trim();
            String name = request.getParameter("name").trim();
            if (name.equals("")) {
                throw new IllegalArgumentException("Field Name is empty");
            }
            Date date;
            try {
                date = Date.valueOf(request.getParameter("birthday"));
            }
            catch (IllegalArgumentException e) {
                date = null;
            }
            float salaryInDollars;
            try {
                salaryInDollars = Float.parseFloat(request.getParameter("salary_in_dollars"));
            }
            catch (NumberFormatException | NullPointerException e) {
                salaryInDollars = 0;
            }
            String state = request.getParameter("state");
            CandidateDao candidateDao = new CandidateDao(connection);
            Candidate candidate = new Candidate(name, surname, date, salaryInDollars);
            candidate.setId(id);
            candidate.setCandidateState(state);
            candidateDao.updateEntity(candidate);
            modelAndView = new ModelAndView("redirect:" + "/candidates/" + id);
        } catch (IllegalArgumentException e) {
            modelAndView = getCandidate(id, request);
            modelAndView.addObject("error", "Name must be filled");
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/candidates/{id:\\d+}", method = RequestMethod.GET)
    public final ModelAndView getCandidate(@PathVariable long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("candidates/candidate");
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            Candidate candidate = candidateDao.getEntityById(id);
            modelAndView.addObject("candidate", candidate);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/candidates/filtering", method = RequestMethod.POST)
    public final ModelAndView getFilteredEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView("candidates/candidates");
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            String surname = request.getParameter("surname").trim();
            String name = request.getParameter("name").trim();
            String date = request.getParameter("birthday");
            String salaryInDollars = request.getParameter("salary_in_dollars");
            String candidateState = request.getParameter("state");
            List<Candidate> candidates = candidateDao.getFilteredEntitiesPage(name, surname, date,
                    salaryInDollars, candidateState);
            CandidateStateDao candidateStateDao = new CandidateStateDao(connection);
            List<CandidateState> candidateStates = candidateStateDao.getSortedEntitiesPage();
            modelAndView.addObject("states", candidateStates);
            modelAndView.addObject("candidates_list", candidates);
        } catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }
}
