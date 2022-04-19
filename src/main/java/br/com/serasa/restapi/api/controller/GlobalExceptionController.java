package br.com.serasa.restapi.api.controller;

import br.com.serasa.restapi.api.dto.response.ErrorResponse;
import br.com.serasa.restapi.api.dto.response.ValidacaoCamposErroResponse;
import br.com.serasa.restapi.exception.ConflictException;
import br.com.serasa.restapi.exception.NoContentException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity handleNoContentException(NoContentException e){
        log.warn("No content message: {}", e.getMessage());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity handleConflictException(ConflictException e){
        log.warn("Conflict message: {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder().mensagem(e.getMessage()).build() , HttpStatus.CONFLICT);
    }


}

