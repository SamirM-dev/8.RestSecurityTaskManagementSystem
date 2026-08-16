package com.example.RestSecurityTaskManagementSystem.user;

import com.example.RestSecurityTaskManagementSystem.task.dto.TaskResponse;
import com.example.RestSecurityTaskManagementSystem.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("#id==authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("#id==authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponse>> getTasksByUserId(@PathVariable Long id){
        return ResponseEntity.ok(userService.getTasksByUserId(id));
    }
}
