package com.wanfadger.springbootsecuritydemo.controller;

import com.wanfadger.springbootsecuritydemo.dto.PasswordDto;
import com.wanfadger.springbootsecuritydemo.dto.UserDto;
import com.wanfadger.springbootsecuritydemo.service.UserService;
import com.wanfadger.springbootsecuritydemo.service.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello Uganda";
    }



    @PostMapping("/createUser")
    public String createUser(@RequestBody UserDto userDto , final HttpServletRequest request){
        //printRequestInfo(request);
        return userService.createUser(userDto , request);
    }


    @GetMapping("/verifyAccount")
    public String verifyUserAccount(@RequestParam("token") String token){
        //printRequestInfo(request);
        String s = userService.verifyUserAccount(token);
        return s.equalsIgnoreCase("enabled") ? "User Account successfully verified/enabled" : "";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerifyToken(@RequestParam("token") String token , final HttpServletRequest request){
        String s = userService.resendVerificationToken(token , request);
        return s;
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email , final HttpServletRequest request){

        String s = userService.forgotPassword(email , request);
        return s;
    }


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordDto passwordDto){
        String s = userService.resetPassword(passwordDto);
        return s;
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordDto passwordDto){
        String s = userService.changePassword(passwordDto);
        return s;
    }


    private void printRequestInfo(HttpServletRequest request) {
        log.info(String.valueOf(request));
        log.info("path info: {}" , request.getPathInfo());
        log.info("Request protocal: {}", request.getProtocol());
        log.info("Request context path: {}", request.getContextPath());
        log.info("Request serverport: {}", request.getServerPort());
        log.info("Request serverName: {}", request.getServerName());
        log.info("Request uri: {}",request.getRequestURI());
        log.info("Request url: {}",request.getRequestURL());
    }


}
