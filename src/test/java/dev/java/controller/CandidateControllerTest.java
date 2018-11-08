package dev.java.controller;

import dev.java.db.daos.AbstractDao;
import dev.java.db.model.Candidate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateControllerTest {

    private CandidateController controller;

    @Before
    public void setUp() {
        this.controller = new CandidateController();
        this.controller.initialize();
    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate1 = new Candidate(1);
        candidate1.setSurname("Piliak");
        candidate1.setName("Kseniya");
        candidate1.setBirthday(Date.valueOf("1996-04-06"));
        candidate1.setSalaryInDollars(200);

        Candidate candidate2 = new Candidate(2);
        candidate1.setSurname("Zakrevskiy");
        candidate1.setName("Evgeniy");
        candidate1.setBirthday(Date.valueOf("1998-12-31"));
        candidates.add(candidate1);
        candidates.add(candidate2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(candidates);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));
        //System.out.println(res);

        Assert.assertEquals(candidates, res.getBody());
    }

    @Test
    public void checkSQLExceptionInGetAllEntities() throws Exception {
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenThrow(new SQLException());
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));
        //System.out.println(res);

        Assert.assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInCreateEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(candidate)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        controller.setUrl("/candidate/");
        ResponseEntity res = this.controller.createEntity(candidate, mock(HttpServletRequest.class));
        Assert.assertEquals("/candidate/1", res.getHeaders().getLocation().toString());
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(candidate)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        controller.setUrl("/candidate/");
        ResponseEntity res = this.controller.createEntity(candidate, mock(HttpServletRequest.class));
        Assert.assertEquals(405, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInCreateEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(candidate)).thenThrow(new SQLException());

        controller.setAbstractDao(daoMock);
        controller.setUrl("/candidate/");
        ResponseEntity res = this.controller.createEntity(candidate, mock(HttpServletRequest.class));
        Assert.assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInGetEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(candidate);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));

        Assert.assertEquals(candidate, res.getBody());
    }

    @Test
    public void checkNotFoundInGetEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(candidate);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(2, mock(HttpServletRequest.class));

        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInGetEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenThrow(new SQLException());
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));

        Assert.assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInUpdateEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(candidate)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(1, candidate, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(candidate)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(2, candidate, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInUpdateEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(candidate)).thenThrow(new SQLException());

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(1, candidate, mock(HttpServletRequest.class));
        Assert.assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInDeleteEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(candidate)).thenReturn(true);
        when(daoMock.getEntityById(1)).thenReturn(candidate);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(candidate)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(2, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInDeleteEntity() throws Exception {
        Candidate candidate = new Candidate("Piliak", "Kseniya", Date.valueOf("1996-04-06"), 200);
        candidate.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(candidate)).thenThrow(new SQLException());
        when(daoMock.getEntityById(1)).thenReturn(candidate);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(500, res.getStatusCodeValue());
    }

}
