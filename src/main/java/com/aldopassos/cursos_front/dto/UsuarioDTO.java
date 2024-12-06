package com.aldopassos.cursos_front.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

    private String id;
    private String login;
    private String email;
    private String senha;
}
