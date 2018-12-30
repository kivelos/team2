package dev.java.controller;

import dev.java.db.daos.VacancyDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VacancyController extends AbstractController<Vacancy> {
    @Autowired
    private VacancyDao vacancyDao;

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("position");
        setUrl("/vacancy/");
        setAbstractDao(vacancyDao);
    }

    @Override
    @GetMapping("/vacancies")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/vacancies")
    public ResponseEntity createEntity(@RequestBody Vacancy vacancy, HttpServletRequest request) {
        return super.createEntity(vacancy, request);
    }

    @Override
    @GetMapping("/vacancy/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/vacancy/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody Vacancy vacancy,
                                       HttpServletRequest request) {
        return super.updateEntity(id, vacancy, request);
    }

    @Override
    @DeleteMapping("/vacancy/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @GetMapping("/vacancy/{id:\\d+}/candidates")
    public ResponseEntity getCorrespondCandidates(@PathVariable long id, HttpServletRequest request) {
        getLogging().runMe(request);
        try {
            Vacancy entity = getAbstractDao().getEntityById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(entity.getCandidates());
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PutMapping("/vacancy/{id:\\d+}/candidates")
    public ResponseEntity updateCorrespondCandidates(@PathVariable long id, @RequestBody List<Candidate> candidates,
                                                 HttpServletRequest request) {
        getLogging().runMe(request);
        try {
            Vacancy entity = getAbstractDao().getEntityById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            entity.setCandidates(candidates);
            getAbstractDao().updateEntity(entity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }
}
