package dev.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.java.db.daos.AbstractDao;
import dev.java.db.model.CVFeedback;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class CVFeedbackControllerTest {
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Autowired
    private CVFeedbackController controller;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        controller.initialize();
    }

    @Configuration
    @EnableWebMvc
    public static class TestConfiguration {

        @Bean
        public CVFeedbackController cvFeedbackController() {
            return new CVFeedbackController();
        }

    }


    @Test
    public void checkOkInGetAllEntities() throws Exception {
        List<CVFeedback> cvFeedbacks = new ArrayList<>();
        CVFeedback cvFeedback1 = ObjectsFactory.getCVFeedback();
        CVFeedback cvFeedback2 = ObjectsFactory.getAnotherCVFeedback();

        cvFeedbacks.add(cvFeedback1);
        cvFeedbacks.add(cvFeedback2);

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getSortedEntitiesPage(anyInt(), anyString(), anyBoolean(), anyInt())).thenReturn(cvFeedbacks);
        controller.setAbstractDao(daoMock);


        mockMvc.perform(MockMvcRequestBuilders.get("/cv_feedbacks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].candidate.surname", is("Piliak")))
                .andExpect(jsonPath("$[0].feedbackState.name", is("Accepted")))
                .andExpect(jsonPath("$[0].feedbackText", is("Very hard-working!")))
                .andExpect(jsonPath("$[0].interviewer.surname", is("Zakrevskiy")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].candidate.surname", is("Grudinskiy")))
                .andExpect(jsonPath("$[1].feedbackState.name", is("Denied")))
                .andExpect(jsonPath("$[1].feedbackText", is("Very nice!")))
                .andExpect(jsonPath("$[1].interviewer.surname", is("Kivel")));
    }

    @Test
    public void checkExceptionInGetAllEntities() throws Exception {
        controller.setAbstractDao(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/cv_feedbacks"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
        ;
    }

    @Test
    public void checkOkInCreateEntity() throws Exception {
        CVFeedback cvFeedback = ObjectsFactory.getCVFeedback();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(cvFeedback)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/cv_feedbacks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(cvFeedback))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/cv_feedback/1"));
    }

    @Test
    public void checkInvalidInputCreateEntity() throws Exception {
        CVFeedback cvFeedback = ObjectsFactory.getCVFeedback();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.createEntity(cvFeedback)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/cv_feedbacks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(cvFeedback))
        )
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Invalid Input"));
    }

    @Test
    public void checkExceptionInCreateEntity() throws Exception {
        CVFeedback cvFeedback = ObjectsFactory.getCVFeedback();

        controller.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/cv_feedbacks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(cvFeedback))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }

    @Test
    public void checkOkInGetEntity() throws Exception {
        CVFeedback cvFeedback = ObjectsFactory.getCVFeedback();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(cvFeedback);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(get("/cv_feedback/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.candidate.surname", is("Piliak")))
                .andExpect(jsonPath("$.feedbackState.name", is("Accepted")))
                .andExpect(jsonPath("$.feedbackText", is("Very hard-working!")))
                .andExpect(jsonPath("$.interviewer.surname", is("Zakrevskiy")));
    }



    @Test
    public void checkNotFoundInGetEntity() throws Exception {
        CVFeedback cvFeedback= ObjectsFactory.getCVFeedback();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.getEntityById(1)).thenReturn(cvFeedback);
        controller.setAbstractDao(daoMock);

        mockMvc.perform(get("/cv_feedback/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkSQLExceptionInGetEntity() throws Exception {
        controller.setAbstractDao(null);

        mockMvc.perform(get("/cv_feedback/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }


    @Test
    public void checkOkInUpdateEntity() throws Exception {
        CVFeedback cvFeedback= ObjectsFactory.getCVFeedback();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(cvFeedback)).thenReturn(true);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/cv_feedback/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(cvFeedback))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInUpdateEntity() throws Exception {
        CVFeedback cvFeedback= ObjectsFactory.getCVFeedback();

        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.updateEntity(cvFeedback)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/cv_feedback/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(cvFeedback))
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkExceptionInUpdateEntity() throws Exception {
        CVFeedback cvFeedback= ObjectsFactory.getCVFeedback();

        controller.setAbstractDao(null);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/cv_feedback/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(cvFeedback))
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
        mockMvc.perform(delete("/cv_feedback/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkNotFoundInDeleteEntity() throws Exception {
        AbstractDao daoMock = mock(AbstractDao.class);
        when(daoMock.deleteEntityById(1)).thenReturn(false);

        controller.setAbstractDao(daoMock);
        mockMvc.perform(delete("/cv_feedback/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkSQLExceptionInDeleteEntity() throws Exception {
        controller.setAbstractDao(null);
        mockMvc.perform(delete("/cv_feedback/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Server error"));
    }
}
