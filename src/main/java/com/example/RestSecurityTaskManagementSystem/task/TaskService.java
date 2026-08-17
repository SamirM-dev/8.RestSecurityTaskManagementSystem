package com.example.RestSecurityTaskManagementSystem.task;

import com.example.RestSecurityTaskManagementSystem.details.UserPrincipal;
import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;
import com.example.RestSecurityTaskManagementSystem.helper.HelpForService;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskCreateRequest;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskResponse;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskStatusUpdateRequest;
import com.example.RestSecurityTaskManagementSystem.task.dto.TaskUpdateRequest;
import com.example.RestSecurityTaskManagementSystem.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private static final String TASK = "Task";

    private final TaskRepository taskRepository;
    private final HelpForService helpForService;

    public List<TaskResponse> getMyTasks(Long userId, String status, String priority, Pageable pageable){
        helpForService.checkSort(pageable);
        TaskStatus status1 = status==null?null:TaskStatus.valueOf(status);
        TaskPriority priority1 = priority==null?null:TaskPriority.valueOf(priority);
        Page<Task> result = taskRepository.findWithFiltersAndPagination(userId,status1,priority1,pageable);
        return result.get().map(this::toResponse).toList();
    }

    public TaskResponse create(TaskCreateRequest request, User user){
        Task created = taskRepository.save(new Task(request.title(), request.description(), request.priority() , user));
        return toResponse(created);
    }

    public TaskResponse getTaskById(Long id){
        return toResponse(helpForService.checkId(id,taskRepository,TASK));
    }

    public TaskResponse update(TaskUpdateRequest request,Long id){
        Task updated = helpForService.checkId(id,taskRepository,TASK);
        updated.setTitle(request.title());
        updated.setDescription(request.description());
        updated.setStatus(request.status());
        updated.setPriority(request.priority());
        return toResponse(taskRepository.save(updated));
    }

    public TaskResponse updateStatus(TaskStatusUpdateRequest request,Long id){
        Task updated = helpForService.checkId(id,taskRepository,TASK);
        updated.setStatus(request.status());
        return toResponse(taskRepository.save(updated));
    }

    public void delete(Long id){
        Task task = helpForService.checkId(id,taskRepository,TASK);
        taskRepository.delete(task);
    }

    public TaskResponse toResponse(Task task){
        return new TaskResponse(
                task.getId(),task.getTitle(),task.getDescription(),task.getStatus(),
                task.getPriority(),task.getCreatedAt(),task.getUpdatedAt(),task.getUser().getId()
        );
    }
}
