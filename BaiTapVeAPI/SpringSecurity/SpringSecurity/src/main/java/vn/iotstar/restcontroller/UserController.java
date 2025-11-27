/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.iotstar.restcontroller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.UserInfoRepository;

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
