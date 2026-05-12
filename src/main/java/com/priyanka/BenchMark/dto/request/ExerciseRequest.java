package com.priyanka.BenchMark.dto.request;

import com.priyanka.BenchMark.Entity.Muscle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseRequest {

    @NotNull(message = "Is machine or not required")
    private Boolean isMachine;


    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotBlank(message = "Exercise name is required")
    private String name;

    @NotNull(message = "Muscles required")
    private List<Long> muscleIds;


}
