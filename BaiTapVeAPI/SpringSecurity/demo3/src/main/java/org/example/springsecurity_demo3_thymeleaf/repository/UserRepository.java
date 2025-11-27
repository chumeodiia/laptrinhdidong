package org.example.springsecurity_demo3_thymeleaf.repository;


import org.example.springsecurity_demo3_thymeleaf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
