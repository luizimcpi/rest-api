package br.com.serasa.restapi.service;


import br.com.serasa.restapi.persistence.entity.Score;
import br.com.serasa.restapi.persistence.repository.ScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static br.com.serasa.restapi.utils.ScoreTestUtils.SCORE_ACEITAVEL;
import static br.com.serasa.restapi.utils.ScoreTestUtils.SCORE_INACEITAVEL;
import static br.com.serasa.restapi.utils.ScoreTestUtils.SCORE_INSUFICIENTE;
import static br.com.serasa.restapi.utils.ScoreTestUtils.SCORE_RECOMENDAVEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScoreServiceTest {

    @InjectMocks
    ScoreService service;

    @Mock
    ScoreRepository repository;

    @Test
    public void deveSalvarUmScoreComSucessoQuandoParametrosValidos(){
        Score score = SCORE_INSUFICIENTE;

        when(repository.save(any())).thenReturn(score);

        Score scoreDb = service.salvar(score);

        Assertions.assertAll(
                () -> assertNotNull(scoreDb),
                () -> assertEquals(1L, scoreDb.getId()),
                () -> assertEquals("Insuficiente", scoreDb.getScoreDescricao()),
                () -> assertEquals(0, scoreDb.getScoreInicial()),
                () -> assertEquals(200, scoreDb.getScoreFinal())
        );
    }

    @Test
    public void deveRetornarOptionalEmptyQuandoRetornarListaVaziaDoBanco(){
        var scores = new ArrayList<Score>();

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(20);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertTrue(response.isEmpty())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreInsuficienteECodigoZero(){

        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(0);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Insuficiente", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreInsuficienteECodigoVinte(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(20);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Insuficiente", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreInsuficienteECodigoDuzentos(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(200);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Insuficiente", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreInaceitavelECodigoDuzentosEUm(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(201);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Inaceitável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreInaceitavelECodigoDuzentosEDez(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(210);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Inaceitável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreInaceitavelECodigoQuinhentos(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(500);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Inaceitável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreAceitavelECodigoQuinhentosEUm(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(501);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Aceitável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreAceitavelECodigoQuinhentosEDez(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(510);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Aceitável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreAceitavelECodigoSetecentos(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(700);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Aceitável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreRecomendavelECodigoSetecentosEUm(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(701);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Recomendável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreRecomendavelECodigoOitocentos(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(800);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Recomendável", response.get())
        );
    }

    @Test
    public void deveBuscarDescricaoComSucessoQuandoScoreRecomendavelECodigoMil(){
        var scores = List.of(SCORE_INSUFICIENTE, SCORE_INACEITAVEL, SCORE_ACEITAVEL, SCORE_RECOMENDAVEL);

        when(repository.findAll()).thenReturn(scores);

        var response = service.buscaDescricaoPeloScore(1000);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Recomendável", response.get())
        );
    }



}
