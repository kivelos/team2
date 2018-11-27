package dev.java.controller;

import dev.java.db.daos.UserDao;
import dev.java.db.model.User;
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
public class UserController extends AbstractController<User> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("email");
        setUrl("/user/");
        setAbstractDao(new UserDao(getSession()));
    }

    @Override
    @GetMapping("/users")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/users")
    public ResponseEntity createEntity(@RequestBody User user, HttpServletRequest request) {
        return super.createEntity(user, request);
    }

    @Override
    @GetMapping("/user/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/user/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody User user,
                                       HttpServletRequest request) {
        return super.updateEntity(id, user, request);
    }

    @Override
    @DeleteMapping("/user/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }
}
