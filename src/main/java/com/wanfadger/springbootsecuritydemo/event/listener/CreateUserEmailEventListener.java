package com.wanfadger.springbootsecuritydemo.event.listener;

import com.wanfadger.springbootsecuritydemo.entity.User;
import com.wanfadger.springbootsecuritydemo.event.CreateUserEmailEvent;
import com.wanfadger.springbootsecuritydemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CreateUserEmailEventListener implements ApplicationListener<CreateUserEmailEvent> {

    @Autowired
    UserService userService;



    @Override
    public void onApplicationEvent(CreateUserEmailEvent event) {
        String token = UUID.randomUUID().toString();
        User user = event.getUser();
        String applicationUrl = event.getApplicationUrl();

        // create verification token
        String s = userService.saveVerificationToken(user, token);
        log.info(s);

        // create verification link/endpoint
        String verificationLink = applicationUrl.concat("/auth").concat("/verifyAccount?token=").concat(token);

        // send verification email
        sendVerificationEmail(verificationLink);

    }

    private void sendVerificationEmail(String verificationLink) {
        log.info("Click Link to Verify Your Account: {}" , verificationLink);
    }
}
