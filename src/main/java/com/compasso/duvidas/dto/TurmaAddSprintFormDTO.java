package com.compasso.duvidas.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TurmaAddSprintFormDTO {
    @NotNull
    private List<Long> sprintsIds;
}
