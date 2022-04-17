package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.request.AfinidadeRequest;
import br.com.serasa.restapi.api.mapper.AfinidadeMapper;
import br.com.serasa.restapi.persistence.entity.Afinidade;
import br.com.serasa.restapi.service.AfinidadeService;
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
@RequestMapping("/afinidade")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AfinidadeController {

    private final AfinidadeService afinidadeService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid AfinidadeRequest request){

        log.info("Iniciando cadastro de afinidade no sistema com request: {}", request);

        Afinidade afinidadeDb = afinidadeService.salvar(AfinidadeMapper.toPersistenceModel(request));

        log.info("Afinidade cadastrada com sucesso no sistema: {}", afinidadeDb);

        return new ResponseEntity(afinidadeDb, HttpStatus.CREATED);

    }
}
