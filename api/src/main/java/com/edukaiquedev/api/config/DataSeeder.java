package com.edukaiquedev.api.config;

import com.edukaiquedev.api.model.User;
import com.edukaiquedev.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                    new User(null, "admin", "admin123", true),
                    new User(null, "user1", "pass1", true),
                    new User(null, "user2", "pass2", true),
                    new User(null, "user3", "pass3", false),
                    new User(null, "user4", "pass4", false)
                ));
                System.out.println("Banco de dados semeado com 5 usuários.");
            }
        };
    }
}
