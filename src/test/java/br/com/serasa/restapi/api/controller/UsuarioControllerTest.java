package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.exception.ConflictException;
import br.com.serasa.restapi.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static br.com.serasa.restapi.utils.UsuarioTestUtils.USUARIO_SENHA_INVALIDA_REQUEST;
import static br.com.serasa.restapi.utils.UsuarioTestUtils.USUARIO_VALIDO;
import static br.com.serasa.restapi.utils.UsuarioTestUtils.USUARIO_VALIDO_REQUEST;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    private static final String URL = "/usuario";


    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    UsuarioService usuarioService;

    @BeforeAll
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void deveCriarUsuarioComSucessoQuandoParametrosDaRequestValidos() throws Exception {

        doNothing().when(usuarioService).salvar(USUARIO_VALIDO);

        String json = new ObjectMapper().writeValueAsString(USUARIO_VALIDO_REQUEST);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void deveRetornarStatusBadRequestQuandoSenhaPossuirMenosDeOitoCaracteres() throws Exception {

        String json = new ObjectMapper().writeValueAsString(USUARIO_SENHA_INVALIDA_REQUEST);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", hasItem("Campo: 'password' Mensagem: Campo password deve ter no mínimo 8 caracteres")));
    }

    @Test
    public void deveRetornarStatusConflictQuandoExistirUsuarioPreviamenteCadastradoComMesmoUsername() throws Exception {

        doThrow(ConflictException.class).when(usuarioService).salvar(USUARIO_VALIDO);

        String json = new ObjectMapper().writeValueAsString(USUARIO_VALIDO_REQUEST);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

}
