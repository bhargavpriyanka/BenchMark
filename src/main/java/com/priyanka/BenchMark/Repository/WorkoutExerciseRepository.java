package com.priyanka.BenchMark.Repository;

import com.priyanka.BenchMark.Entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise,Long> {

}
