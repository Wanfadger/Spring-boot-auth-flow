package com.wanfadger.springbootsecuritydemo.error;

import lombok.Data;

public class VerificationTokenExpiredException extends RuntimeException{

    public VerificationTokenExpiredException(String message) {
        super(message);
    }
}
