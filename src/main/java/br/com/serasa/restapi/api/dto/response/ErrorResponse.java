package br.com.serasa.restapi.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

   private String mensagem;

}
