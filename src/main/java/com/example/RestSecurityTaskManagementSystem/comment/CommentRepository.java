package com.example.RestSecurityTaskManagementSystem.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Optional<Comment> findByIdAndTask_Id(Long id,Long taskId);
}
