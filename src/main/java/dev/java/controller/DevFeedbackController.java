package dev.java.controller;

import dev.java.db.daos.DevFeedbackDao;
import dev.java.db.model.DevFeedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class DevFeedbackController extends AbstractController<DevFeedback> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("id");
        setUrl("/dev_feedback/");
        setAbstractDao(new DevFeedbackDao(getSession()));
    }


    @Override
    @GetMapping("/dev_feedback/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/dev_feedback/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody DevFeedback devFeedback,
                                       HttpServletRequest request) {
        return super.updateEntity(id, devFeedback, request);
    }

    @Override
    @DeleteMapping("/dev_feedback/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @Override
    @PostMapping("/dev_feedbacks")
    public ResponseEntity createEntity(@RequestBody DevFeedback devFeedback, HttpServletRequest request) {
        return super.createEntity(devFeedback, request);
    }

    @Override
    @GetMapping("/dev_feedbacks")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

}
