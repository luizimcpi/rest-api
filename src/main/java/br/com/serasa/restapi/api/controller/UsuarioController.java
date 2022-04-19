package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.request.UsuarioRequest;
import br.com.serasa.restapi.api.mapper.UsuarioMapper;
import br.com.serasa.restapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity registraNovoUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        log.info("Iniciando cadastro de novo usuario no sistema com username: {}", usuarioRequest.getUsername());

        usuarioService.salvar(UsuarioMapper.toPersistenceModel(usuarioRequest));

        log.info("Usuario cadastrado com sucesso no sistema");

        return ResponseEntity.created(URI.create("/usuario")).build();
    }
}
