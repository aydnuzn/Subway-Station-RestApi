package com.works.metrostation.exception;

public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
