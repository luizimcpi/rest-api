package br.com.serasa.restapi.api.mapper;

import br.com.serasa.restapi.api.dto.request.AfinidadeRequest;
import br.com.serasa.restapi.persistence.entity.Afinidade;

import java.util.stream.Collectors;

public final class AfinidadeMapper {

    public static Afinidade toPersistenceModel(AfinidadeRequest request) {
        return Afinidade.builder()
                .regiao(request.getRegiao())
                .estados(request.getEstados().stream().map(e -> e.name()).collect(Collectors.toSet()))
                .build();
    }
}
