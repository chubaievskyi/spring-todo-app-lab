package com.chubaievskyi.service;

import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.entity.UserEntity;
import com.chubaievskyi.exception.UserNotFoundException;
import com.chubaievskyi.mapper.UserMapper;
import com.chubaievskyi.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements UserDetailsService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = UserMapper.MAPPER.dtoToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        return UserMapper.MAPPER.entityToDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = UserMapper.MAPPER.dtoToEntity(userDto);
            userEntity.setId(optionalUserEntity.get().getId());
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            UserEntity updatedUser = userRepository.save(userEntity);
            return UserMapper.MAPPER.entityToDto(updatedUser);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void deleteUser(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public UserDto findUserById(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            return UserMapper.MAPPER.entityToDto(optionalUserEntity.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public Page<UserDto> findAllUsers(Pageable pageable) {
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return userEntityPage.map(UserMapper.MAPPER::entityToDto);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(userEntity -> new User(
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        Collections.singleton(userEntity.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
    }
}