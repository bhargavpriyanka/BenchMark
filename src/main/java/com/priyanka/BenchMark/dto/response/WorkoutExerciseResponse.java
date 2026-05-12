package com.priyanka.BenchMark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseResponse {
    private Long id;
    private String exerciseName;
    private Long workoutSessionId;
    private Integer sets;
    private Integer reps;
    private Double weight;


}
