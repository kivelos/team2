package dev.java.controller;

import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Candidate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public final class CandidateController extends AbstractController<Candidate> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        sortedField = "surname";
        url = "/candidate/";
        abstractDao = new CandidateDao(connection);
    }

    @Override
    @GetMapping("/candidates")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/candidates")
    public ResponseEntity createEntity(@RequestBody Candidate candidate, HttpServletRequest request) {
        return super.createEntity(candidate, request);
    }

    @Override
    @GetMapping("/candidate/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/candidate/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody Candidate candidate,
                                          HttpServletRequest request) {
       return super.updateEntity(id, candidate, request);
    }

    @Override
    @DeleteMapping("/candidate/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
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
            modelAndView = getAllVacancies(request);
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
        ModelAndView modelAndView = getVacancy(id, request);
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
            modelAndView = getVacancy(id, request);
            modelAndView.addObject("error", "Name must be filled");
        }
        catch (Exception e) {
            logging.runMe(e);
            modelAndView = new ModelAndView("errors/500");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/candidates/{id:\\d+}", method = RequestMethod.GET)
    public ModelAndView getVacancy(@PathVariable long id, HttpServletRequest request) {
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
