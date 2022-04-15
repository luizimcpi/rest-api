package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.ScoreRequest;
import br.com.serasa.restapi.api.mapper.ScoreMapper;
import br.com.serasa.restapi.persistence.entity.Score;
import br.com.serasa.restapi.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid ScoreRequest request){

        log.info("Iniciando cadastro de score no sistema com request: {}", request);

        Score scoreDb = scoreService.salvar(ScoreMapper.toPersistenceModel(request));

        log.info("Score cadastrado com sucesso no sistema: {}", scoreDb);

        return new ResponseEntity(scoreDb, HttpStatus.CREATED);

    }
}
