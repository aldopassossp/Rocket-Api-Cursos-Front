package com.aldopassos.cursos_front.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CursoInsertDTO {

    private String id;
    private String name;
    private String category;
    private String active;
    private Date createdAt;
    private Date updatedAt;
    private Long professorId;
}
