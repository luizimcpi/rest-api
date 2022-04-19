package br.com.serasa.restapi.utils;

import br.com.serasa.restapi.api.dto.request.UsuarioRequest;
import br.com.serasa.restapi.persistence.entity.Usuario;

public final class UsuarioTestUtils {

    public static final String USERNAME_VALIDO = "teste";
    public static final String PASSWORD_VALIDO = "12345678";
    public static final Usuario USUARIO_VALIDO = Usuario.builder()
            .username(USERNAME_VALIDO)
            .password(PASSWORD_VALIDO)
            .build();
    public static final UsuarioRequest USUARIO_VALIDO_REQUEST = UsuarioRequest.builder()
            .username(USERNAME_VALIDO)
            .password(PASSWORD_VALIDO)
            .build();

    public static final UsuarioRequest USUARIO_SENHA_INVALIDA_REQUEST = UsuarioRequest.builder()
            .username(USERNAME_VALIDO)
            .password("1234")
            .build();

}
