package br.com.serasa.restapi.persistence.repository;

import br.com.serasa.restapi.persistence.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
