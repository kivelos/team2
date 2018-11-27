package dev.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.java.db.daos.AbstractDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.Interview;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class InterviewControllerTest {
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Autowired
    private InterviewController interviewController;

    @Before
    public void setUp() {
        //this.controller = new CandidateController();
        //this.controller.initialize();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        interviewController.initialize();
    }

    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {

        @Bean
        public InterviewController interviewController() {
            return new InterviewController();
        }

    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<Interview> interviews = new ArrayList<>();
        Interview interview1 = ObjectsFactory.getInterview();
        Interview interview2 = new Interview();
        interview2.setId(2);
        interview2.setCandidate(ObjectsFactory.getCandidate());
        interview2.setVacancy(ObjectsFactory.getVacancy());
        interview2.setPlanDate(Timestamp.valueOf("2018-11-26 12:00:00"));
        interview2.setFactDate(Timestamp.valueOf("2018-11-26 13:00:00"));
        interviews.add(interview1);
        interviews.add(interview2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(interviews);
        interviewController.setAbstractDao(daoMock);
        //ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));

        //Assert.assertEquals(candidates, res.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/interviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].planDate", is(Timestamp.valueOf("2014-01-01 00:00:00").getTime())))
                .andExpect(jsonPath("$[0].factDate", is(Timestamp.valueOf("2014-01-01 00:00:00").getTime())))
                .andExpect(jsonPath("$[0].vacancy.position", is("C++ Developer")))
                .andExpect(jsonPath("$[0].candidate.name", is("Kseniya")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].planDate", is(Timestamp.valueOf("2018-11-26 12:00:00").getTime())))
                .andExpect(jsonPath("$[1].factDate", is(Timestamp.valueOf("2018-11-26 13:00:00").getTime())))
                .andExpect(jsonPath("$[1].vacancy.position", is("C++ Developer")))
                .andExpect(jsonPath("$[1].candidate.name", is("Kseniya")));
    }

    @Test
    public void checkExceptionInGetAllEntities() throws Exception {
        interviewController.setAbstractDao(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/interviews"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
        ;
    }

    @Test
    public void checkOkInCreateEntity() throws Exception {
        Interview interview = ObjectsFactory.getInterview();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(interview)).thenReturn(true);

        interviewController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/interviews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(interview))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/interview/1"));
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        Interview interview = ObjectsFactory.getInterview();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(interview)).thenReturn(false);

        interviewController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/interviews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(interview))
        )
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Invalid Input"));
    }

    @Test
    public void checkExceptionInCreateEntity() throws Exception {
        Interview interview = ObjectsFactory.getInterview();

        interviewController.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/interviews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(interview))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInGetEntity() throws Exception {
        Interview interview = ObjectsFactory.getInterview();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(interview);
        interviewController.setAbstractDao(daoMock);

        mockMvc.perform(get("/interview/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.planDate", is(Timestamp.valueOf("2014-01-01 00:00:00").getTime())))
                .andExpect(jsonPath("$.factDate", is(Timestamp.valueOf("2014-01-01 00:00:00").getTime())))
                .andExpect(jsonPath("$.vacancy.position", is("C++ Developer")))
                .andExpect(jsonPath("$.candidate.name", is("Kseniya")));
    }



    @Test
    public void checkNotFoundInGetEntity() throws Exception {
        Interview interview = ObjectsFactory.getInterview();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(interview);
        interviewController.setAbstractDao(daoMock);

        mockMvc.perform(get("/candidate/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkSQLExceptionInGetEntity() throws Exception {
        interviewController.setAbstractDao(null);

        mockMvc.perform(get("/interview/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInUpdateEntity() throws Exception {
        Interview interview = ObjectsFactory.getInterview();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(interview)).thenReturn(true);

        interviewController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/interview/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(interview))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        Interview interview = ObjectsFactory.getInterview();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(interview)).thenReturn(false);

        interviewController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/interview/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(interview))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkExceptionInUpdateEntity() throws Exception {
        Candidate candidate = ObjectsFactory.getCandidate();

        interviewController.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/interview/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidate))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInDeleteEntity() throws Exception {
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(true);

        interviewController.setAbstractDao(daoMock);
        mockMvc.perform(delete("/interview/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(false);

        interviewController.setAbstractDao(daoMock);
        mockMvc.perform(delete("/interview/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkSQLExceptionInDeleteEntity() throws Exception {
        interviewController.setAbstractDao(null);
        mockMvc.perform(delete("/interview/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }


}
