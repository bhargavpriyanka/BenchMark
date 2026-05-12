package com.priyanka.BenchMark.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workout_exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private WorkoutSession session;

    @Column (nullable = false)
    private Integer sets;

    @Column (nullable = false)
    private Integer reps;

    // Weight stored as Double to support decimal value
    @Column
    private Double weight;


}
