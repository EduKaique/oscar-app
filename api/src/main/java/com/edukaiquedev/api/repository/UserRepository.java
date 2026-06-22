package com.edukaiquedev.api.repository;

import com.edukaiquedev.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // O JPA deriva o SELECT automaticamente pelo nome do método; não precisa de @Query
    Optional<User> findByLogin(String login);
}
