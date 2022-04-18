package br.com.serasa.restapi.persistence.repository;

import br.com.serasa.restapi.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

    boolean existsByUsername(String username);
}
