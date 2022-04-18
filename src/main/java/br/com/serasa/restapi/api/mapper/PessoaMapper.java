package br.com.serasa.restapi.api.mapper;

import br.com.serasa.restapi.api.dto.request.PessoaRequest;
import br.com.serasa.restapi.api.dto.response.PessoaIdResponse;
import br.com.serasa.restapi.api.dto.response.PessoasResponse;
import br.com.serasa.restapi.persistence.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public final class PessoaMapper {

    private static final String DESCRICAO_SCORE_VAZIA = "";

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

    public static PessoaIdResponse toPessoaIdResponse(Pessoa pessoa,
                                                      Optional<String> optionalScoreDesc,
                                                      List<String> estados){
        return PessoaIdResponse.builder()
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .idade(pessoa.getIdade())
                .scoreDescricao(optionalScoreDesc.isPresent() ? optionalScoreDesc.get() : DESCRICAO_SCORE_VAZIA)
                .estados(estados)
                .build();
    }

    public static PessoasResponse toPessoasResponse(Pessoa pessoa,
                                                    Optional<String> optionalScoreDesc,
                                                    List<String> estados){
        return PessoasResponse.builder()
                .nome(pessoa.getNome())
                .cidade(pessoa.getCidade())
                .estado(pessoa.getEstado())
                .scoreDescricao(optionalScoreDesc.isPresent() ? optionalScoreDesc.get() : DESCRICAO_SCORE_VAZIA)
                .estados(estados)
                .build();
    }
}
