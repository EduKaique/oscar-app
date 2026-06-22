package com.edukaiquedev.api.dto;

import lombok.Data;

@Data
public class VotoRequest {
    private String idFilme;
    private String idDiretor;
    private Integer token;
}
