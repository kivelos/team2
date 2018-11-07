package dev.java.controller;

import dev.java.db.daos.AbstractDao;
import dev.java.db.model.Candidate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
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

    }

    @Test
    public void helloWorld() throws Exception {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate());
        candidates.add(new Candidate());

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(candidates);
        controller.setAbstractDao(daoMock);

        ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));
        System.out.println(res);

        Assert.assertEquals(candidates, res.getBody());
    }


}
