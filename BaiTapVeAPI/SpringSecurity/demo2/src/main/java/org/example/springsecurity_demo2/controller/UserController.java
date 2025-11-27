package org.example.springsecurity_demo2.controller;


import org.example.springsecurity_demo2.entity.UserInfo;
import org.example.springsecurity_demo2.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public UserInfo createUser(@RequestBody UserInfo user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null) {
            user.setRoles("ROLE_USER");
        }

        return userInfoRepository.save(user);
    }
    @GetMapping("/hello")
    public String hello() {
        return "User API is working!";
    }

}

