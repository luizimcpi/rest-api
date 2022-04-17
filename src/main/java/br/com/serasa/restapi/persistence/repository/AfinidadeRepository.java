package br.com.serasa.restapi.persistence.repository;

import br.com.serasa.restapi.persistence.entity.Afinidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AfinidadeRepository extends JpaRepository<Afinidade, Long> {

    Optional<Afinidade> findEstadosByRegiao(String regiao);
}
