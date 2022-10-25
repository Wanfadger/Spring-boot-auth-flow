package com.wanfadger.springbootsecuritydemo.event;

import com.wanfadger.springbootsecuritydemo.entity.VerificationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter

public class ForgotPasswordEvent extends ApplicationEvent {
    private String email ;
    private String applicationUrl;
    public ForgotPasswordEvent(String email, String applicationUrl) {
        super(email);

        this.applicationUrl = applicationUrl;
        this.email = email;
    }
}
