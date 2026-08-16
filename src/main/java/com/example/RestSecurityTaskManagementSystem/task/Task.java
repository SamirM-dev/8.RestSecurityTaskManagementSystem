package com.example.RestSecurityTaskManagementSystem.task;

import com.example.RestSecurityTaskManagementSystem.comment.Comment;
import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;
import com.example.RestSecurityTaskManagementSystem.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tasks")
public class Task {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.EAGER)@JoinColumn(name = "user_id",nullable = false)
    private User user;
    @OneToMany(mappedBy = "task",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Task(){}
    public Task(String title, String description,TaskPriority priority, User user){
        this.title=title;
        this.description=description;
        this.status=TaskStatus.NEW;
        this.priority=priority;
        this.user=user;
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addComment(Comment comment){this.comments.add(comment);}

    public void deleteComment(Comment comment){this.comments.remove(comment);}




}
