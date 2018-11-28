package dev.java.controller;

import dev.java.db.daos.TMFeedbackDao;
import dev.java.db.model.TMFeedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class TMFeedbackController extends AbstractController<TMFeedback> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("id");
        setUrl("/tm_feedback/");
        setAbstractDao(new TMFeedbackDao(getSession()));
    }


    @Override
    @GetMapping("/tm_feedback/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/tm_feedback/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody TMFeedback tmFeedback,
                                       HttpServletRequest request) {
        return super.updateEntity(id, tmFeedback, request);
    }

    @Override
    @DeleteMapping("/tm_feedback/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @Override
    @PostMapping("/tm_feedbacks")
    public ResponseEntity createEntity(@RequestBody TMFeedback tmFeedback, HttpServletRequest request) {
        return super.createEntity(tmFeedback, request);
    }

    @Override
    @GetMapping("/tm_feedbacks")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }
}
