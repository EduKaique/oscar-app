package com.edukaiquedev.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    // false = ainda pode votar, true = já exerceu o voto
    private boolean votou;

    @Column(name = "token_votacao")
    private Integer tokenVotacao;

    @Column(name = "id_filme_votado")
    private String idFilmeVotado;

    @Column(name = "id_diretor_votado")
    private String idDiretorVotado;
}
