package com.priyanka.BenchMark.Repository;

import com.priyanka.BenchMark.Entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {
    List<WorkoutSession> findByUser_Username(String username);

}
