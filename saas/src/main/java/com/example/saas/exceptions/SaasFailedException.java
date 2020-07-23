package com.example.saas.exceptions;

/**
 * @author 马成军
 **/
public class SaasFailedException extends RuntimeException {
    public SaasFailedException(String message) {
        super(message);
    }
}

