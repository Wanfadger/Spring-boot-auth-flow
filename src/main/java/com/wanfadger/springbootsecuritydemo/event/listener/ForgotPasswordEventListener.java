package com.wanfadger.springbootsecuritydemo.event.listener;

import com.wanfadger.springbootsecuritydemo.entity.User;
import com.wanfadger.springbootsecuritydemo.event.CreateUserEmailEvent;
import com.wanfadger.springbootsecuritydemo.event.ForgotPasswordEvent;
import com.wanfadger.springbootsecuritydemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ForgotPasswordEventListener implements ApplicationListener<ForgotPasswordEvent> {

    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {
        String applicationUrl = event.getApplicationUrl();
        // create verification link/endpoint
        String verificationLink = applicationUrl.concat("/auth").concat("/forgotPassword?email=").concat(event.getEmail());

        // SEND FORGOT PASSWORD EMAIL
        // send verification email
        sendForgotPasswordEmail(verificationLink);

    }

    private void sendForgotPasswordEmail(String verificationLink) {
        // includes webpage to create new password
        log.info("Click Forgot password link: {}" , verificationLink);
    }

}
