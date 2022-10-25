package com.wanfadger.springbootsecuritydemo.event.listener;

import com.wanfadger.springbootsecuritydemo.entity.User;
import com.wanfadger.springbootsecuritydemo.entity.VerificationToken;
import com.wanfadger.springbootsecuritydemo.event.ResendVerificationTokenEvent;
import com.wanfadger.springbootsecuritydemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ResendVerificationTokenEventListener implements ApplicationListener<ResendVerificationTokenEvent> {
    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(ResendVerificationTokenEvent event) {
     VerificationToken verificationToken = event.getVerificationToken();
     String applicationUrl = event.getApplicationUrl();

        // create verification token
        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        String s = userService.saveVerificationToken(verificationToken);
        log.info(s);
        // create verification link/endpoint
        String verificationLink = applicationUrl.concat("/auth").concat("/verifyAccount?token=").concat(newToken);

        // send verification email
        rendVerificationEmail(verificationLink);

    }

    private void rendVerificationEmail(String verificationLink) {
        log.info("Click Link to Verify Your Account: {}" , verificationLink);
    }
}
