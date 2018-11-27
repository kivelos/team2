package dev.java.controller;

import dev.java.db.daos.CandidateFeedbackDao;
import dev.java.db.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

public class FeedbackContoller extends AbstractController<CandidateFeedback> {
    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("id");
        setUrl("/feedback/");
        setAbstractDao(new CandidateFeedbackDao(getSession()));
    }

    @GetMapping("/cv_feedbacks")
    public ResponseEntity getAllCVFeedback(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @GetMapping("/dev_feedbacks")
    public ResponseEntity getAllDevFeedback(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @GetMapping("/hr_feedbacks")
    public ResponseEntity getAllHRFeedback(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @GetMapping("/tm_feedbacks")
    public ResponseEntity getAllTMFeedback(HttpServletRequest request) {
        return super.getAllEntities(request);
    }



    @PostMapping("/cv_feedbacks")
    public ResponseEntity createCVFeedback(@RequestBody CVFeedback feedback, HttpServletRequest request) {
        return super.createEntity(feedback, request);
    }

    @PostMapping("/dev_feedbacks")
    public ResponseEntity createDevFeedback(@RequestBody DevFeedback feedback, HttpServletRequest request) {
        return super.createEntity(feedback, request);
    }

    @PostMapping("/hr_feedbacks")
    public ResponseEntity createHRFeedback(@RequestBody HRFeedback feedback, HttpServletRequest request) {
        return super.createEntity(feedback, request);
    }

    @PostMapping("/tm_feedbacks")
    public ResponseEntity createTMFeedback(@RequestBody TMFeedback feedback, HttpServletRequest request) {
        return super.createEntity(feedback, request);
    }


    @GetMapping("/cv_feedback/{id:\\d+}")
    public ResponseEntity getCVFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @GetMapping("/dev_feedback/{id:\\d+}")
    public ResponseEntity getDevFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @GetMapping("/hr_feedback/{id:\\d+}")
    public ResponseEntity getHRFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @GetMapping("/tm_feedback/{id:\\d+}")
    public ResponseEntity getTMFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }


    @PutMapping("/cv_feedback/{id:\\d+}")
    public ResponseEntity updateCVFeedback(@PathVariable long id, @RequestBody CVFeedback feedback,
                                       HttpServletRequest request) {
        return super.updateEntity(id, feedback, request);
    }
    @PutMapping("/dev_feedback/{id:\\d+}")
    public ResponseEntity updateDevFeedback(@PathVariable long id, @RequestBody DevFeedback feedback,
                                           HttpServletRequest request) {
        return super.updateEntity(id, feedback, request);
    }

    @PutMapping("/hr_feedback/{id:\\d+}")
    public ResponseEntity updateHRFeedback(@PathVariable long id, @RequestBody HRFeedback feedback,
                                           HttpServletRequest request) {
        return super.updateEntity(id, feedback, request);
    }

    @PutMapping("/tm_feedback/{id:\\d+}")
    public ResponseEntity updateTMFeedback(@PathVariable long id, @RequestBody TMFeedback feedback,
                                           HttpServletRequest request) {
        return super.updateEntity(id, feedback, request);
    }

    @DeleteMapping("/cv_feedback/{id:\\d+}")
    public ResponseEntity deleteCVFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @DeleteMapping("/dev_feedback/{id:\\d+}")
    public ResponseEntity deleteDevFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @DeleteMapping("/hr_feedback/{id:\\d+}")
    public ResponseEntity deleteHRFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @DeleteMapping("/tm_feedback/{id:\\d+}")
    public ResponseEntity deleteTMFeedback(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

}
