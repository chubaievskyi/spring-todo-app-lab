package com.chubaievskyi.repository;

import com.chubaievskyi.entity.TaskEntity;
import com.chubaievskyi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

//    Optional<TaskEntity> updateTaskStatus(Long id);

}