package com.edukaiquedev.api.controller;

import com.edukaiquedev.api.dto.VotoRequest;
import com.edukaiquedev.api.dto.VotoResponse;
import com.edukaiquedev.api.model.User;
import com.edukaiquedev.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/voto")
public class VotoController {

    private final UserRepository userRepository;

    public VotoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<VotoResponse> confirmarVoto(@RequestBody VotoRequest req) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> usuarioOpt = userRepository.findByLogin(login);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new VotoResponse("Usuário não encontrado", false, 3));
        }

        User usuario = usuarioOpt.get();

        if (usuario.isVotou()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new VotoResponse("Você já confirmou seu voto anteriormente", false, 2));
        }

        if (!req.getToken().equals(usuario.getTokenVotacao())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new VotoResponse("Token de votação inválido", false, 1));
        }

        usuario.setIdFilmeVotado(req.getIdFilme());
        usuario.setIdDiretorVotado(req.getIdDiretor());
        usuario.setVotou(true);
        userRepository.save(usuario);

        return ResponseEntity.ok(new VotoResponse("Votos registrados com sucesso!", true, null));
    }
}
