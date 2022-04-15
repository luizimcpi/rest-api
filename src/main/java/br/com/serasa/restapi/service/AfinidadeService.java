package br.com.serasa.restapi.service;

import br.com.serasa.restapi.persistence.entity.Afinidade;
import br.com.serasa.restapi.persistence.repository.AfinidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AfinidadeService {

    private final AfinidadeRepository afinidadeRepository;

    @Transactional
    public Afinidade salvar(Afinidade afinidade) {
        log.info("Cadastrando afinidade no sistema com modelo: {}", afinidade);
        return afinidadeRepository.save(afinidade);
    }

}
