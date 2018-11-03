package dev.java.controller;

import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Candidate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

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
}
