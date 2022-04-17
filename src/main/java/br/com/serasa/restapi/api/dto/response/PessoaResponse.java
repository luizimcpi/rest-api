package br.com.serasa.restapi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class PessoaResponse {

    public final String scoreDescricao;
    private final List<String> estados;
}
