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

    @PostMapping
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginRequest req) {
        Optional<User> usuario = userRepository.findByLogin(req.getLogin());

        // Compara senha em texto plano
        if (usuario.isPresent() && usuario.get().getSenha().equals(req.getSenha())) {
            return ResponseEntity.ok(new LoginResponse("Login realizado com sucesso!", true));
        }

        // 401 para credencial errada; 404 exporia quais logins existem
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse("Usuário ou senha incorretos.", false));
    }
}
