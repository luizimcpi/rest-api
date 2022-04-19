package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.enums.Estado;
import br.com.serasa.restapi.api.dto.request.AfinidadeRequest;
import br.com.serasa.restapi.persistence.entity.Afinidade;
import br.com.serasa.restapi.service.AfinidadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static br.com.serasa.restapi.utils.AfinidadeTestUtils.AFINIDADE_VALIDA;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AfinidadeControllerTest {

    private static final String URL = "/afinidade";

    public static final AfinidadeRequest REQUEST_AFINIDADE_VALIDA = AfinidadeRequest.builder()
            .regiao("sudeste")
            .estados(Set.of(Estado.SP, Estado.RJ, Estado.MG, Estado.ES))
            .build();

    public static final AfinidadeRequest REQUEST_AFINIDADE_SEM_REGIAO_PREENCHIDA = AfinidadeRequest.builder()
            .regiao("")
            .estados(Set.of(Estado.SP, Estado.RJ, Estado.MG, Estado.ES))
            .build();

    public static final AfinidadeRequest REQUEST_AFINIDADE_SEM_ESTADOS_PREENCHIDOS = AfinidadeRequest.builder()
            .regiao("sudeste")
            .build();

    public static final String REQUEST_AFINIDADE_COM_ESTADO_INVALIDO = "{\n" +
            "    \"regiao\": \"sudeste\",\n" +
            "    \"estados\": [\"SP\", \"MM\"]\n" +
            "}";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    AfinidadeService afinidadeService;

    @BeforeAll
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("spring")
    @Test
    public void deveCriarAfinidadeComSucesso() throws Exception {

        Afinidade afinidade = AFINIDADE_VALIDA;

        when(afinidadeService.salvar(any(Afinidade.class))).thenReturn(afinidade);

        String json = new ObjectMapper().writeValueAsString(REQUEST_AFINIDADE_VALIDA);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(afinidade.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("regiao").value(afinidade.getRegiao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados", hasItem("SP")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados", hasItem("RJ")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados", hasItem("MG")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estados", hasItem("ES")));
    }

    @WithMockUser("spring")
    @Test
    public void deveRetornarErroQuandoNaoPreencherRegiaoNaRequestDeAfinidade() throws Exception {

        String json = new ObjectMapper().writeValueAsString(REQUEST_AFINIDADE_SEM_REGIAO_PREENCHIDA);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("codigo").value((HttpStatus.BAD_REQUEST.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value((HttpStatus.BAD_REQUEST.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", hasItem("Campo: 'regiao' Mensagem: Favor preencher/enviar o campo regiao na request")));
    }

    @WithMockUser("spring")
    @Test
    public void deveRetornarErroQuandoNaoPreencherEstadosNaRequestDeAfinidade() throws Exception {

        String json = new ObjectMapper().writeValueAsString(REQUEST_AFINIDADE_SEM_ESTADOS_PREENCHIDOS);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("codigo").value((HttpStatus.BAD_REQUEST.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("status").value((HttpStatus.BAD_REQUEST.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", hasItem("Campo: 'estados' Mensagem: Favor preencher/enviar o campo estados na request")));
    }

    @WithMockUser("spring")
    @Test
    public void deveRetornarErroQuandPreencherEstadosComItemInvalidoNaRequestDeAfinidade() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_AFINIDADE_COM_ESTADO_INVALIDO);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
