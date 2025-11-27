package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import vn.iotstar.service.IStorageService;
import vn.iotstar.config.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringbootThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootThymeleafApplication.class, args);
    }

    @Bean
    CommandLineRunner init(IStorageService storageService) {
        return args -> {
            storageService.init();
        };
    }
}
