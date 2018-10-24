package dev.java.controller;

import dev.java.Logging;
import dev.java.db.ConnectorDB;
import dev.java.db.daos.AbstractDao;
import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Candidate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AbstractController {
    private Logging logging;

    public ModelAndView getAllEntities(HttpServletRequest request) {
        logging.runMe(request);
        ModelAndView modelAndView = new ModelAndView();
        /*try (Connection connection = ConnectorDB.getConnection()) {
            AbstractDao candidateDao = new Ab(connection);
            List<Candidate> candidates = candidateDao.getSortedEntitiesPage(1, "surname", true, 10);
            modelAndView.addObject("candidates_list", candidates);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return modelAndView;
    }
}
