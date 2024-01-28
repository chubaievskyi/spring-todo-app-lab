package com.chubaievskyi.service;

import com.chubaievskyi.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OwnUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;


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