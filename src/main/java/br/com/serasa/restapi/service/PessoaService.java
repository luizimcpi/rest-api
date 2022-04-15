package br.com.serasa.restapi.service;

import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        log.debug("Cadastrando pessoa no sistema com modelo: {}", pessoa);
        return pessoaRepository.save(pessoa);
    }

}
