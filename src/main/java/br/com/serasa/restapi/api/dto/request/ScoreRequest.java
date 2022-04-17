package br.com.serasa.restapi.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
public class ScoreRequest {

    @NotEmpty(message = "Favor preencher/enviar o campo scoreDescricao na request")
    private String scoreDescricao;

    @NotNull(message = "Favor preencher/enviar o campo inicial na request")
    @JsonProperty("inicial")
    private Integer scoreInicial;

    @NotNull(message = "Favor preencher/enviar o campo final na request")
    @JsonProperty("final")
    private Integer scoreFinal;

}
