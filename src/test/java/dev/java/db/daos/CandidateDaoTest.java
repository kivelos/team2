package dev.java.db.daos;

import dev.java.db.model.Candidate;
import org.junit.Test;

import java.sql.SQLException;

public class CandidateDaoTest {
    private CandidateDao candidateDao;
    private Candidate candidate;

    @Test
    public void getAllEntities() {
    }

    @Test
    public void createEntity() {
    }

    @Test
    public void updateEntity() {
    }

    @Test
    public void getEntityById() {
        try {
            Candidate candidate = candidateDao.getEntityById(6);

        }
        catch (SQLException e) {

        }

    }
}