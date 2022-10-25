package com.wanfadger.springbootsecuritydemo.error;

public class UnVerifiedUserException extends RuntimeException{

    public UnVerifiedUserException(String message) {
        super(message);
    }
}
