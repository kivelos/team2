package dev.java.controller;

import dev.java.db.daos.AbstractDao;
import dev.java.db.model.Interview;
import dev.java.db.model.InterviewFeedback;
import dev.java.db.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class InterviewFeedbackControllerTest {

    private InterviewFeedbackController controller;

    @Before
    public void setUp() {
        this.controller = new InterviewFeedbackController();
        this.controller.initialize();
    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<InterviewFeedback> interviewFeedbacks = new ArrayList<>();
        InterviewFeedback interviewFeedback1 = new InterviewFeedback();
        interviewFeedback1.setInterview(new Interview(1));
        interviewFeedback1.setInterviewer(new User(1));
        interviewFeedback1.setReason("OK");
        interviewFeedback1.setFeedbackState("Bad english");

        InterviewFeedback interviewFeedback2 = new InterviewFeedback();
        interviewFeedback1.setInterview(new Interview(2));
        interviewFeedback1.setInterviewer(new User(2));
        interviewFeedback1.setReason("Good knowlenge");
        interviewFeedback1.setFeedbackState("Bad english");

        interviewFeedbacks.add(interviewFeedback1);
        interviewFeedbacks.add(interviewFeedback2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(interviewFeedbacks);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));
        //System.out.println(res);

        Assert.assertEquals(interviewFeedbacks, res.getBody());
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
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(interviewFeedback)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        controller.setUrl("/interviewFeedback/");
        ResponseEntity res = this.controller.createEntity(interviewFeedback, mock(HttpServletRequest.class));
        Assert.assertEquals("/interviewFeedback/1", res.getHeaders().getLocation().toString());
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(interviewFeedback)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        controller.setUrl("/interviewFeedback/");
        ResponseEntity res = this.controller.createEntity(interviewFeedback, mock(HttpServletRequest.class));
        Assert.assertEquals(405, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInCreateEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(interviewFeedback)).thenThrow(new SQLException());

        controller.setAbstractDao(daoMock);
        controller.setUrl("/inteviewFeedback/");
        ResponseEntity res = this.controller.createEntity(interviewFeedback, mock(HttpServletRequest.class));
        Assert.assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInGetEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(interviewFeedback);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));

        Assert.assertEquals(interviewFeedback, res.getBody());
    }

    @Test
    public void checkNotFoundInGetEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(interviewFeedback);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(2, mock(HttpServletRequest.class));

        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInGetEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenThrow(new SQLException());
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getEntity(1, mock(HttpServletRequest.class));

        Assert.assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInUpdateEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(interviewFeedback)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(1, interviewFeedback, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(interviewFeedback)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(2, interviewFeedback, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInUpdateEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(interviewFeedback)).thenThrow(new SQLException());

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.updateEntity(1, interviewFeedback, mock(HttpServletRequest.class));
        Assert.assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    public void checkOkInDeleteEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(interviewFeedback)).thenReturn(true);
        when(daoMock.getEntityById(1)).thenReturn(interviewFeedback);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(interviewFeedback)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(2, mock(HttpServletRequest.class));
        Assert.assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void checkSQLExceptionInDeleteEntity() throws Exception {
        InterviewFeedback interviewFeedback = new InterviewFeedback(new Interview(1), new User(1), "OK", "Bad english");
        interviewFeedback.setId(1);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntity(interviewFeedback)).thenThrow(new SQLException());
        when(daoMock.getEntityById(1)).thenReturn(interviewFeedback);

        controller.setAbstractDao(daoMock);
        ResponseEntity res = this.controller.deleteEntity(1, mock(HttpServletRequest.class));
        Assert.assertEquals(500, res.getStatusCodeValue());
    }

}