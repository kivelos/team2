package dev.java.controller1;

import dev.java.db.daos1.VacancyDao;
import dev.java.db.model1.User;
import dev.java.db.model1.Vacancy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class VacancyController extends AbstractController<Vacancy> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("position");
        setUrl("/vacancy/");
        setAbstractDao(new VacancyDao(getSession()));
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
}
