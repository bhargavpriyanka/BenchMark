package com.priyanka.BenchMark.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkoutExerciseRequest {

    @NotNull(message = "Exercise is required")
    private Long exerciseId;

    @NotNull(message = "Session is required")
    private Long sessionId;

    @NotNull(message = "Sets is required")
    private Integer sets;

    @NotNull(message = "Reps is required")
    private Integer reps;

    @NotNull(message = "Weight is required")
    private Double weight;

}
