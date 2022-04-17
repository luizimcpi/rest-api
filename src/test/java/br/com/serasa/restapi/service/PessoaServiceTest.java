package br.com.serasa.restapi.service;


import br.com.serasa.restapi.api.dto.response.PessoaResponse;
import br.com.serasa.restapi.exception.NoContentException;
import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.entity.Score;
import br.com.serasa.restapi.persistence.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.serasa.restapi.utils.PessoaTestUtils.PESSOA_VALIDA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    PessoaService service;

    @Mock
    PessoaRepository repository;

    @Mock
    ScoreService scoreService;

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

        when(repository.findById(anyLong())).thenReturn(Optional.of(PESSOA_VALIDA));
        when(scoreService.buscaDescricaoPeloScore(any())).thenReturn(Optional.of("Recomendável"));

        PessoaResponse response = service.buscarPorId(1L);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Teste", response.getNome()),
                () -> assertEquals("13 99999-9999", response.getTelefone()),
                () -> assertEquals(28, response.getIdade()),
                () -> assertEquals("Recomendável", response.getScoreDescricao())
        );
    }

    @Test
    public void deveBuscarUmaPessoaPorIdComSucessoQuandoParametroValidoEScoreEmBranco(){

        when(repository.findById(anyLong())).thenReturn(Optional.of(PESSOA_VALIDA));
        when(scoreService.buscaDescricaoPeloScore(any())).thenReturn(Optional.empty());

        PessoaResponse response = service.buscarPorId(1L);

        Assertions.assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Teste", response.getNome()),
                () -> assertEquals("13 99999-9999", response.getTelefone()),
                () -> assertEquals(28, response.getIdade()),
                () -> assertEquals("", response.getScoreDescricao())
        );
    }

    @Test
    public void deveLancarErroQuandoBuscarPorIdInvalido(){

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        NoContentException exception = assertThrows(NoContentException.class, () -> service.buscarPorId(1L));

        assertEquals("Pessoa não encotrada com id informado!", exception.getMessage());
    }

}
