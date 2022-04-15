package br.com.serasa.restapi.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class AfinidadeRequest {

    @NotEmpty(message = "Favor preencher/enviar o campo regiao na request")
    private String regiao;

    @NotEmpty(message = "Favor preencher/enviar o campo estados na request")
    private List<@NotBlank(message = "Item do campo 'estados' vazio, favor preencher corretamente.") String> estados;
}
