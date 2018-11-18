package dev.java.controller1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.java.db.daos1.AbstractDao;
import dev.java.db.model1.Candidate;
import dev.java.db.model1.CandidateExperience;
import dev.java.db.model1.CandidateState;
import dev.java.db.model1.Skill;
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

import java.math.BigDecimal;
import java.sql.Date;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class CandidateControllerTest {
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Autowired
    private CandidateController candidateController;

    @Before
    public void setUp() {
        //this.controller = new CandidateController();
        //this.controller.initialize();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        candidateController.initialize();
    }

    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {

        @Bean
        public CandidateController candidateController() {
            return new CandidateController();
        }

    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate1 = getCandidate();

        Candidate candidate2 = new Candidate();
        candidate2.setId(2);
        candidate2.setSurname("Zakrevskiy");
        candidate2.setName("Evgeniy");
        candidate2.setBirthday(Date.valueOf("1998-12-31"));
        CandidateExperience experience = new CandidateExperience();
        experience.setDateFrom(Date.valueOf("2018-11-12"));
        experience.setDateTo(Date.valueOf("2018-11-13"));
        candidate2.getExperiences().add(experience);
        candidates.add(candidate1);
        candidates.add(candidate2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(candidates);
        candidateController.setAbstractDao(daoMock);
        //ResponseEntity res = this.controller.getAllEntities(mock(HttpServletRequest.class));

        //Assert.assertEquals(candidates, res.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].surname", is("Piliak")))
                .andExpect(jsonPath("$[0].name", is("Kseniya")))
                .andExpect(jsonPath("$[0].birthday", is(Date.valueOf("1996-04-06").getTime())))
                .andExpect(jsonPath("$[0].candidateState.name", is("Invited to interview")))
                .andExpect(jsonPath("$[0].salaryInDollars", is(200)))
                .andExpect(jsonPath("$[0].skills[0].name", is("Java")))
                .andExpect(jsonPath("$[0].skills[1].name", is("HELLO")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].surname", is("Zakrevskiy")))
                .andExpect(jsonPath("$[1].name", is("Evgeniy")))
                .andExpect(jsonPath("$[1].birthday", is(Date.valueOf("1998-12-31").getTime())))
                .andExpect(jsonPath("$[1].experiences[0].dateFrom", is(Date.valueOf("2018-11-12").getTime())))
                .andExpect(jsonPath("$[1].experiences[0].dateTo", is(Date.valueOf("2018-11-13").getTime())));
    }

    @Test
    public void checkExceptionInGetAllEntities() throws Exception {
        candidateController.setAbstractDao(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/candidates"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
        ;
    }

    @Test
    public void checkOkInCreateEntity() throws Exception {
        Candidate candidate = getCandidate();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(candidate)).thenReturn(true);

        candidateController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/candidates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidate))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/candidate/1"));
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        Candidate candidate = getCandidate();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(candidate)).thenReturn(false);

        candidateController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/candidates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidate))
        )
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Invalid Input"));
    }

    @Test
    public void checkExceptionInCreateEntity() throws Exception {
        Candidate candidate = getCandidate();

        candidateController.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/candidates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidate))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInGetEntity() throws Exception {
        Candidate candidate = getCandidate();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(candidate);
        candidateController.setAbstractDao(daoMock);

        mockMvc.perform(get("/candidate/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.surname", is("Piliak")))
                .andExpect(jsonPath("$.name", is("Kseniya")))
                .andExpect(jsonPath("$.birthday", is(Date.valueOf("1996-04-06").getTime())))
                .andExpect(jsonPath("$.candidateState.name", is("Invited to interview")))
                .andExpect(jsonPath("$.salaryInDollars", is(200)))
                .andExpect(jsonPath("$.skills[0].name", is("Java")))
                .andExpect(jsonPath("$.skills[1].name", is("HELLO")));
    }

    private Candidate getCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setSurname("Piliak");
        candidate.setName("Kseniya");
        candidate.setBirthday(Date.valueOf("1996-04-06"));
        candidate.setSalaryInDollars(new BigDecimal(200));
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("HELLO");
        candidate.getSkills().add(skill1);
        candidate.getSkills().add(skill2);
        CandidateState candidateState = new CandidateState();
        candidateState.setName("Invited to interview");
        candidate.setCandidateState(candidateState);
        return candidate;
    }

    @Test
    public void checkNotFoundInGetEntity() throws Exception {
        Candidate candidate = getCandidate();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(candidate);
        candidateController.setAbstractDao(daoMock);

        mockMvc.perform(get("/candidate/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkSQLExceptionInGetEntity() throws Exception {
        candidateController.setAbstractDao(null);

        mockMvc.perform(get("/candidate/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInUpdateEntity() throws Exception {
        Candidate candidate = getCandidate();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(candidate)).thenReturn(true);

        candidateController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/candidate/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidate))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        Candidate candidate = getCandidate();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(candidate)).thenReturn(false);

        candidateController.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/candidate/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidate))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkExceptionInUpdateEntity() throws Exception {
        Candidate candidate = getCandidate();

        candidateController.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/candidate/1")
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

        candidateController.setAbstractDao(daoMock);
        mockMvc.perform(delete("/candidate/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(false);

        candidateController.setAbstractDao(daoMock);
        mockMvc.perform(delete("/candidate/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkSQLExceptionInDeleteEntity() throws Exception {
        candidateController.setAbstractDao(null);
        mockMvc.perform(delete("/candidate/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

}
