package dev.java.controller;

import dev.java.db.daos.InterviewFeedbackDao;
import dev.java.db.model.InterviewFeedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class InterviewFeedbackController extends AbstractController<InterviewFeedback> {
    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        sortedField = "feedback_state";
        url = "/interview_feedback/";
        abstractDao = new InterviewFeedbackDao(connection);
    }

    @Override
    @GetMapping("/interview_feedbacks")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/interview_feedbacks")
    public ResponseEntity createEntity(@RequestBody InterviewFeedback interviewFeedback, HttpServletRequest request) {
        return super.createEntity(interviewFeedback, request);
    }

    @Override
    @GetMapping("/interview_feedback/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/interview_feedback/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody InterviewFeedback interviewFeedback,
                                       HttpServletRequest request) {
        return super.updateEntity(id, interviewFeedback, request);
    }

    @Override
    @DeleteMapping("/interview_feedback/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }
}
