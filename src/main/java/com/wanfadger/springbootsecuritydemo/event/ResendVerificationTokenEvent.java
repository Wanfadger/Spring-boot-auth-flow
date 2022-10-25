package com.wanfadger.springbootsecuritydemo.event;

import com.wanfadger.springbootsecuritydemo.entity.User;
import com.wanfadger.springbootsecuritydemo.entity.VerificationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Setter
@Getter

public class ResendVerificationTokenEvent extends ApplicationEvent {
    private VerificationToken verificationToken ;
    private String applicationUrl;
    public ResendVerificationTokenEvent(VerificationToken verificationToken, String applicationUrl) {
        super(verificationToken);

        this.applicationUrl = applicationUrl;
        this.verificationToken = verificationToken;
    }
}
