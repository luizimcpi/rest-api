package br.com.serasa.restapi.persistence.repository;

import br.com.serasa.restapi.persistence.entity.Afinidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AfinidadeRepository extends JpaRepository<Afinidade, Long> {
}
