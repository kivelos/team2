package dev.java.controller;

import dev.java.db.daos.HRFeedbackDao;
import dev.java.db.model.HRFeedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class HRFeedbackController extends AbstractController<HRFeedback> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("id");
        setUrl("/hr_feedback/");
        setAbstractDao(new HRFeedbackDao(getSession()));
    }


    @Override
    @GetMapping("/hr_feedback/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/hr_feedback/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody HRFeedback hrFeedback,
                                       HttpServletRequest request) {
        return super.updateEntity(id, hrFeedback, request);
    }

    @Override
    @DeleteMapping("/hr_feedback/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @Override
    @PostMapping("/hr_feedbacks")
    public ResponseEntity createEntity(@RequestBody HRFeedback hrFeedback, HttpServletRequest request) {
        return super.createEntity(hrFeedback, request);
    }

    @Override
    @GetMapping("/hr_feedbacks")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }
}
