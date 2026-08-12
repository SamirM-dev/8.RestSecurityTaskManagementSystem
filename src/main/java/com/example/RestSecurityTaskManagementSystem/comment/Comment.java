package com.example.RestSecurityTaskManagementSystem.comment;

import com.example.RestSecurityTaskManagementSystem.task.Task;
import jakarta.persistence.*;
import lombok.Getter;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "comments")
public class Comment {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(nullable = false)@ManyToOne(fetch = FetchType.EAGER)@JoinColumn(name = "task_id")
    private Task task;

    public Comment(){}
    public Comment(String text,String author,Task task){
        this.text=text;
        this.author=author;
        this.task=task;
        this.createdAt=LocalDateTime.now();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
