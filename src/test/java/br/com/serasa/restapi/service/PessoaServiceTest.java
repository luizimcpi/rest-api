package br.com.serasa.restapi.service;


import br.com.serasa.restapi.api.dto.response.PessoaIdResponse;
import br.com.serasa.restapi.exception.NoContentException;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.serasa.restapi.utils.PessoaTestUtils.PESSOA_VALIDA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    PessoaService service;

    @Mock
    PessoaRepository repository;

    @Mock
    ScoreService scoreService;

    @Mock
    AfinidadeService afinidadeService;

    @Test
    public void deveSalvarUmaPessoaComSucessoQuandoParametrosValidos(){
        Pessoa pessoa = PESSOA_VALIDA;

        when(repository.save(any())).thenReturn(pessoa);

        Pessoa pessoaDb = service.salvar(pessoa);

        Assertions.assertAll(
                () -> assertNotNull(pessoaDb),
                () -> assertEquals(1L, pessoaDb.getId()),
                () -> assertEquals("Teste", pessoaDb.getNome()),
                () -> assertEquals("13 99999-9999", pessoaDb.getTelefone()),
                () -> assertEquals(28, pessoaDb.getIdade()),
                () -> assertEquals("São Paulo", pessoaDb.getCidade()),
                () -> assertEquals("SP", pessoaDb.getEstado()),
                () -> assertEquals(1000, pessoaDb.getScore()),
                () -> assertEquals("sudeste", pessoaDb.getRegiao()),
                () -> assertNotNull(pessoaDb.getDataInclusao())
        );
    }

    @Test
    public void deveBuscarUmaPessoaPorIdComSucessoQuandoParametroValido(){

        var estados = List.of("SP", "RJ", "MG", "ES");

        when(repository.findById(anyLong())).thenReturn(Optional.of(PESSOA_VALIDA));
        when(scoreService.buscaDescricaoPeloScore(any())).thenReturn(Optional.of("Recomendável"));
        when(afinidadeService.findEstadosPorRegiao(anyString())).thenReturn(estados);

        PessoaIdResponse response = service.buscarPorId(1L);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Teste", response.getNome()),
                () -> assertEquals("13 99999-9999", response.getTelefone()),
                () -> assertEquals(28, response.getIdade()),
                () -> assertEquals("Recomendável", response.getScoreDescricao()),
                () -> assertEquals(estados.size(), response.getEstados().size())
        );
    }

    @Test
    public void deveBuscarUmaPessoaPorIdComSucessoQuandoParametroValidoEScoreEmBranco(){
        var estados = List.of("SP", "RJ", "MG", "ES");

        when(repository.findById(anyLong())).thenReturn(Optional.of(PESSOA_VALIDA));
        when(scoreService.buscaDescricaoPeloScore(any())).thenReturn(Optional.empty());
        when(afinidadeService.findEstadosPorRegiao(anyString())).thenReturn(estados);

        PessoaIdResponse response = service.buscarPorId(1L);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Teste", response.getNome()),
                () -> assertEquals("13 99999-9999", response.getTelefone()),
                () -> assertEquals(28, response.getIdade()),
                () -> assertEquals("", response.getScoreDescricao()),
                () -> assertEquals(estados.size(), response.getEstados().size())
        );
    }

    @Test
    public void deveLancarErroQuandoBuscarPorIdInvalido(){

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        NoContentException exception = assertThrows(NoContentException.class, () -> service.buscarPorId(1L));

        assertEquals("Pessoa não encotrada com id informado!", exception.getMessage());
    }

    @Test
    public void deveBuscarPessoasComSucesso(){

        var estados = List.of("SP", "RJ", "MG", "ES");

        when(repository.findAll()).thenReturn(List.of(PESSOA_VALIDA));
        when(scoreService.buscaDescricaoPeloScore(any())).thenReturn(Optional.of("Recomendável"));
        when(afinidadeService.findEstadosPorRegiao(anyString())).thenReturn(estados);

        var response = service.buscarTodas();

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size()),
                () -> assertEquals("Teste", response.get(0).getNome()),
                () -> assertEquals("São Paulo", response.get(0).getCidade()),
                () -> assertEquals("SP", response.get(0).getEstado()),
                () -> assertEquals(estados.size(), response.get(0).getEstados().size()),
                () -> assertEquals("Recomendável", response.get(0).getScoreDescricao())
        );
    }

    @Test
    public void deveLancarErroQuandoNaoExistirPessoasCadastradas(){

        when(repository.findAll()).thenReturn(new ArrayList<>());

        NoContentException exception = assertThrows(NoContentException.class, () -> service.buscarTodas());

        assertEquals("Não existem pessoas cadastradas", exception.getMessage());
    }

}
