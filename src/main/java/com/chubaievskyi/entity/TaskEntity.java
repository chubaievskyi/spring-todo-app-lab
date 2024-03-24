package com.chubaievskyi.entity;

import com.chubaievskyi.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime createdAt;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "owner")
    String owner;

    @Column(name = "deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate deadline;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;
}