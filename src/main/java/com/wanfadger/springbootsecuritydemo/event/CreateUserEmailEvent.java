package com.wanfadger.springbootsecuritydemo.event;

import com.wanfadger.springbootsecuritydemo.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class CreateUserEmailEvent extends ApplicationEvent {
  private User user;
  private String applicationUrl; //email url
    public CreateUserEmailEvent(User user , String applicationUrl) {
        super(user);
        this.applicationUrl = applicationUrl;
        this.user = user;
    }
}
