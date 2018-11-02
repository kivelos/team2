package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Candidate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
public final class CandidateController {

    private final Logging logging = new Logging();
    private final boolean sortType = true;
    private static String sortedField = "surname";
    private static int itemsInPage = 3;

    private static CandidateDao candidateDao;
    private static Connection connection;

    @PostConstruct
    public void initialize() {
        try {
            connection = ConnectorDB.getConnection();
            candidateDao = new CandidateDao(connection);
        } catch (SQLException e) {
            logging.runMe(e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            logging.runMe(e);
        }
    }

    @GetMapping("/candidates")
    public ResponseEntity getAllCandidates(HttpServletRequest request) {
        logging.runMe(request);
        List<Candidate> allCandidates;
        try {
            allCandidates = candidateDao.getSortedEntitiesPage(1, sortedField, true, itemsInPage);
            return ResponseEntity.ok(allCandidates);
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PostMapping("/candidates")
    public ResponseEntity createCandidate(@RequestBody Candidate candidate, HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (candidateDao.createEntity(candidate)) {
                return ResponseEntity.created(new URI("/candidate/" + candidate.getId()))
                        .body("Created");
            }
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Invalid Input");
        } catch (SQLException | URISyntaxException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @GetMapping("/candidate/{id:\\d+}")
    public ResponseEntity getCandidate(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        try {
            Candidate candidate = candidateDao.getEntityById(id);
            if (candidate == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(candidate);
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PutMapping("/candidate/{id:\\d+}")
    public ResponseEntity updateCandidate(@PathVariable long id, @RequestBody Candidate candidate,
                                          HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (candidateDao.updateEntity(candidate)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @DeleteMapping("/candidate/{id:\\d+}")
    public ResponseEntity deleteCandidate(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (candidateDao.deleteEntity(new Candidate(id))) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return getResponseEntityOnServerError(e);
        }
    }

    private ResponseEntity getResponseEntityOnServerError(Exception e) {
        logging.runMe(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
    }



    /*@RequestMapping(value = "/candidates/page/{page:\\d+}", method = RequestMethod.GET)
    public ModelAndView nextPage(@PathVariable int page, HttpServletRequest request) {
        ModelAndView modelAndView;
        logging.runMe(request);
        try (Connection connection = ConnectorDB.getConnection()) {
            CandidateDao candidateDao = new CandidateDao(connection);
            if (page == 0) {
                page = 1;
                modelAndView = new ModelAndView("redirect:/candidates/page/" + page);
                return modelAndView;
            }
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(page, sortedField, sortType, itemsInPage);
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
    public ModelAndView addCandidate(HttpServletRequest request, HttpServletResponse response) {
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
    public ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request) {
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
    public ModelAndView editCandidate(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
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
        }
        catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/candidates/{id:\\d+}", method = RequestMethod.GET)
    public ModelAndView getCandidate(@PathVariable long id, HttpServletRequest request) {
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
    }*/
}
