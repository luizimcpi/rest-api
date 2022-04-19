package br.com.serasa.restapi.service;


import br.com.serasa.restapi.exception.ConflictException;
import br.com.serasa.restapi.persistence.entity.Usuario;
import br.com.serasa.restapi.persistence.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static br.com.serasa.restapi.utils.UsuarioTestUtils.USUARIO_VALIDO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    UsuarioService service;

    @Mock
    UsuarioRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    public void deveSalvarUmUsuarioComSucessoQuandoParametrosValidos(){

        when(repository.existsByUsername(USUARIO_VALIDO.getUsername())).thenReturn(false);

        service.salvar(USUARIO_VALIDO);

        verify(repository, times(1)).save(any(Usuario.class));
    }


    @Test
    public void deveLancarErroQuandoExistirUsuarioPreviamenteCadastradoComMesmoUsername(){

        when(repository.existsByUsername(USUARIO_VALIDO.getUsername())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () -> service.salvar(USUARIO_VALIDO));

        Assertions.assertAll(
                () -> assertEquals("Já existe um usuário cadastrado com este username.", exception.getMessage()),
                () -> verify(repository, never()).save(any(Usuario.class))
        );
    }

}
