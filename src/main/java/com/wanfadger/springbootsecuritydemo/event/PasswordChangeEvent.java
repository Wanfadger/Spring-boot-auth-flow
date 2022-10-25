package com.wanfadger.springbootsecuritydemo.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordChangeEvent extends ApplicationEvent {
   String email;
    public PasswordChangeEvent(String email ) {
        super(email);
        this.email = email;
    }
}
