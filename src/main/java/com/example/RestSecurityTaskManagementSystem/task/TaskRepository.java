package com.example.RestSecurityTaskManagementSystem.task;

import com.example.RestSecurityTaskManagementSystem.enums.TaskPriority;
import com.example.RestSecurityTaskManagementSystem.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE t.user.id=:userId AND (:status IS NULL OR t.status=:status) AND (:priority IS NULL OR t.priority=:priority)")
    Page<Task> findWithFiltersAndPagination(@Param("userId") Long userId,@Param("status")TaskStatus status, @Param("priority")TaskPriority priority, Pageable pageable);
}
