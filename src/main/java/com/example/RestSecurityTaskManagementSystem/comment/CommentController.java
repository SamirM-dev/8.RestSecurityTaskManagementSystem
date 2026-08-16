package com.example.RestSecurityTaskManagementSystem.comment;

import com.example.RestSecurityTaskManagementSystem.comment.dto.CommentCreateRequest;
import com.example.RestSecurityTaskManagementSystem.comment.dto.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{taskId}/comments")
    @PreAuthorize("@helpForService.isOwner(authentication.principal.id,#taskId) or hasRole('ADMIN')")
    public ResponseEntity<List<CommentResponse>> getByTask(@PathVariable Long taskId){
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }

    @PostMapping("/{taskId}/comments")
    @PreAuthorize("@helpForService.isOwner(authentication.principal.id,#taskId) or hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> create(@Valid@RequestBody CommentCreateRequest request,@PathVariable Long taskId){
        CommentResponse response = commentService.createComment(request,taskId);
        return ResponseEntity.created(URI.create("/api/v1/tasks/"+taskId+"/comments/"+response.id())).body(response);
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long taskId,@PathVariable Long commentId){
        commentService.delete(taskId, commentId);
        return ResponseEntity.noContent().build();
    }
}
