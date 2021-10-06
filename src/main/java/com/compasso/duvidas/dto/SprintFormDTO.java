package com.compasso.duvidas.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SprintFormDTO {
    @NotEmpty
    private String titulo;

    private List<Long> cursosIds;
}
