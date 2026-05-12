package com.priyanka.BenchMark.Repository;

import com.priyanka.BenchMark.Entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
    boolean existsByName(String name);
}
