package com.chubaievskyi.entity;

import com.chubaievskyi.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "first_name")
    String firstName;

    @JoinColumn(name = "last_name")
    String lastName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;
}