package com.priyanka.BenchMark.Controller;

import com.priyanka.BenchMark.Service.ExerciseService;
import com.priyanka.BenchMark.Service.WorkoutExerciseService;
import com.priyanka.BenchMark.dto.request.ExerciseRequest;
import com.priyanka.BenchMark.dto.request.WorkoutExerciseRequest;
import com.priyanka.BenchMark.dto.response.ExerciseResponse;
import com.priyanka.BenchMark.dto.response.WorkoutExerciseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Handles workout exercise endpoints
@RestController
@RequestMapping("/api/workout-exercise")
@RequiredArgsConstructor
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;

    //Creates a new workout exercise @Valid triggers validation on the request body
    @PostMapping
    public ResponseEntity<WorkoutExerciseResponse> createWorkoutExercise(@Valid @RequestBody WorkoutExerciseRequest request) {
        WorkoutExerciseResponse workoutExerciseResponse = workoutExerciseService.createWorkoutExercise(request);
        return new ResponseEntity<>(workoutExerciseResponse, HttpStatus.CREATED);
    }

    // Returns one workout exercise by its id
    // throws ResourceNotFoundException if id is not found
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutExerciseResponse> getWorkoutExerciseById(@PathVariable Long id) {
        WorkoutExerciseResponse workoutExerciseResponse = workoutExerciseService.findWorkoutExerciseById(id);
        return new ResponseEntity<>(workoutExerciseResponse, HttpStatus.OK);
    }

    //Returns all workout exercises
    @GetMapping
    public ResponseEntity<List<WorkoutExerciseResponse>> getAllWorkoutExercises() {
        List<WorkoutExerciseResponse> workoutExerciseResponses = workoutExerciseService.findAllWorkoutExercises();
        return new ResponseEntity<>(workoutExerciseResponses, HttpStatus.OK);
    }
}
