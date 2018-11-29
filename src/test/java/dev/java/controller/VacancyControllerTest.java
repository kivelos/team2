package dev.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.java.db.daos.AbstractDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.Requirement;
import dev.java.db.model.Vacancy;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class VacancyControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Autowired
    private VacancyController controller;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        controller.initialize();
    }

    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {
        @Bean
        public VacancyController vacancyController() {
            return new VacancyController();
        }
    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<Vacancy> vacancies = new ArrayList<>();
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        Vacancy vacancy2 = new Vacancy();
        vacancy2.setId(2);
        vacancy2.setPosition("Java Developer");
        vacancy2.setDeveloper(ObjectsFactory.getUser());
        vacancy2.setVacancyState(Vacancy.VacancyState.CLOSE);
        vacancy2.getCandidates().add(ObjectsFactory.getCandidate());
        vacancy2.setExperienceYearsRequire(1F);
        vacancy2.setSalaryInDollarsFrom(700F);
        vacancy2.setSalaryInDollarsTo(1000F);
        Requirement requirement = new Requirement();
        requirement.setName("Nothing + Nothing");
        vacancy2.getRequirements().add(requirement);

        vacancies.add(vacancy1);
        vacancies.add(vacancy2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(vacancies);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(get("/vacancies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].position", is("C++ Developer")))
                .andExpect(jsonPath("$[0].vacancyState", is("OPEN")))
                .andExpect(jsonPath("$[0].experienceYearsRequire", is(1.5)))
                .andExpect(jsonPath("$[0].salaryInDollarsFrom", is(1500.0)))
                .andExpect(jsonPath("$[0].salaryInDollarsTo", is(2000.0)))
                .andExpect(jsonPath("$[0].requirements[0].name", is("Nothing")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].position", is("Java Developer")))
                .andExpect(jsonPath("$[1].vacancyState", is("CLOSE")))
                .andExpect(jsonPath("$[1].experienceYearsRequire", is(1.0)))
                .andExpect(jsonPath("$[1].salaryInDollarsFrom", is(700.0)))
                .andExpect(jsonPath("$[1].salaryInDollarsTo", is(1000.0)))
                .andExpect(jsonPath("$[1].requirements[0].name", is("Nothing + Nothing")));
    }

    @Test
    public void checkExceptionInGetAllEntities() throws Exception {
        controller.setAbstractDao(null);
        mockMvc.perform(get("/vacancies"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
        ;
    }



    @Test
    public void checkOkInCreateEntity() throws Exception {
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(vacancy1)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/vacancies")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(vacancy1))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/vacancy/1"));
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(vacancy1)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/vacancies")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(vacancy1))
        )
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Invalid Input"));
    }

    @Test
    public void checkOkGetEntity() throws Exception {
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(vacancy1);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(get("/vacancy/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.position", is("C++ Developer")))
                .andExpect(jsonPath("$.vacancyState", is("OPEN")))
                .andExpect(jsonPath("$.experienceYearsRequire", is(1.5)))
                .andExpect(jsonPath("$.salaryInDollarsFrom", is(1500.0)))
                .andExpect(jsonPath("$.salaryInDollarsTo", is(2000.0)))
                .andExpect(jsonPath("$.requirements[0].name", is("Nothing")));
    }

    @Test
    public void checkNotFoundGetEntity() throws Exception {
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(vacancy1);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(get("/vacancy/2"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void checkOkInUpdateEntity() throws Exception {
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(vacancy1)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/vacancy/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(vacancy1))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(vacancy1)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/vacancy/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(vacancy1))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkExceptionInUpdateEntity() throws Exception {
        Vacancy vacancy1 = ObjectsFactory.getVacancy();

        controller.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/vacancy/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(vacancy1))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInDeleteEntity() throws Exception {
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        mockMvc.perform(delete("/vacancy/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        mockMvc.perform(delete("/vacancy/2"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void checkSQLExceptionInDeleteEntity() throws Exception {
        controller.setAbstractDao(null);
        mockMvc.perform(delete("/vacancy/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkSQLExceptionInGetEntity() throws Exception {
        controller.setAbstractDao(null);
        mockMvc.perform(get("/vacancy/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInGetCorrespondCandidates() throws Exception {
        Vacancy vacancy = ObjectsFactory.getVacancy();
        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setSurname("Kotovich");
        candidate1.setName("Kot");

        Candidate candidate2 = new Candidate();
        candidate2.setSurname("Murzikova");
        candidate2.setName("Markiza");
        candidate2.setId(2);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate1);
        candidates.add(candidate2);

        vacancy.setCandidates(candidates);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(vacancy);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/vacancy/1/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].surname", is("Kotovich")))
                .andExpect(jsonPath("$[0].name", is("Kot")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].surname", is("Murzikova")))
                .andExpect(jsonPath("$[1].name", is("Markiza")));

    }

    @Test
    public void checkNotFoundInGetCorrespondVacancies() throws Exception {
        Vacancy vacancy = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(vacancy);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/vacancy/2/candidates"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void checkExceptionInGetCorrespondVacancies() throws Exception {
        controller.setAbstractDao(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/vacancy/1/candidates"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));

    }

    @Test
    public void checkOkInUpdateCorrespondVacancies() throws Exception {
        Vacancy vacancy = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(vacancy);

        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setSurname("Kotovich");
        candidate1.setName("Kot");

        Candidate candidate2 = new Candidate();
        candidate2.setSurname("Murzikova");
        candidate2.setName("Markiza");
        candidate2.setId(2);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate1);
        candidates.add(candidate2);

        vacancy.setCandidates(candidates);

        when(daoMock.updateEntity(vacancy)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/vacancy/1/candidates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidates))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInUpdateCorrespondVacancies() throws Exception {
        Vacancy vacancy = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(vacancy);

        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setSurname("Kotovich");
        candidate1.setName("Kot");

        Candidate candidate2 = new Candidate();
        candidate2.setSurname("Murzikova");
        candidate2.setName("Markiza");
        candidate2.setId(2);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate1);
        candidates.add(candidate2);

        vacancy.setCandidates(candidates);

        when(daoMock.updateEntity(vacancy)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/vacancy/2/candidates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidates))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkExceptionInUpdateCorrespondVacancies() throws Exception {
        Vacancy vacancy = ObjectsFactory.getVacancy();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(vacancy);

        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setSurname("Kotovich");
        candidate1.setName("Kot");

        Candidate candidate2 = new Candidate();
        candidate2.setSurname("Murzikova");
        candidate2.setName("Markiza");
        candidate2.setId(2);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate1);
        candidates.add(candidate2);

        vacancy.setCandidates(candidates);

        when(daoMock.updateEntity(vacancy)).thenReturn(true);

        controller.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/vacancy/1/candidates")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidates))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }
}

