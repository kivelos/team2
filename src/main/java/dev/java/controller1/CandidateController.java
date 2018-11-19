package dev.java.controller1;

import dev.java.db.daos1.CandidateDao;
import dev.java.db.model1.Candidate;
import dev.java.db.model1.Vacancy;
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
import java.util.List;

@RestController
public class CandidateController extends AbstractController<Candidate> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("surname");
        setUrl("/candidate/");
        setAbstractDao(new CandidateDao(getSession()));
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

    @GetMapping("/candidate/{id:\\d+}/vacancies")
    public ResponseEntity getCorrespondVacancies(@PathVariable long id, HttpServletRequest request) {
        getLogging().runMe(request);
        try {
            Candidate entity = getAbstractDao().getEntityById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(entity.getVacancies());
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PutMapping("/candidate/{id:\\d+}/vacancies")
    public ResponseEntity updateCorrespondVacancies(@PathVariable long id, @RequestBody List<Vacancy> vacancies,
                                                 HttpServletRequest request) {
        getLogging().runMe(request);
        try {
            Candidate entity = getAbstractDao().getEntityById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            entity.setVacancies(vacancies);
            getAbstractDao().updateEntity(entity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }
}
