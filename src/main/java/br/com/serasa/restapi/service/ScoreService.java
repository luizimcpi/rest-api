package br.com.serasa.restapi.service;

import br.com.serasa.restapi.persistence.entity.Score;
import br.com.serasa.restapi.persistence.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<String> buscaDescricaoPeloScore(Integer score){
        var scores = scoreRepository.findAll();
        if(!scores.isEmpty()){
            var filteredScore = scores.stream()
                    .filter(s -> score >= s.getScoreInicial() && score <= s.getScoreFinal())
                    .collect(Collectors.toList()).get(0);

            return Optional.of(filteredScore.getScoreDescricao());
        }
        return Optional.empty();
    }

}
