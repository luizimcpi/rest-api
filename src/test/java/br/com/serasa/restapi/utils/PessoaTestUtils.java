package br.com.serasa.restapi.utils;

import br.com.serasa.restapi.api.dto.PessoaRequest;
import br.com.serasa.restapi.persistence.entity.Pessoa;

import java.time.LocalDateTime;

public final class PessoaTestUtils {

    public static final Pessoa PESSOA_VALIDA = Pessoa.builder()
            .id(1L)
            .nome("Teste")
            .telefone("13 99999-9999")
            .idade(28)
            .cidade("São Paulo")
            .estado("SP")
            .score(1000)
            .regiao("sudeste")
            .dataInclusao(LocalDateTime.now())
            .build();

    public static final PessoaRequest REQUEST_PESSOA_VALIDA = PessoaRequest.builder()
            .nome("Teste")
            .telefone("13 99999-9999")
            .idade(28)
            .cidade("São Paulo")
            .estado("SP")
            .score(1000)
            .regiao("sudeste")
            .build();

    public static final PessoaRequest REQUEST_PESSOA_SEM_NOME_PREENCHIDO = PessoaRequest.builder()
            .nome("")
            .telefone("13 99999-9999")
            .idade(28)
            .cidade("São Paulo")
            .estado("SP")
            .score(1000)
            .regiao("sudeste")
            .build();

    public static final PessoaRequest REQUEST_PESSOA_SCORE_MAIOR_QUE_MIL = PessoaRequest.builder()
            .nome("Teste")
            .telefone("13 99999-9999")
            .idade(28)
            .cidade("São Paulo")
            .estado("SP")
            .score(1001)
            .regiao("sudeste")
            .build();

    public static final PessoaRequest REQUEST_PESSOA_SCORE_MENOR_QUE_MIL = PessoaRequest.builder()
            .nome("Teste")
            .telefone("13 99999-9999")
            .idade(28)
            .cidade("São Paulo")
            .estado("SP")
            .score(-1)
            .regiao("sudeste")
            .build();
}
