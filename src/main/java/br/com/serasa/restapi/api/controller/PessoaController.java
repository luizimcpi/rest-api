package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.request.PessoaRequest;
import br.com.serasa.restapi.api.dto.response.PessoaResponse;
import br.com.serasa.restapi.api.mapper.PessoaMapper;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.service.PessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid PessoaRequest request){

        log.info("Iniciando cadastro de nova pessoa no sistema com request: {}", request);

        pessoaService.salvar(PessoaMapper.toPersistenceModel(request));

        log.info("Pessoa cadastrada com sucesso no sistema");

        return ResponseEntity.created(URI.create("/pessoa")).build();

    }

    @GetMapping("{id}")
    public ResponseEntity buscarPorId(@PathVariable("id") Long id){

        log.info("Iniciando busca de pessoa no sistema com id: {}", id);

        PessoaResponse pessoaResponse = pessoaService.buscarPorId(id);

        log.info("Pessoa com id {} encontrada com sucesso no sistema", id);

        return new ResponseEntity(pessoaResponse, HttpStatus.OK);

    }
}
