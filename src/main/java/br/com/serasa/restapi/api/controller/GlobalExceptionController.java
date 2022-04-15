package br.com.serasa.restapi.api.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleRegraNegocioException(MethodArgumentNotValidException e) {
        var fieldErrors = e.getBindingResult().getFieldErrors();
        var globalErrors = e.getBindingResult().getGlobalErrors();

        List<String> validationErrors = new ArrayList<>();

        for(FieldError error : fieldErrors){
            validationErrors.add("Campo: '" + error.getField() + "' Mensagem: "+ error.getDefaultMessage());
        }
        for(ObjectError error : globalErrors) {
            validationErrors.add("Objeto: '" + error.getObjectName() + "' Mensagem: "+ error.getDefaultMessage());
        }

        ErrorMessageResponse errorMessage = new ErrorMessageResponse();
        errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.name());
        errorMessage.setErros(validationErrors);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

    }
}

@Getter
@Setter
class ErrorMessageResponse {

    private Integer code;
    private String status;
    private List<String> erros;

}
