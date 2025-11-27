package org.example.springsecurity_demo2.config;

import lombok.RequiredArgsConstructor;
import org.example.springsecurity_demo2.service.UserInfoDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // inject service do bạn viết
    private final UserInfoDetailsService userInfoDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ❗ Quan trọng: dùng constructor có UserDetailsService, không setUserDetailsService
    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userInfoDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // các trang public
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",        // nếu sau này có trang đăng ký
                                "/api/users/create",
                                "/api/users/hello",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()
                        // các request khác phải login
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")              // GET /login -> login.html
                        .loginProcessingUrl("/login")     // POST form action="/login"
                        .defaultSuccessUrl("/home", true) // login xong thì vào /home
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
