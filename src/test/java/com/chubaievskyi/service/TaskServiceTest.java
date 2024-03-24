package com.chubaievskyi.service;

import com.chubaievskyi.dto.TaskDto;
import com.chubaievskyi.entity.*;
import com.chubaievskyi.enums.Event;
import com.chubaievskyi.enums.Role;
import com.chubaievskyi.enums.Status;
import com.chubaievskyi.exception.TaskNotFoundException;
import com.chubaievskyi.mapper.TaskMapper;
import com.chubaievskyi.repository.TaskRepository;
import com.chubaievskyi.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccessor;
import org.springframework.statemachine.state.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    StateMachine<Status, Event> stateMachine;

    @Mock
    StateMachineAccessor<Status, Event> stateMachineAccessor;

    @Mock
    State<Status, Event> state;

    @Mock
    TaskMapper taskMapper;

    @Mock
    Pageable pageable;

    @InjectMocks
    TaskService taskService;

    TaskDto taskDto;

    TaskEntity taskEntity;
    UserEntity userEntity;

    Long taskId;
    Long userId;


    @BeforeEach
    void SetUp() {

        taskService = new TaskService(taskRepository, userRepository, stateMachine);
        taskId = 1L;
        userId = 1L;

        taskDto = new TaskDto(
                1L,
                LocalDateTime.parse("2024-03-30 21:57", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                "user@gmail.com",
                "First test",
                "Test",
                "First@gmail.com",
                "2024-03-30",
                "NEW"
        );

        taskEntity = new TaskEntity(
                1L,
                LocalDateTime.parse("2024-03-30 21:57", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                "user@gmail.com",
                "First test",
                "Test",
                "First@gmail.com",
                LocalDate.parse("2024-03-30"),
                Status.NEW
        );

        userEntity = new UserEntity(
                1L,
                "First@gmail.com",
                "123",
                "First",
                "Test",
                Role.USER
        );
    }

    @Test
    void createTask() {

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(taskDto.getCreatedBy());
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        TaskDto result = taskService.createTask(taskDto);

        assertNotNull(result);

        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void updateTask() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

        TaskDto result = taskService.updateTask(taskId, taskDto);

        assertNotNull(result);
        assertEquals(taskDto, result);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(taskEntity);
    }

    @Test
    void updateTaskWhenTaskNotFound() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, taskDto));

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    void deleteTask() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void deleteTaskWhenTaskNotFound() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void findTaskById() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        TaskDto result = taskService.findTaskById(taskId);

        assertNotNull(result);
        assertEquals(taskDto, result);

        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void findTaskByIdWhenTaskNotFound() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findTaskById(taskId));

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskMapper, never()).entityToDto(any(TaskEntity.class));
    }

    @Test
    void findAllTasks() {

        List<TaskEntity> taskEntities = new ArrayList<>();
        Page<TaskEntity> taskEntityPage = new PageImpl<>(taskEntities);

        when(taskRepository.findAll(pageable)).thenReturn(taskEntityPage);

        Page<TaskDto> result = taskService.findAllTasks(pageable);

        verify(taskRepository, times(1)).findAll(pageable);
        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    void findAllTasksByUserId() {

        List<TaskEntity> taskEntities = new ArrayList<>();
        Page<TaskEntity> taskEntityPage = new PageImpl<>(taskEntities);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(taskRepository.findAllByOwner(userEntity.getEmail(), pageable)).thenReturn(taskEntityPage);

        Page<TaskDto> result = taskService.findAllTasksByUserId(pageable, userId);

        verify(taskRepository, times(1)).findAllByOwner(userEntity.getEmail(), pageable);
        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    void updateTaskStatus() {

        String status = "WORK_IN_PROGRESS";

        UserDetails userDetails = new User(userEntity.getEmail(), userEntity.getPassword(), getAuthorities());

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

        when(stateMachine.getStateMachineAccessor()).thenReturn(stateMachineAccessor);
        when(stateMachine.sendEvent(Event.WORK_IN_PROGRESS)).thenReturn(true);
        when(stateMachine.getState()).thenReturn(state);
        when(stateMachine.getState().getId()).thenReturn(Status.WORK_IN_PROGRESS);

        TaskDto result = taskService.updateTaskStatus(taskId, status);

        assertNotNull(result);
        assertEquals(status, result.getStatus());
        assertEquals(Status.WORK_IN_PROGRESS, taskEntity.getStatus());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(taskEntity);
        verify(stateMachine, times(1)).sendEvent(Event.WORK_IN_PROGRESS);
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
}