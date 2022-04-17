package br.com.serasa.restapi.api.dto.request;

import br.com.serasa.restapi.api.dto.enums.Estado;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class AfinidadeRequest {

    @NotEmpty(message = "Favor preencher/enviar o campo regiao na request")
    private String regiao;

    @NotEmpty(message = "Favor preencher/enviar o campo estados na request")
    private Set<Estado> estados;
}
