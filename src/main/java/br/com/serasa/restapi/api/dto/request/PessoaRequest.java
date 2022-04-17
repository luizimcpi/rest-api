package br.com.serasa.restapi.api.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
public class PessoaRequest {

    @NotEmpty(message = "Favor preencher/enviar o campo nome na request")
    private String nome;

    @NotEmpty(message = "Favor preencher/enviar o campo telefone na request")
    private String telefone;

    @NotNull(message = "Favor preencher/enviar o campo idade na request")
    private Integer idade;

    @NotEmpty(message = "Favor preencher/enviar o campo cidade na request")
    private String cidade;

    @NotEmpty(message = "Favor preencher/enviar o campo estado na request")
    private String estado;

    @Min(0)
    @Max(1000)
    @NotNull(message = "Favor preencher/enviar o campo score na request")
    private Integer score;

    @NotEmpty(message = "Favor preencher/enviar o campo regiao na request")
    private String regiao;
}
