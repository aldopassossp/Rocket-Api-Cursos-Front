package com.aldopassos.cursos_front.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Token {

    @JsonProperty("token")
    private String token;
    @JsonProperty("expirationDate")
    private String expirationDate;
    @JsonProperty("role")
    private String role;

}
