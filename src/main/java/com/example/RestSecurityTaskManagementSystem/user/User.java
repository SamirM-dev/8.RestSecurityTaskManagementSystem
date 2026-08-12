package com.example.RestSecurityTaskManagementSystem.user;

import com.example.RestSecurityTaskManagementSystem.jwt.RefreshToken;
import com.example.RestSecurityTaskManagementSystem.task.Task;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Entity
@Table(name = "users")
public class User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)@CollectionTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"))@Column(name = "role")
    private Set<String> roles = new HashSet<>();
    @Column(nullable = false)
    private boolean  enabled;
    private String provider;
    @Column(name = "provider_id")
    private String providerId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    public User(){}
    public User(String name,String email,String password){
        this.name=name;
        this.email=email;
        this.password=password;
        this.roles.add("ROLE_USER");
        this.enabled=true;
        this.createdAt=LocalDateTime.now();
    }
    public User(String name,String email,String provider,String providerId){
        this.name=name;
        this.email=email;
        this.provider=provider;
        this.providerId=providerId;
        this.roles.add("ROLE_USER");
        this.enabled=true;
        this.createdAt=LocalDateTime.now();

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void addRole(String role){
        this.roles.add(role);
    }

    public void deleteRole(String role){
        this.roles.remove(role);
    }

    public void addTask(Task task){this.tasks.add(task);}

    public void deleteTask(Task task){this.tasks.remove(task);}

    public void addRefreshToken(RefreshToken token){this.refreshTokens.add(token);}

    public void deleteRefreshToken(RefreshToken token){this.refreshTokens.remove(token);}
}
