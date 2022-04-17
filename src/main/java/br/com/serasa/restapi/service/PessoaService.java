package br.com.serasa.restapi.service;

import br.com.serasa.restapi.api.dto.response.PessoaResponse;
import br.com.serasa.restapi.exception.NoContentException;
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

    public static final String DESCRICAO_SCORE_VAZIA = "";
    private final PessoaRepository pessoaRepository;
    private final ScoreService scoreService;

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        log.info("Cadastrando pessoa no sistema com modelo: {}", pessoa);
        return pessoaRepository.save(pessoa);
    }

    public PessoaResponse buscarPorId(Long id){
        log.info("Buscando pessoa no sistema com id: {}", id);
        var pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new NoContentException("Pessoa não encotrada com id informado!"));

        var optionalScoreDescricao = scoreService.buscaDescricaoPeloScore(pessoa.getScore());

        return PessoaResponse.builder()
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .idade(pessoa.getIdade())
                .scoreDescricao(optionalScoreDescricao.isPresent() ? optionalScoreDescricao.get() : DESCRICAO_SCORE_VAZIA)
                .build();
    }

}
