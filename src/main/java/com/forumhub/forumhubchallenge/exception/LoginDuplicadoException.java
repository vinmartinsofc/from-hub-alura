package com.forumhub.forumhubchallenge.exception;

public class LoginDuplicadoException extends RuntimeException {
    public LoginDuplicadoException(String mensagem) {
        super(mensagem);
    }
}