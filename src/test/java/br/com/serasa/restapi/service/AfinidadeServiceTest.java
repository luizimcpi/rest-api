package br.com.serasa.restapi.service;


import br.com.serasa.restapi.persistence.entity.Afinidade;
import br.com.serasa.restapi.persistence.repository.AfinidadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.serasa.restapi.utils.AfinidadeTestUtils.AFINIDADE_VALIDA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AfinidadeServiceTest {

    @InjectMocks
    AfinidadeService service;

    @Mock
    AfinidadeRepository repository;

    @Test
    public void deveSalvarAfinidadeComSucessoQuandoParametrosValidos(){
        Afinidade afinidade = AFINIDADE_VALIDA;

        when(repository.save(any())).thenReturn(afinidade);

        Afinidade afinidadeDb = service.salvar(afinidade);

        Assertions.assertAll(
                () -> assertNotNull(afinidadeDb),
                () -> assertEquals(1L, afinidadeDb.getId()),
                () -> assertEquals("sudeste", afinidadeDb.getRegiao()),
                () -> assertNotNull(afinidadeDb.getEstados())
        );
    }

}
