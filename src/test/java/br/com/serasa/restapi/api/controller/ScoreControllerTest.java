package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.request.ScoreRequest;
import br.com.serasa.restapi.persistence.entity.Score;
import br.com.serasa.restapi.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static br.com.serasa.restapi.utils.ScoreTestUtils.SCORE_INSUFICIENTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScoreControllerTest {

    private static final String URL = "/score";

    public static final ScoreRequest REQUEST_SCORE_VALIDO = ScoreRequest.builder()
            .scoreDescricao("Insuficiente")
            .scoreInicial(0)
            .scoreFinal(200)
            .build();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    ScoreService scoreService;

    @BeforeAll
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("spring")
    @Test
    public void deveCriarUmScoreComSucesso() throws Exception {
        ScoreRequest requestDto = REQUEST_SCORE_VALIDO;

        Score score = SCORE_INSUFICIENTE;

        when(scoreService.salvar(any(Score.class))).thenReturn(score);

        String json = new ObjectMapper().writeValueAsString(requestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(score.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("scoreInicial").value(score.getScoreInicial()))
                .andExpect(MockMvcResultMatchers.jsonPath("scoreFinal").value(score.getScoreFinal()))
                .andExpect(MockMvcResultMatchers.jsonPath("scoreDescricao").value(score.getScoreDescricao()));
    }



}
