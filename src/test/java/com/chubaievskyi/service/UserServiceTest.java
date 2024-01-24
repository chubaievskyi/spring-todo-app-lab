package com.chubaievskyi.service;

import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.entity.UserEntity;
import com.chubaievskyi.exception.UserNotFoundException;
import com.chubaievskyi.mapper.UserMapper;
import com.chubaievskyi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;

    private UserEntity userEntity;

    private Long userId;

    @BeforeEach
    void SetUp() {
        userService = new UserService(userRepository);
        userId = 1L;

        userDto = new UserDto(
                1L,
                "First",
                "Test",
                "1234567899"
        );

        userEntity = new UserEntity(
                1L,
                "First",
                "Test",
                "1234567899"
        );
    }

    @Test
    void createUser() {

        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void updateUser() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserDto result = userService.updateUser(userId, userDto);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void updateUserWhenUserNotFound() {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, userDto));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void deleteUser() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUserWhenUserNotFound() {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void findUserById() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserDto result = userService.findUserById(userId);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findUserByIdWhenUserNotFound() {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).entityToDto(any(UserEntity.class));
    }

    @Test
    void findAllUsers() {

        List<UserEntity> userEntities = new ArrayList<>();
        Page<UserEntity> userEntityPage = new PageImpl<>(userEntities);

        when(userRepository.findAll(pageable)).thenReturn(userEntityPage);

        Page<UserDto> result = userService.findAllUsers(pageable);

        verify(userRepository, times(1)).findAll(pageable);
        assertEquals(Collections.emptyList(), result.getContent());
    }
}