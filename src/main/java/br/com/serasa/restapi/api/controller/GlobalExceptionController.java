package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.ValidacaoCamposErroResponse;
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
    public ResponseEntity<ValidacaoCamposErroResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldErrors = e.getBindingResult().getFieldErrors();
        var globalErrors = e.getBindingResult().getGlobalErrors();

        List<String> validationErrors = new ArrayList<>();

        for(FieldError error : fieldErrors){
            validationErrors.add("Campo: '" + error.getField() + "' Mensagem: "+ error.getDefaultMessage());
        }
        for(ObjectError error : globalErrors) {
            validationErrors.add("Objeto: '" + error.getObjectName() + "' Mensagem: "+ error.getDefaultMessage());
        }

        ValidacaoCamposErroResponse response = new ValidacaoCamposErroResponse();
        response.setCodigo(HttpStatus.BAD_REQUEST.value());
        response.setStatus(HttpStatus.BAD_REQUEST.name());
        response.setErros(validationErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}

