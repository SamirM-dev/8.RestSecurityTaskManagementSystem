package com.example.RestSecurityTaskManagementSystem.comment;

import com.example.RestSecurityTaskManagementSystem.comment.dto.CommentCreateRequest;
import com.example.RestSecurityTaskManagementSystem.comment.dto.CommentResponse;
import com.example.RestSecurityTaskManagementSystem.helper.HelpForService;
import com.example.RestSecurityTaskManagementSystem.task.Task;
import com.example.RestSecurityTaskManagementSystem.task.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final static String TASK="Task";
    private final static String COMMENT="Comment";

    private final CommentRepository commentRepository;
    private final HelpForService helpForService;
    private final TaskRepository taskRepository;

    public List<CommentResponse> getCommentsByTask(Long taskId){
     return helpForService.checkId(taskId,taskRepository,TASK).getComments().stream().map(this::toResponse).toList();
    }

    public CommentResponse createComment(CommentCreateRequest request,Long taskId){
        Task task = helpForService.checkId(taskId,taskRepository,TASK);
        Comment created = commentRepository.save(new Comment(request.text(), request.author(),task));
        return toResponse(created);
    }
    public void delete(Long taskId,Long commentId){
        Comment deleted = commentRepository.findByIdAndTask_Id(commentId,taskId).orElseThrow(()->new EntityNotFoundException("Task{"+taskId+"} does not has comment{"+commentId+"}"));
        commentRepository.delete(deleted);
    }

    public CommentResponse toResponse(Comment comment){
        return new CommentResponse(
                comment.getId(), comment.getText(), comment.getAuthor(), comment.getCreatedAt()
        );
    }
}
