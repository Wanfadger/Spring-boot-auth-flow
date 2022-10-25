package com.wanfadger.springbootsecuritydemo.event.listener;

import com.wanfadger.springbootsecuritydemo.event.PasswordChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PasswordChangeEventListener implements ApplicationListener<PasswordChangeEvent> {
    @Override
    public void onApplicationEvent(PasswordChangeEvent event) {
     String email = event.getEmail();

     sendPasswordChangeEmail(email);
    }

    private void sendPasswordChangeEmail(String email) {
        log.info("Password Change event sent to "+email);
    }
}
