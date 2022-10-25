package com.wanfadger.springbootsecuritydemo.service.serviceImpl;

import com.wanfadger.springbootsecuritydemo.dto.PasswordDto;
import com.wanfadger.springbootsecuritydemo.dto.UserDto;
import com.wanfadger.springbootsecuritydemo.entity.User;
import com.wanfadger.springbootsecuritydemo.entity.VerificationToken;
import com.wanfadger.springbootsecuritydemo.error.InvalidVerificationToken;
import com.wanfadger.springbootsecuritydemo.error.UnVerifiedUserException;
import com.wanfadger.springbootsecuritydemo.error.UserNotFoundException;
import com.wanfadger.springbootsecuritydemo.error.VerificationTokenExpiredException;
import com.wanfadger.springbootsecuritydemo.event.CreateUserEmailEvent;
import com.wanfadger.springbootsecuritydemo.event.ForgotPasswordEvent;
import com.wanfadger.springbootsecuritydemo.event.PasswordChangeEvent;
import com.wanfadger.springbootsecuritydemo.event.ResendVerificationTokenEvent;
import com.wanfadger.springbootsecuritydemo.repository.UserRepository;
import com.wanfadger.springbootsecuritydemo.repository.VerificationTokenRepository;
import com.wanfadger.springbootsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${server.protocal}")
    private  String SERVER_PROTOCAL;

    @Override
    public String createUser(UserDto userDto , final HttpServletRequest request) {
        User user = User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        User save = userRepository.save(user);
        String applicationUrl =generateApplicationUrl(request);
        // trigger send email event
        publisher.publishEvent(new CreateUserEmailEvent(save , applicationUrl));

        return save != null ? "User Successfully created" : "User failed to create";
    }

    private String generateApplicationUrl(final HttpServletRequest request){
        return SERVER_PROTOCAL
                .concat("://")
                .concat(request.getServerName())
                .concat(":")
                .concat(String.valueOf(request.getServerPort()))
                .concat(request.getContextPath());
    }

    @Override
    public String saveVerificationToken(User user, String token) {
        VerificationToken save = verificationTokenRepository.save(new VerificationToken(user , token));
        return save != null ? "Successfully created verification token" : "Failed to create verification token";
    }

    @Override
    public String saveVerificationToken(VerificationToken token) {
       return verificationTokenRepository.save(token) != null ? "Successfully Updated Verification token" : "failed to update verification token";
    }

    @Override
    public String verifyUserAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            throw new InvalidVerificationToken("Invalid verification token");
        }


        if (!verificationToken.validateVerificationTokeExpiration(verificationToken.getExpirationTime())) {
            throw new VerificationTokenExpiredException("verification token is expired");
        }

        User user = verificationToken.getUser();
        // enable user
        user.setEnabled(true);
        User save = userRepository.save(user);


        // delete verification token
        verificationTokenRepository.delete(verificationToken);

        return save != null ? "enabled" :"failed to enable user";
    }



    @Override
    public String resendVerificationToken(String oldToken , final HttpServletRequest request) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);

        if (verificationToken == null) {
            throw new InvalidVerificationToken("Invalid verification token");
        }
         publisher.publishEvent(new ResendVerificationTokenEvent(verificationToken ,generateApplicationUrl(request)));

        return "Verification Link sent";
    }

    @Override
    public String resetPassword(PasswordDto passwordDto) {
        User user = userRepository.findByEmail(passwordDto.getEmail());
        if (user == null) {
          throw new UserNotFoundException("User with email "+passwordDto.getEmail()+" not found");
        }

        if (!user.isEnabled()) {
            throw  new UnVerifiedUserException("Unverified user account, contact system admin");
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        User save = userRepository.save(user);

        publisher.publishEvent(new PasswordChangeEvent(passwordDto.getEmail()));

        return save != null ? "Password Successfully reset" :"Failed to reset password";
    }

    @Override
    public String forgotPassword(String email , final HttpServletRequest request) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email "+email+" not found");
        }

        if (!user.isEnabled()) {
            throw  new UnVerifiedUserException("Unverified user account, contact system admin");
        }

        publisher.publishEvent(new ForgotPasswordEvent(email , generateApplicationUrl(request)));

        return "Forgot Password Event Sent to your email";
    }

    @Override
    public String changePassword(PasswordDto passwordDto) {
        User user = userRepository.findByEmail(passwordDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User with email "+passwordDto.getEmail()+" not found");
        }

        if (!user.isEnabled()) {
            throw  new UnVerifiedUserException("Unverified user account, contact system admin");
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        userRepository.save(user);


        publisher.publishEvent(new PasswordChangeEvent(passwordDto.getEmail()));
        return "Password Successfully Changed";
    }


}
