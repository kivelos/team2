package dev.java.controller;

import dev.java.db.daos.UserDao;
import dev.java.db.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController extends AbstractController<User> {
    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        sortedField = "surname";
        url = "/user/";
        abstractDao = new UserDao(connection);
    }

    @Override
    @GetMapping("/users")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/user")
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
