package com.wanfadger.springbootsecuritydemo.service;

import com.wanfadger.springbootsecuritydemo.dto.PasswordDto;
import com.wanfadger.springbootsecuritydemo.dto.UserDto;
import com.wanfadger.springbootsecuritydemo.entity.User;
import com.wanfadger.springbootsecuritydemo.entity.VerificationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface UserService {

    String createUser(UserDto userDto , final HttpServletRequest request);

    String saveVerificationToken(User user , String token);
    String saveVerificationToken(VerificationToken token);

    String verifyUserAccount(String token);

    String resendVerificationToken(String oldToken , final HttpServletRequest request);

    String resetPassword(PasswordDto passwordDto);
    String forgotPassword(String email , final HttpServletRequest request);

    String changePassword(PasswordDto passwordDto);
}
