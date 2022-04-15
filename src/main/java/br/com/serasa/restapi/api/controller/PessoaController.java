package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.PessoaRequest;
import br.com.serasa.restapi.api.mapper.PessoaMapper;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.repository.PessoaRepository;
import br.com.serasa.restapi.service.PessoaService;
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
@RequestMapping("/pessoa")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid PessoaRequest request){

        log.debug("Iniciando cadastro de nova pessoa no sistema com request: {}", request);

        Pessoa pessoaDb = pessoaService.salvar(PessoaMapper.toPersistenceModel(request));

        log.debug("Pessoa cadastrada com sucesso no sistema: {}", pessoaDb);

        return new ResponseEntity(pessoaDb, HttpStatus.CREATED);

    }
}
