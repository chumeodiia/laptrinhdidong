package org.example.springsecurity_demo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // dung @preauthorize

public class SecurityConfig {
    // khai bao 2 user cung : 1 amdin , 1 user de test
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("123"))
                .authorities("ROLE_ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("123"))
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
    // ma hoa mat khau
    @Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // demo API nên tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        // /customer/hello cho phép tất cả truy cập
                        .requestMatchers("/customer/hello").permitAll()
                        // các URL còn lại bắt buộc login
                        .anyRequest().authenticated()
                )
                // dùng form login mặc định của Spring Security
                .formLogin(form -> form.permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
