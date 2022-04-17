package br.com.serasa.restapi.api.mapper;

import br.com.serasa.restapi.api.dto.request.ScoreRequest;
import br.com.serasa.restapi.persistence.entity.Score;

public final class ScoreMapper {

    public static Score toPersistenceModel(ScoreRequest request) {
        return Score.builder()
                .scoreDescricao(request.getScoreDescricao())
                .scoreFinal(request.getScoreFinal())
                .scoreInicial(request.getScoreInicial())
                .build();
    }
}
