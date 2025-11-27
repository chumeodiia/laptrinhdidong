package org.example.springsecurity_demo3_thymeleaf;

import lombok.RequiredArgsConstructor;
import org.example.springsecurity_demo3_thymeleaf.entity.Product;
import org.example.springsecurity_demo3_thymeleaf.entity.Role;
import org.example.springsecurity_demo3_thymeleaf.entity.User;
import org.example.springsecurity_demo3_thymeleaf.repository.ProductRepository;
import org.example.springsecurity_demo3_thymeleaf.repository.RoleRepository;
import org.example.springsecurity_demo3_thymeleaf.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInit {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData(RoleRepository roleRepo,
                               UserRepository userRepo,
                               ProductRepository productRepo) {
        return args -> {
            if (roleRepo.count() == 0) {
                Role admin = roleRepo.save(Role.builder().name("ROLE_ADMIN").build());
                Role user = roleRepo.save(Role.builder().name("ROLE_USER").build());

                userRepo.save(User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123"))
                        .roles(Set.of(admin, user))
                        .build());

                userRepo.save(User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("123"))
                        .roles(Set.of(user))
                        .build());
            }

            if (productRepo.count() == 0) {
                productRepo.save(Product.builder().name("Sản phẩm A").price(100000).build());
                productRepo.save(Product.builder().name("Sản phẩm B").price(200000).build());
            }
        };
    }
}
