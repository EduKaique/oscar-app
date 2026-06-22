package com.edukaiquedev.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @PostMapping
    public ResponseEntity<Map<String, String>> logout() {
        // JWT é stateless — o logout é responsabilidade do cliente (apagar o token).
        // Este endpoint existe para formalizar o contrato da API.
        return ResponseEntity.ok(Map.of("mensagem", "Logout realizado com sucesso"));
    }
}
