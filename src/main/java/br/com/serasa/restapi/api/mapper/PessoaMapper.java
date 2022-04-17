package br.com.serasa.restapi.api.mapper;

import br.com.serasa.restapi.api.dto.request.PessoaRequest;
import br.com.serasa.restapi.persistence.entity.Pessoa;

public final class PessoaMapper {

    public static Pessoa toPersistenceModel(PessoaRequest request) {
        return Pessoa.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .idade(request.getIdade())
                .cidade(request.getCidade())
                .estado(request.getEstado())
                .score(request.getScore())
                .regiao(request.getRegiao())
                .build();
    }
}
