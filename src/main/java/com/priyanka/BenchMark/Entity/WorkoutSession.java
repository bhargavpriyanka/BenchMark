package com.priyanka.BenchMark.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "workout_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSession {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column (nullable = false)
    private LocalTime startTime;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;


}
