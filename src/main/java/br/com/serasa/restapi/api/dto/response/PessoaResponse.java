package br.com.serasa.restapi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class PessoaResponse {

    private final String nome;
    public final String scoreDescricao;
    private final List<String> estados;
}
