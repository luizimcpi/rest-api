package br.com.serasa.restapi.service;


import br.com.serasa.restapi.persistence.entity.Pessoa;
import br.com.serasa.restapi.persistence.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.serasa.restapi.utils.PessoaTestUtils.PESSOA_VALIDA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    PessoaService service;

    @Mock
    PessoaRepository repository;

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

}
