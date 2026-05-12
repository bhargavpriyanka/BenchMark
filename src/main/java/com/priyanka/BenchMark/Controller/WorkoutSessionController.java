package com.priyanka.BenchMark.Controller;

import com.priyanka.BenchMark.Service.WorkoutExerciseService;
import com.priyanka.BenchMark.Service.WorkoutSessionService;
import com.priyanka.BenchMark.dto.request.WorkoutExerciseRequest;
import com.priyanka.BenchMark.dto.request.WorkoutSessionRequest;
import com.priyanka.BenchMark.dto.response.WorkoutExerciseResponse;
import com.priyanka.BenchMark.dto.response.WorkoutSessionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Handles workout session endpoints
@RestController
@RequestMapping("/api/workout-session")
@RequiredArgsConstructor
public class WorkoutSessionController {
    private final WorkoutSessionService workoutSessionService;

    //Creates a new workout session, @Valid triggers validation on the request body
    @PostMapping
    public ResponseEntity<WorkoutSessionResponse> createWorkoutSession(@Valid @RequestBody WorkoutSessionRequest request) {
        WorkoutSessionResponse workoutSessionSResponse = workoutSessionService.createWorkoutSession(request);
        return new ResponseEntity<>(workoutSessionSResponse, HttpStatus.CREATED);
    }

    //Returns one workout session by its id
    // throws ResourceNotFoundException if id is not found
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSessionResponse> getWorkoutSessionById(@PathVariable Long id) {
        WorkoutSessionResponse workoutSessionResponse = workoutSessionService.findWorkoutSessionById(id);
        return new ResponseEntity<>(workoutSessionResponse, HttpStatus.OK);
    }

    //Returns all workout sessions
    @GetMapping
    public ResponseEntity<List<WorkoutSessionResponse>> getAllSessions() {

        List<WorkoutSessionResponse> responses = workoutSessionService.findAllWorkoutSessions();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
