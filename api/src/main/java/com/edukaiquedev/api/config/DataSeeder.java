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
    CommandLineRunner iniciarBanco(UserRepository repository) {
        return args -> {
            // Só popula se o banco estiver vazio para não duplicar ao reiniciar o container
            if (repository.count() == 0) {
                // Os 3 primeiros já votaram (votou=true); ana e pedro ainda não (votou=false)
                // Isso permite testar o fluxo de votação com ana e pedro
                repository.saveAll(List.of(
                    new User(null, "admin",  "admin123", true),
                    new User(null, "maria",  "maria123", true),
                    new User(null, "joao",   "joao123",  true),
                    new User(null, "ana",    "ana123",   false),
                    new User(null, "pedro",  "pedro123", false)
                ));
            }
        };
    }
}
