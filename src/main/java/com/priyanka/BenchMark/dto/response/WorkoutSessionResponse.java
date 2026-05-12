package com.priyanka.BenchMark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionResponse {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalDate date;
    private String description;
    private String userName;
}
