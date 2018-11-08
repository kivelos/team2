package dev.java.controller;

import dev.java.db.daos.AbstractDao;
import dev.java.db.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InterviewControllerTest {

    private InterviewController controller;

    private Interview getTestInterview() {
        Interview interview = new Interview();
        interview.setId(1);
        interview.setCandidate(new Candidate("Evgeniy", "Zakrevskiy", Date.valueOf("1998-11-30"), 508));
        interview.setVacancy(new Vacancy("junior", 508, 510, VacancyState.OPEN, 5,
          new User("aaa@bbb.com", "21122112", "gleb", "mashkanov", User.State.ACTIVE )));
        interview.setPlanDate(Timestamp.valueOf("2014-01-01 00:00:00"));
        interview.setFactDate(Timestamp.valueOf("2014-01-02 00:00:00"));
        return interview;
    }

    @Before
    public void setUp() {
        this.controller = new InterviewController();
        this.controller.initialize();
    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<Interview> interviews = new ArrayList<>();
        Interview interview1 = getTestInterview();
        interviews.add(interview1);
        Interview interview2 = new Interview(2);
        interview2.setCandidate(new Candidate("Shpuntik", "mashkanov", Date.valueOf("1998-11-20"), 608));
        interview2.setVacancy(new Vacancy("middle", 608, 610, VacancyState.OPEN, 6,
          new User("bbb@ccc.com", "21122", "gleb", "mashkanov", User.State.ACTIVE )));
        interview2.setPlanDate(Timestamp.valueOf("2015-01-01 00:00:00"));
        interview2.setFactDate(Timestamp.valueOf("2015-01-02 00:00:00"));
        interviews.add(interview2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(interviews);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));
        Assert.assertEquals(interviews, res.getBody());
    }

    @Test
    public void checkOkInCreateEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(interview)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        controller.setUrl("/candidate/");
        ResponseEntity res = this.controller.createEntity(interview, mock(HttpServletRequest.class));
        Assert.assertEquals("/interview/1", res.getHeaders().getLocation().toString());
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(interview)).thenReturn(false);
        controller.setAbstractDao(daoMock);
        controller.setUrl("/interview/");
        ResponseEntity res = this.controller.createEntity(interview, mock(HttpServletRequest.class));
        Assert.assertEquals(405, res.getStatusCodeValue());
    }

    @Test
    public void checkOkGetEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(interview);
        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(interview, res.getBody());
    }

    @Test
    public void checkNotFoundGetEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(interview);
        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.getEntity(2, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInUpdateEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(interview)).thenReturn(true);
        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(1, interview, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(interview)).thenReturn(false);
        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(2, interview, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInDeleteEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(interview)).thenReturn(true);
        when(daoMock.getEntityById(1)).thenReturn(interview);
        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(interview)).thenReturn(false);
        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(2, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInDeleteEntity() throws Exception {
        Interview interview = getTestInterview();
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(interview)).thenThrow(new SQLException());
        when(daoMock.getEntityById(1)).thenReturn(interview);
        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(500, res.getStatusCodeValue());
    }

}
