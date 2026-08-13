package com.example.RestSecurityTaskManagementSystem.user;

import com.example.RestSecurityTaskManagementSystem.helper.HelpForService;
import com.example.RestSecurityTaskManagementSystem.task.TaskService;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskResponse;
import com.example.RestSecurityTaskManagementSystem.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER = "User";

    private final UserRepository userRepository;
    private final HelpForService helpForService;
    private final TaskService taskService;

    public UserResponse getUserById(Long id){
        return toResponse(helpForService.checkId(id,userRepository,USER));
    }

    public List<TaskResponse> getTasksByUserId(Long id){
        return helpForService.checkId(id,userRepository,USER).getTasks().stream().map(taskService::toResponse).toList();
    }

    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(), user.getCreatedAt()
        );
    }
}
