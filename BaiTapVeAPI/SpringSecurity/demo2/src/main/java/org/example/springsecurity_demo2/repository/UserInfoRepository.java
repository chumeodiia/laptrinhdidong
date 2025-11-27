package org.example.springsecurity_demo2.repository;


import org.example.springsecurity_demo2.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByUsername(String username);
}
