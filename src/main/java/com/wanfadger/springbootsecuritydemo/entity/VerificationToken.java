package com.wanfadger.springbootsecuritydemo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class VerificationToken {
   private static long EXPIRATION_TIME = 10L;//MINUTES

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;
    private String token;

    private LocalDateTime expirationTime;
    private LocalDateTime verifiedAt;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.verifiedAt = LocalDateTime.now();
        this.expirationTime = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);

    }

    public  boolean validateVerificationTokeExpiration(LocalDateTime verificationTokenExpirationTime){
        return verificationTokenExpirationTime.isBefore(LocalDateTime.now());
    }

    @PrePersist
    private void onPrePersist(){
        this.verifiedAt = LocalDateTime.now();
    }

}
