package dev.java.controller;

import dev.java.db.daos.SkillDao;
import dev.java.db.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class SkillController {
    @Autowired
    private SkillDao skillDao;

    @GetMapping("/skills")
    public ResponseEntity getAllSkills(HttpServletRequest request, HttpServletResponse response) {
        AbstractController.LOGGING.runMe(request);
        List<Skill> allEntities;
        try {
            allEntities = skillDao.getAllSkills();
            return ResponseEntity.ok(allEntities);
        } catch (Exception e) {
            return AbstractController.getResponseEntityOnServerError(e);
        }
    }
}
