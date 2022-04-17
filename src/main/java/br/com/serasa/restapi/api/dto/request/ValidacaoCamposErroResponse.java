package br.com.serasa.restapi.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidacaoCamposErroResponse {

    private Integer codigo;
    private String status;
    private List<String> erros;

}
