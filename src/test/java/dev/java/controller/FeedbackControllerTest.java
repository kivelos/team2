/*package dev.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.java.db.daos.AbstractDao;
import dev.java.db.model.*;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class FeedbackControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Autowired
    private FeedbackController controller;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        controller.initialize();
    }

    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {
        @Bean
        public FeedbackController feedbackController() {
            return new FeedbackController();
        }
    }

    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<CandidateFeedback> candidateFeedbacks = new ArrayList<>();
        CVFeedback cvFeedback = ObjectsFactory.getCVFeedback();
        HRFeedback hrFeedback = ObjectsFactory.getHRFeedback();
        DevFeedback devFeedback = ObjectsFactory.getDevFeedback();
        TMFeedback tmFeedback = ObjectsFactory.getTMFeedback();


        candidateFeedbacks.add(cvFeedback);
        candidateFeedbacks.add(hrFeedback);
        candidateFeedbacks.add(devFeedback);
        candidateFeedbacks.add(tmFeedback);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(candidateFeedbacks);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(get("/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].surname", is("Zakrevskiy")))
                .andExpect(jsonPath("$[0].name", is("Evgeniy")))
                .andExpect(jsonPath("$[0].password", is("sjkgksgs")))
                .andExpect(jsonPath("$[0].email", is("zakrevskiy_evgeniy@mail.ru")))
                .andExpect(jsonPath("$[0].state", is("ACTIVE")))
                .andExpect(jsonPath("$[0].userRole.name", is("Developer")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].surname", is("Grudinskiy")))
                .andExpect(jsonPath("$[1].name", is("Pavel")))
                .andExpect(jsonPath("$[1].password", is("lkjsgkkhr")))
                .andExpect(jsonPath("$[1].email", is("pavel)g@mail.ru")))
                .andExpect(jsonPath("$[1].state", is("BLOCKED")))
                .andExpect(jsonPath("$[1].userRole.name", is("Developer")));
    }
    //
//    @Test
//    public void checkExceptionInGetAllEntities() throws Exception {
//        controller.setAbstractDao(null);
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
//                .andExpect(content().string("Server error"));
//        ;
//    }
//
//
//
    @Test
    public void checkOkInCreateEntity() throws Exception {
        CandidateFeedback candidateFeedback = ObjectsFactory.getCVFeedback();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(candidateFeedback)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(candidateFeedback))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/cv_feedback/1"));
    }
//
//    @Test
//    public void checkInvalidInputCreateEntity() throws Exception {
//        User user = ObjectsFactory.getUser();
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.createEntity(user)).thenReturn(false);
//
//        controller.setAbstractDao(daoMock);
//        ObjectMapper objectMapper = new ObjectMapper();
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsBytes(user))
//        )
//                .andExpect(status().isMethodNotAllowed())
//                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
//                .andExpect(content().string("Invalid Input"));
//    }
//
//    @Test
//    public void checkOkGetEntity() throws Exception {
//        User user = ObjectsFactory.getUser();
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.getEntityById(1)).thenReturn(user);
//        controller.setAbstractDao(daoMock);
//
//        mockMvc.perform(get("/user/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.surname", is("Zakrevskiy")))
//                .andExpect(jsonPath("$.name", is("Evgeniy")))
//                .andExpect(jsonPath("$.password", is("sjkgksgs")))
//                .andExpect(jsonPath("$.email", is("zakrevskiy_evgeniy@mail.ru")))
//                .andExpect(jsonPath("$.state", is("ACTIVE")))
//                .andExpect(jsonPath("$.userRole.name", is("Developer")));
//    }
//
//    @Test
//    public void checkNotFoundGetEntity() throws Exception {
//        User user = ObjectsFactory.getUser();
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.getEntityById(1)).thenReturn(user);
//        controller.setAbstractDao(daoMock);
//
//        mockMvc.perform(get("/user/2"))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    public void checkOkInUpdateEntity() throws Exception {
//        User user = ObjectsFactory.getUser();
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.updateEntity(user)).thenReturn(true);
//
//        controller.setAbstractDao(daoMock);
//        ObjectMapper objectMapper = new ObjectMapper();
//        mockMvc.perform(put("/user/1")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsBytes(user))
//        )
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void checkNotFoundInUpdateEntity() throws Exception {
//        User user = ObjectsFactory.getUser();
//
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.updateEntity(user)).thenReturn(false);
//
//        controller.setAbstractDao(daoMock);
//        ObjectMapper objectMapper = new ObjectMapper();
//        mockMvc.perform(put("/user/2")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsBytes(user))
//        )
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void checkExceptionInUpdateEntity() throws Exception {
//        User user = ObjectsFactory.getUser();
//
//        controller.setAbstractDao(null);
//        ObjectMapper objectMapper = new ObjectMapper();
//        mockMvc.perform(put("/user/1")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsBytes(user))
//        )
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
//                .andExpect(content().string("Server error"));
//    }
//
//    @Test
//    public void checkOkInDeleteEntity() throws Exception {
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.deleteEntityById(1)).thenReturn(true);
//
//        controller.setAbstractDao(daoMock);
//        mockMvc.perform(delete("/user/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void checkNotFoundInDeleteEntity() throws Exception {
//        AbstractDao daoMock = mock(AbstractDao.class);
//        when(daoMock.deleteEntityById(1)).thenReturn(false);
//
//        controller.setAbstractDao(daoMock);
//        mockMvc.perform(delete("/user/2"))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    public void checkSQLExceptionInDeleteEntity() throws Exception {
//        controller.setAbstractDao(null);
//        mockMvc.perform(delete("/user/1"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
//                .andExpect(content().string("Server error"));
//    }
//
//    @Test
//    public void checkSQLExceptionInGetEntity() throws Exception {
//        controller.setAbstractDao(null);
//        mockMvc.perform(get("/user/1"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
//                .andExpect(content().string("Server error"));
//    }
}*/