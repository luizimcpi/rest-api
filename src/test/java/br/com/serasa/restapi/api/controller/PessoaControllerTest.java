package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.request.PessoaRequest;
import br.com.serasa.restapi.api.dto.response.PessoaIdResponse;
import br.com.serasa.restapi.api.dto.response.PessoasResponse;
import br.com.serasa.restapi.exception.NoContentException;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.service.PessoaService;
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

import java.util.List;

import static br.com.serasa.restapi.utils.PessoaTestUtils.PESSOA_VALIDA;
import static br.com.serasa.restapi.utils.PessoaTestUtils.REQUEST_PESSOA_SCORE_MAIOR_QUE_MIL;
import static br.com.serasa.restapi.utils.PessoaTestUtils.REQUEST_PESSOA_SCORE_MENOR_QUE_MIL;
import static br.com.serasa.restapi.utils.PessoaTestUtils.REQUEST_PESSOA_SEM_NOME_PREENCHIDO;
import static br.com.serasa.restapi.utils.PessoaTestUtils.REQUEST_PESSOA_VALIDA;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PessoaControllerTest {

    private static final String URL = "/pessoa";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    PessoaService pessoaService;

    @BeforeAll
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @WithMockUser("spring")
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
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithMockUser("spring")
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

    @WithMockUser("spring")
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

    @WithMockUser("spring")
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

    @WithMockUser("spring")
    @Test
    public void deveRetornarUmaPessoaPorIdComSucesso() throws Exception {

        PessoaIdResponse pessoaIdResponse = PessoaIdResponse.builder()
                .nome("Fulano")
                .idade(20)
                .telefone("99 99999-9999")
                .scoreDescricao("Insuficiente")
                .estados(List.of("SP", "RJ", "MG", "ES"))
                .build();


        when(pessoaService.buscarPorId(anyLong())).thenReturn(pessoaIdResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL+"/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(pessoaIdResponse.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("telefone").value(pessoaIdResponse.getTelefone()))
                .andExpect(MockMvcResultMatchers.jsonPath("idade").value(pessoaIdResponse.getIdade()))
                .andExpect(MockMvcResultMatchers.jsonPath("scoreDescricao").value(pessoaIdResponse.getScoreDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados", hasSize(4)));
    }

    @WithMockUser("spring")
    @Test
    public void deveRetornarErroQuandoBuscarUmaPessoaPorIdComIdInvalido() throws Exception {

        when(pessoaService.buscarPorId(anyLong())).thenThrow(NoContentException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL+"/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @WithMockUser("spring")
    @Test
    public void deveRetornarPessoasComSucesso() throws Exception {

        PessoasResponse pessoasResponse = PessoasResponse.builder()
                .nome("Fulano")
                .cidade("S??o Paulo")
                .estado("SP")
                .scoreDescricao("Insuficiente")
                .estados(List.of("SP", "RJ", "MG", "ES"))
                .build();

        var response = List.of(pessoasResponse);

        when(pessoaService.buscarTodas()).thenReturn(response);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser("spring")
    @Test
    public void deveRetornarNoContentQuandoNaoEncontrarPessoas() throws Exception {

        when(pessoaService.buscarTodas()).thenThrow(NoContentException.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
