package br.com.serasa.restapi.api.mapper;

import br.com.serasa.restapi.api.dto.request.UsuarioRequest;
import br.com.serasa.restapi.persistence.entity.Usuario;

public final class UsuarioMapper {

    public static Usuario toPersistenceModel(UsuarioRequest request) {
        return Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

}
