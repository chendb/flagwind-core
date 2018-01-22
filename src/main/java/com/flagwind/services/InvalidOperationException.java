package com.flagwind.services;

public class InvalidOperationException extends RuntimeException {

    private static final long serialVersionUID = -1248428692273083866L;
    
    public InvalidOperationException(String message){
        super(message);
    }

}