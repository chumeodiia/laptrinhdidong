package org.example.springsecurity_demo3_thymeleaf.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String name; // ví dụ: ROLE_ADMIN, ROLE_USER
}
