package org.example.springsecurity_demo3_thymeleaf.repository;


import org.example.springsecurity_demo3_thymeleaf.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role getUserByName(@Param("name") String name);   // giống slide

    Optional<Role> findByName(String name);
}
