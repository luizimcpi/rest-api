package br.com.serasa.restapi.service;

import br.com.serasa.restapi.persistence.entity.Score;
import br.com.serasa.restapi.persistence.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreService {

    private final ScoreRepository scoreRepository;

    @Transactional
    public Score salvar(Score score) {
        log.info("Cadastrando score no sistema com modelo: {}", score);
        return scoreRepository.save(score);
    }

}
