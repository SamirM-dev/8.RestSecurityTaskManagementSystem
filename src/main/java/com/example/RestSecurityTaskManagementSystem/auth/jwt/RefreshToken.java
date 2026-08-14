package com.example.RestSecurityTaskManagementSystem.auth.jwt;

import com.example.RestSecurityTaskManagementSystem.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)@JoinColumn(name = "user_id")
    private User user;
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public RefreshToken(){}
    public RefreshToken(String token,User user,LocalDateTime expiresAt){
        this.token=token;
        this.user=user;
        this.expiresAt=expiresAt;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
