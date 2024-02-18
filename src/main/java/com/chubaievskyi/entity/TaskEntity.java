package com.chubaievskyi.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created")
    String created;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @JoinColumn(name = "owner_id")
    Long owner;

    @Column(name = "deadline")
    LocalDateTime deadline;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;
}