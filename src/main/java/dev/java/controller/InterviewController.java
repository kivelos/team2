package dev.java.controller1;

import dev.java.db.daos1.InterviewDao;
import dev.java.db.model1.Interview;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class InterviewController extends AbstractController<Interview> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("fact_date");
        setUrl("/interview/");
        setAbstractDao(new InterviewDao(getSession()));
    }

    @Override
    @GetMapping("/interviews")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/interviews")
    public ResponseEntity createEntity(@RequestBody Interview interview, HttpServletRequest request) {
        return super.createEntity(interview, request);
    }


    @Override
    @GetMapping("/interview/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/interview/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody Interview interview,
                                       HttpServletRequest request) {
        return super.updateEntity(id, interview, request);
    }

    @Override
    @DeleteMapping("/interview/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

}
