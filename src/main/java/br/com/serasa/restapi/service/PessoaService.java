package br.com.serasa.restapi.service;

import br.com.serasa.restapi.api.dto.response.PessoaIdResponse;
import br.com.serasa.restapi.api.dto.response.PessoasResponse;
import br.com.serasa.restapi.api.mapper.PessoaMapper;
import br.com.serasa.restapi.exception.NoContentException;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaService {

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

        return PessoaMapper.toPessoaIdResponse(pessoa, optionalScoreDescricao, estados);
    }

    @Transactional
    public List<PessoasResponse> buscarTodas(){
        var pessoas = pessoaRepository.findAll();
        if(pessoas.isEmpty()) throw new NoContentException("Não existem pessoas cadastradas");

        List<PessoasResponse> pessoasResponse = new ArrayList<>();
        for(Pessoa pessoa : pessoas){
            var optionalScoreDescricao = scoreService.buscaDescricaoPeloScore(pessoa.getScore());
            var estados = afinidadeService.findEstadosPorRegiao(pessoa.getRegiao());
            var pessoaResponse = PessoaMapper.toPessoasResponse(pessoa, optionalScoreDescricao, estados);
            pessoasResponse.add(pessoaResponse);
        }

        return pessoasResponse;
    }


}
