package dev.java.controller;

import dev.java.db.daos.CandidateFeedbackDao;
import dev.java.db.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

public class FeedbackController extends AbstractController<CandidateFeedback> {
    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("id");
        setUrl("/feedbacks/");
        setAbstractDao(new CandidateFeedbackDao(getSession()));
    }

    @Override
    @GetMapping("/feedbacks")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/feedbacks")
    public ResponseEntity createEntity(@RequestBody CandidateFeedback feedback, HttpServletRequest request) {
        return super.createEntity(feedback, request);
    }
}
