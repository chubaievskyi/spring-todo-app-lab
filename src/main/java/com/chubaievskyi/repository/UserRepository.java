package com.chubaievskyi.repository;

import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

//    @Query(value = "SELECT ue1_0.* FROM users ue1_0 WHERE ue1_0.username = :username",
//            nativeQuery = true)
    Optional<UserEntity> findByUsername(String username);
}