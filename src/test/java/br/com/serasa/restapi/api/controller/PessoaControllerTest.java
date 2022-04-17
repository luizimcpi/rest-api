package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.request.PessoaRequest;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.service.PessoaService;
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

import static br.com.serasa.restapi.utils.PessoaTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest( controllers = PessoaController.class)
@AutoConfigureMockMvc
public class PessoaControllerTest {

    private static final String URL = "/pessoa";

    @Autowired
    MockMvc mvc;

    @MockBean
    PessoaService pessoaService;

    @Test
    public void deveCriarUmaPessoaComSucesso() throws Exception {
        PessoaRequest requestDto = REQUEST_PESSOA_VALIDA;

        Pessoa pessoa = PESSOA_VALIDA;

        when(pessoaService.salvar(any(Pessoa.class))).thenReturn(pessoa);

        String json = new ObjectMapper().writeValueAsString(requestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(pessoa.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(pessoa.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("telefone").value(pessoa.getTelefone()))
                .andExpect(MockMvcResultMatchers.jsonPath("idade").value(pessoa.getIdade()))
                .andExpect(MockMvcResultMatchers.jsonPath("cidade").value(pessoa.getCidade()))
                .andExpect(MockMvcResultMatchers.jsonPath("estado").value(pessoa.getEstado()))
                .andExpect(MockMvcResultMatchers.jsonPath("score").value(pessoa.getScore()))
                .andExpect(MockMvcResultMatchers.jsonPath("regiao").value(pessoa.getRegiao()));
    }

    @Test
    public void deveRetornarBadRequestQuandoParametroNomeNaoEstiverPreenchido() throws Exception {
        PessoaRequest requestDto = REQUEST_PESSOA_SEM_NOME_PREENCHIDO;

        String json = new ObjectMapper().writeValueAsString(requestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestQuandoParametroScoreForMaiorQueMil() throws Exception {
        PessoaRequest requestDto = REQUEST_PESSOA_SCORE_MAIOR_QUE_MIL;

        String json = new ObjectMapper().writeValueAsString(requestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestQuandoParametroScoreForMenorQueZero() throws Exception {
        PessoaRequest requestDto = REQUEST_PESSOA_SCORE_MENOR_QUE_MIL;

        String json = new ObjectMapper().writeValueAsString(requestDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
