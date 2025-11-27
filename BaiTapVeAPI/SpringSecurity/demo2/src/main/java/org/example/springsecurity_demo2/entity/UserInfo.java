package org.example.springsecurity_demo2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    // Lưu role dạng chuỗi: "ROLE_ADMIN", "ROLE_USER"
    @Column(nullable = false, length = 50)
    private String roles;
}
