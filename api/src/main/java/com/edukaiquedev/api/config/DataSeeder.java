package com.edukaiquedev.api.config;

import com.edukaiquedev.api.model.User;
import com.edukaiquedev.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner iniciarBanco(UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                    new User(null, "admin",  encoder.encode("senha123"), true,  null, null, null),
                    new User(null, "maria",  encoder.encode("senha123"), true,  null, null, null),
                    new User(null, "joao",   encoder.encode("senha123"), true,  null, null, null),
                    new User(null, "ana",    encoder.encode("senha123"), false, null, null, null),
                    new User(null, "pedro",  encoder.encode("senha123"), false, null, null, null)
                ));
            }
        };
    }
}
