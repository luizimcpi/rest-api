package br.com.serasa.restapi.service;

import br.com.serasa.restapi.exception.ConflictException;
import br.com.serasa.restapi.persistence.entity.Usuario;
import br.com.serasa.restapi.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void salvar(final Usuario usuario){
        log.info("Salvando novo usuário no banco. {}", usuario.getUsername());

        validarUsername(usuario.getUsername());

        log.info("Usuario não existe iniciando processo de gravação do novo usuário no banco. {}", usuario.getUsername());

        Usuario usuarioComSenhaCriptografada = Usuario.builder()
                .username(usuario.getUsername())
                .password(bCryptPasswordEncoder.encode(usuario.getPassword()))
                .build();

        usuarioRepository.save(usuarioComSenhaCriptografada);
    }

    public void validarUsername(String username) {
        log.info("Validando existência do novo usuário {} no banco.", username);
        boolean existe = usuarioRepository.existsByUsername(username);
        if(existe){
            log.warn("Usuario com username ja existente no banco retornando erro.");
            throw new ConflictException("Já existe um usuário cadastrado com este username.");
        }
    }
}
