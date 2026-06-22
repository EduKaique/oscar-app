package com.edukaiquedev.api.controller;

import com.edukaiquedev.api.dto.LoginRequest;
import com.edukaiquedev.api.dto.LoginResponse;
import com.edukaiquedev.api.model.User;
import com.edukaiquedev.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint de autenticação via POST.
     * Recebe as credenciais, busca no banco de dados PostgreSQL (via Spring Data JPA)
     * e valida a senha para permitir o acesso ao aplicativo.
     */
    @PostMapping
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Login realizado com sucesso!", true));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse("Credenciais inválidas", false));
    }
}
