package com.example.RestSecurityTaskManagementSystem.user;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
