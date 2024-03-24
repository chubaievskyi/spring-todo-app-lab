package com.chubaievskyi.service;

import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.enums.Role;
import com.chubaievskyi.entity.UserEntity;
import com.chubaievskyi.exception.InvalidPasswordException;
import com.chubaievskyi.exception.UserNotFoundException;
import com.chubaievskyi.mapper.UserMapper;
import com.chubaievskyi.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserMapper userMapper;

    @Mock
    Pageable pageable;

    @InjectMocks
    UserService userService;

    UserDto userDto;

    UserEntity userEntity;

    Long userId;

    String userEmail;

    @BeforeEach
    void SetUp() {
        userService = new UserService(userRepository, passwordEncoder);
        userId = 1L;
        userEmail = "First@gmail.com";

        userDto = new UserDto(
                1L,
                "First@gmail.com",
                "123",
                "First",
                "Test",
                "USER"
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
    void createUser() {

        when(passwordEncoder.encode(any())).thenReturn("123");
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void updateUser() {

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode(any())).thenReturn("123");
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

    @Test
    void findUserByEmail() {

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));

        UserDto result = userService.findUserByEmail(userEmail);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository).findByEmail(userEmail);
    }

    @Test
    void findUserByEmailWhenUserNotFound() {

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(userEmail));

        verify(userRepository).findByEmail(userEmail);
        verify(userMapper, never()).entityToDto(any(UserEntity.class));
    }

    @Test
    void updateOwnUserPassword() {
        String currentPassword = "123";
        String newPassword = "456";

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(currentPassword, userEntity.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserDto result = userService.updateOwnUserPassword(userEmail, currentPassword, newPassword);

        assertNotNull(result);
        assertEquals(newPassword, result.getPassword());

        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(passwordEncoder).encode(newPassword);
        verify(userRepository).save(userEntity);
    }

    @Test
    void updateOwnUserPasswordWhenUserNotFound() {
        String email = "nonexistent@email.com";
        String currentPassword = userDto.getPassword();
        String newPassword = "456";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateOwnUserPassword(email, currentPassword, newPassword));

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder, never()).matches(any(), any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateOwnUserPasswordWhenInvalidPassword() {
        String email = userDto.getEmail();
        String currentPassword = "wrong_password";
        String newPassword = "456";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(currentPassword, userEntity.getPassword())).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> userService.updateOwnUserPassword(email, currentPassword, newPassword));

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(currentPassword, userEntity.getPassword());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }
}