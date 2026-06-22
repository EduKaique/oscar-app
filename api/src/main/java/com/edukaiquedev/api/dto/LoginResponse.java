package com.edukaiquedev.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String mensagem;
    private boolean sucesso;
}
