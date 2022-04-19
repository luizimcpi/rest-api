package br.com.serasa.restapi.api.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
public class UsuarioRequest {

    @NotEmpty(message = "Favor preencher/enviar o campo username na request")
    private String username;

    @NotEmpty(message = "Favor preencher/enviar o campo password na request")
    @Size(message = "Campo password deve ter no mínimo 8 caracteres", min = 8)
    private String password;


}
