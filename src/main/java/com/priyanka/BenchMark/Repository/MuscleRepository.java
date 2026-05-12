package com.priyanka.BenchMark.Repository;

import com.priyanka.BenchMark.Entity.Category;
import com.priyanka.BenchMark.Entity.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuscleRepository extends JpaRepository<Muscle,Long> {
    boolean existsByName(String name);

}
