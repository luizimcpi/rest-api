package br.com.serasa.restapi.service;

import br.com.serasa.restapi.api.dto.response.PessoaIdResponse;
import br.com.serasa.restapi.api.dto.response.PessoaResponse;
import br.com.serasa.restapi.exception.NoContentException;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaService {

    public static final String DESCRICAO_SCORE_VAZIA = "";
    private final PessoaRepository pessoaRepository;
    private final ScoreService scoreService;
    private final AfinidadeService afinidadeService;

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        log.info("Cadastrando pessoa no sistema com modelo: {}", pessoa);
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public PessoaIdResponse buscarPorId(Long id){
        log.info("Buscando pessoa no sistema com id: {}", id);
        var pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new NoContentException("Pessoa não encotrada com id informado!"));

        var optionalScoreDescricao = scoreService.buscaDescricaoPeloScore(pessoa.getScore());
        var estados = afinidadeService.findEstadosPorRegiao(pessoa.getRegiao());

        return PessoaIdResponse.builder()
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .idade(pessoa.getIdade())
                .scoreDescricao(optionalScoreDescricao.isPresent() ? optionalScoreDescricao.get() : DESCRICAO_SCORE_VAZIA)
                .estados(estados)
                .build();
    }


}
