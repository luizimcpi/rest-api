package br.com.serasa.restapi.utils;

import br.com.serasa.restapi.api.dto.request.PessoaRequest;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.entity.Score;

import java.time.LocalDateTime;

public final class ScoreTestUtils {

    public static final Score SCORE_INSUFICIENTE = Score.builder()
            .id(1L)
            .scoreInicial(0)
            .scoreFinal(200)
            .scoreDescricao("Insuficiente")
            .build();

    public static final Score SCORE_INACEITAVEL = Score.builder()
            .id(2L)
            .scoreInicial(201)
            .scoreFinal(500)
            .scoreDescricao("Inaceitável")
            .build();

    public static final Score SCORE_ACEITAVEL = Score.builder()
            .id(3L)
            .scoreInicial(501)
            .scoreFinal(700)
            .scoreDescricao("Aceitável")
            .build();

    public static final Score SCORE_RECOMENDAVEL = Score.builder()
            .id(4L)
            .scoreInicial(701)
            .scoreFinal(1000)
            .scoreDescricao("Recomendável")
            .build();

}
