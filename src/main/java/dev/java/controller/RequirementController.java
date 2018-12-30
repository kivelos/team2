package dev.java.controller;

import dev.java.db.daos.RequirementDao;
import dev.java.db.model.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class RequirementController {
    @Autowired
    private RequirementDao requirementDao;

    @GetMapping("/requirements")
    public ResponseEntity getAllRequirements(HttpServletRequest request, HttpServletResponse response) {
        AbstractController.LOGGING.runMe(request);
        List<Requirement> allEntities;
        try {
            allEntities = requirementDao.getAllRequirements();
            return ResponseEntity.ok(allEntities);
        } catch (Exception e) {
            return AbstractController.getResponseEntityOnServerError(e);
        }
    }
}
