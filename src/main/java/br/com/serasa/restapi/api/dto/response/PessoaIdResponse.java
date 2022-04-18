package br.com.serasa.restapi.api.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PessoaIdResponse extends PessoaResponse {

    private final String telefone;
    private final Integer idade;

    @Builder
    public PessoaIdResponse(String scoreDescricao, List<String> estados, String nome, String telefone, Integer idade) {
        super(nome, scoreDescricao, estados);
        this.telefone = telefone;
        this.idade = idade;
    }

}
