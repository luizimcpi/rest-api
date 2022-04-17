package br.com.serasa.restapi.api.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PessoaResponse {

    private String nome;
    private String telefone;
    private Integer idade;
    private String scoreDescricao;
}
