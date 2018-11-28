package dev.java.controller;

import dev.java.db.daos.CVFeedbackDao;
import dev.java.db.model.CVFeedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CVFeedbackController extends AbstractController<CVFeedback> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("id");
        setUrl("/cv_feedback/");
        setAbstractDao(new CVFeedbackDao(getSession()));
    }

    @Override
    @GetMapping("/cv_feedback/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/cv_feedback/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody CVFeedback cvFeedback,
                                       HttpServletRequest request) {
        return super.updateEntity(id, cvFeedback, request);
    }

    @Override
    @DeleteMapping("/cv_feedback/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @Override
    @PostMapping("/cv_feedbacks")
    public ResponseEntity createEntity(@RequestBody CVFeedback cvFeedback, HttpServletRequest request) {
        return super.createEntity(cvFeedback, request);
    }

    @Override
    @GetMapping("/cv_feedbacks")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

}
