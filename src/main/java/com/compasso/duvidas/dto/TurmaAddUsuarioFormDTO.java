package com.compasso.duvidas.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TurmaAddUsuarioFormDTO {
    @NotNull
    private Long turmaId;
    @NotNull
    private Long usuarioId;
}
