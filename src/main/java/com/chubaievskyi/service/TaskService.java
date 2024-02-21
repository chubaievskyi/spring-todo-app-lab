package com.chubaievskyi.service;

import com.chubaievskyi.dto.TaskDto;
import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.entity.Status;
import com.chubaievskyi.entity.TaskEntity;
import com.chubaievskyi.exception.TaskNotFoundException;
import com.chubaievskyi.mapper.TaskMapper;
import com.chubaievskyi.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskService {

    final TaskRepository taskRepository;
    final UserService userService;

    public TaskDto createTask(TaskDto taskDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserByEmail(taskDto.getOwner());
        taskDto.setCreatedBy(username);
        taskDto.setCreatedAt(LocalDateTime.now());
        taskDto.setOwner(String.valueOf(userDto.getId()));
        taskDto.setStatus(String.valueOf(Status.NEW));
        TaskEntity taskEntity = TaskMapper.MAPPER.dtoToEntity(taskDto);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        return TaskMapper.MAPPER.entityToDto(savedTask);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            UserDto userDto = userService.findUserByEmail(taskDto.getOwner());
            TaskEntity taskEntity = optionalTaskEntity.get();
            taskEntity.setName(taskDto.getName());
            taskEntity.setDescription(taskDto.getDescription());
            taskEntity.setOwner(userDto.getId());
            taskEntity.setDeadline(LocalDate.parse(taskDto.getDeadline()));
            TaskEntity updatedTask = taskRepository.save(taskEntity);
            return TaskMapper.MAPPER.entityToDto(updatedTask);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskDto updateTaskStatus(Long id, String status) { //////!!!!!!!!!! на які статуси можна!!!!
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            TaskEntity taskEntity = optionalTaskEntity.get();
            taskEntity.setStatus(Status.valueOf(status));
            TaskEntity updatedTask = taskRepository.save(taskEntity);
            return TaskMapper.MAPPER.entityToDto(updatedTask);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public void deleteTask(Long id) {
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskDto findTaskById(Long id) {
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            UserDto userDto = userService.findUserById(optionalTaskEntity.get().getOwner());
            TaskDto taskDto = TaskMapper.MAPPER.entityToDto(optionalTaskEntity.get());
            taskDto.setOwner(userDto.getEmail());
            return taskDto;
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public Page<TaskDto> findAllTasks(Pageable pageable) {
        Page<TaskEntity> taskEntityPage = taskRepository.findAll(pageable);
        return taskEntityPage.map(TaskMapper.MAPPER::entityToDto);
    }
}