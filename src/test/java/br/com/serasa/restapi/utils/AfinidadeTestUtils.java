package br.com.serasa.restapi.utils;

import br.com.serasa.restapi.persistence.entity.Afinidade;

import java.util.List;

public final class AfinidadeTestUtils {

    public static final Afinidade AFINIDADE_VALIDA = Afinidade.builder()
            .id(1L)
            .regiao("sudeste")
            .estados(List.of("SP", "RJ", "MG", "ES"))
            .build();
}
