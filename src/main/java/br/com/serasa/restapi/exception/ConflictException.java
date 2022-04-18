package br.com.serasa.restapi.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String msg){
        super(msg);
    }
}
