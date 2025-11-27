package org.example.springboot_jwt.controller;

import org.example.springboot_jwt.entity.User;
import org.example.springboot_jwt.models.LoginResponse;
import org.example.springboot_jwt.models.LoginUserModel;
import org.example.springboot_jwt.models.RegisterUserModel;
import org.example.springboot_jwt.service.AuthenticationService;
import org.example.springboot_jwt.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService,
                                    AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUser) {
        User registeredUser = authenticationService.register(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserModel loginUser) {
        User user = authenticationService.authenticate(loginUser);

        String jwtToken = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setExpiresIn(System.currentTimeMillis() + 3600000); // demo

        return ResponseEntity.ok(response);
    }
}
