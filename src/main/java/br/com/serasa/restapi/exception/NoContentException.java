package br.com.serasa.restapi.exception;

public class NoContentException extends RuntimeException {
    public NoContentException(String msg){
        super(msg);
    }
}
