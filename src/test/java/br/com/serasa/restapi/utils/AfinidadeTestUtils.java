package br.com.serasa.restapi.utils;

import br.com.serasa.restapi.persistence.entity.Afinidade;

import java.util.Set;

public final class AfinidadeTestUtils {

    public static final Afinidade AFINIDADE_VALIDA = Afinidade.builder()
            .id(1L)
            .regiao("sudeste")
            .estados(Set.of("SP", "RJ", "MG", "ES"))
            .build();
}
