package org.example.springboot_jwt.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token; // chuoi jwt
    private long expiresIn; // tgian het han
}
