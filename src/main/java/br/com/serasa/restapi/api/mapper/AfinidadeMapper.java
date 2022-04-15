package br.com.serasa.restapi.api.mapper;

import br.com.serasa.restapi.api.dto.AfinidadeRequest;
import br.com.serasa.restapi.persistence.entity.Afinidade;

public final class AfinidadeMapper {

    public static Afinidade toPersistenceModel(AfinidadeRequest request) {
        return Afinidade.builder()
                .regiao(request.getRegiao())
                .estados(request.getEstados())
                .build();
    }
}
