package org.example.springboot_jwt.service;

import org.example.springboot_jwt.entity.User;
import org.example.springboot_jwt.models.LoginUserModel;
import org.example.springboot_jwt.models.RegisterUserModel;
import org.example.springboot_jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // Đăng ký
    public User register(RegisterUserModel input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setFullName(input.getFullName());

        // CHỈ encode ở đây khi lưu DB
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Xác thực đăng nhập
    public User authenticate(LoginUserModel request) {
        System.out.println("Login with: " + request.getEmail() + " / " + request.getPassword());
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        return (User) auth.getPrincipal();
    }

}
