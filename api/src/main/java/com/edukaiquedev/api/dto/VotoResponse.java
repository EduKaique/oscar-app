package com.edukaiquedev.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotoResponse {
    private String mensagem;
    private boolean sucesso;
    // null = sucesso; 1 = token inválido; 2 = já votou; 3 = usuário não encontrado
    private Integer codigoErro;
}
