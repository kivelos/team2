package dev.java.controller;

import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Candidate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class CandidateControllerTest {

    @InjectMocks
    private CandidateController candidateController;

    private MockMvc mockMvc;

    @Mock
    private CandidateDao candidateDao;

    @Before
    public void setUp() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(candidateController).build();
    }

    @Test
    public void helloWorld() throws Exception {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate());
        candidates.add(new Candidate());

        when(candidateDao.getSortedEntitiesPage(1, "surname", true, 2))
                .thenReturn(candidates);
        mockMvc.perform(
                get("/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }


}
