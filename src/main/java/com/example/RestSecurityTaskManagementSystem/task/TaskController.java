package com.example.RestSecurityTaskManagementSystem.task;

import com.example.RestSecurityTaskManagementSystem.details.UserPrincipal;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskCreateRequest;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskResponse;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskStatusUpdateRequest;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getMyTasks(@RequestParam(required = false) String status, @RequestParam(required = false) String priority, @ParameterObject Pageable pageable, @AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(taskService.getMyTasks(principal.getUser().getId(),status,priority,pageable));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskCreateRequest request, @AuthenticationPrincipal UserPrincipal principal){
        TaskResponse created = taskService.create(request,principal.getUser());
        return ResponseEntity.created(URI.create("/api/v1/tasks/"+created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@Valid @RequestBody TaskUpdateRequest request, @PathVariable Long id){
        return ResponseEntity.ok(taskService.update(request,id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> statusUpdate(@Valid @RequestBody TaskStatusUpdateRequest request, @PathVariable Long id){
        return ResponseEntity.ok(taskService.updateStatus(request,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
