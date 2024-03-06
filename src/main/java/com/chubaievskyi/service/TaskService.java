package com.chubaievskyi.service;

import com.chubaievskyi.dto.TaskDto;
import com.chubaievskyi.entity.Event;
import com.chubaievskyi.entity.Status;
import com.chubaievskyi.entity.TaskEntity;
import com.chubaievskyi.exception.InvalidStatusException;
import com.chubaievskyi.exception.TaskNotFoundException;
import com.chubaievskyi.mapper.TaskMapper;
import com.chubaievskyi.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskService {

    final TaskRepository taskRepository;
    final StateMachine<Status, Event> stateMachine;

    public TaskService(TaskRepository taskRepository, StateMachine<Status, Event> stateMachine) {
        this.taskRepository = taskRepository;
        this.stateMachine = stateMachine;
    }

    public TaskDto createTask(TaskDto taskDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        taskDto.setCreatedBy(username);
        taskDto.setCreatedAt(LocalDateTime.now());
        taskDto.setStatus(String.valueOf(Status.NEW));
        TaskEntity taskEntity = TaskMapper.MAPPER.dtoToEntity(taskDto);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        return TaskMapper.MAPPER.entityToDto(savedTask);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            TaskEntity taskEntity = optionalTaskEntity.get();
            taskEntity.setName(taskDto.getName());
            taskEntity.setDescription(taskDto.getDescription());
            taskEntity.setOwner(taskDto.getOwner());
            taskEntity.setDeadline(LocalDate.parse(taskDto.getDeadline()));
            TaskEntity updatedTask = taskRepository.save(taskEntity);
            return TaskMapper.MAPPER.entityToDto(updatedTask);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskDto updateTaskStatus(Long id, Event status) {
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            TaskEntity taskEntity = optionalTaskEntity.get();
            Status currentStatus = taskEntity.getStatus();

            stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(accessor -> {
                        accessor.resetStateMachine(new DefaultStateMachineContext<>(currentStatus, null, null, null));
                    });

//            boolean transitionPossible = stateMachine.sendEvent(status);
            if (stateMachine.sendEvent(status)) {
                taskEntity.setStatus(stateMachine.getState().getId());
                TaskEntity updatedTask = taskRepository.save(taskEntity);
                return TaskMapper.MAPPER.entityToDto(updatedTask);
            } else {
                throw new InvalidStatusException(status);
            }
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
            return TaskMapper.MAPPER.entityToDto(optionalTaskEntity.get());
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    public Page<TaskDto> findAllTasks(Pageable pageable) {
        Page<TaskEntity> taskEntityPage = taskRepository.findAll(pageable);
        return taskEntityPage.map(TaskMapper.MAPPER::entityToDto);
    }
}