package br.com.serasa.restapi.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PessoasResponse extends PessoaResponse {

    private final String cidade;
    private final String estado;

    @Builder
    public PessoasResponse(String scoreDescricao, List<String> estados, String nome, String cidade, String estado) {
        super(nome, scoreDescricao, estados);
        this.cidade = cidade;
        this.estado = estado;
    }

}
