package com.edukaiquedev.api.controller;

import com.edukaiquedev.api.dto.LoginRequest;
import com.edukaiquedev.api.dto.LoginResponse;
import com.edukaiquedev.api.model.User;
import com.edukaiquedev.api.repository.UserRepository;
import com.edukaiquedev.api.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginRequest req) {
        Optional<User> usuario = userRepository.findByLogin(req.getLogin());

        if (usuario.isPresent() && passwordEncoder.matches(req.getSenha(), usuario.get().getSenha())) {
            int tokenVotacao = new Random().nextInt(101);
            usuario.get().setTokenVotacao(tokenVotacao);
            userRepository.save(usuario.get());

            String token = jwtService.gerarToken(usuario.get().getLogin());
            return ResponseEntity.ok(new LoginResponse("Login realizado com sucesso!", true, token, tokenVotacao));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse("Usuário ou senha incorretos.", false, null, null));
    }
}
