package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.request.ScoreRequest;
import br.com.serasa.restapi.persistence.entity.Score;
import br.com.serasa.restapi.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static br.com.serasa.restapi.utils.ScoreTestUtils.SCORE_INSUFICIENTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest( controllers = ScoreController.class)
@AutoConfigureMockMvc
public class ScoreControllerTest {

    private static final String URL = "/score";

    public static final ScoreRequest REQUEST_SCORE_VALIDO = ScoreRequest.builder()
            .scoreDescricao("Insuficiente")
            .scoreInicial(0)
            .scoreFinal(200)
            .build();

    @Autowired
    MockMvc mvc;

    @MockBean
    ScoreService scoreService;

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
