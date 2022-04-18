package br.com.serasa.restapi.persistence.repository;

import br.com.serasa.restapi.persistence.entity.Score;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static br.com.serasa.restapi.config.CacheConfig.LISTA_SCORES;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Cacheable(cacheNames = { LISTA_SCORES })
    List<Score> findAll();
}
