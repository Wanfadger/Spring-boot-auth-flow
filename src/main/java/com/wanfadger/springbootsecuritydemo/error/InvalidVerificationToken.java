package com.wanfadger.springbootsecuritydemo.error;

public class InvalidVerificationToken extends RuntimeException{
    public InvalidVerificationToken(String message) {
        super(message);
    }
}
