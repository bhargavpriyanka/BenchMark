package com.priyanka.BenchMark.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MuscleRequest {

    @NotBlank(message = "Muscle name is required")
    private String name;
}
