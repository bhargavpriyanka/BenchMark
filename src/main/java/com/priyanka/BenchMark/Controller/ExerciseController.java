package com.priyanka.BenchMark.Controller;

import com.priyanka.BenchMark.Service.ExerciseService;
import com.priyanka.BenchMark.dto.request.ExerciseRequest;
import com.priyanka.BenchMark.dto.response.ExerciseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Handles exercise endpoints
@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    //Creates a new exercise, @Valid triggers validation on the request body
    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(@Valid @RequestBody ExerciseRequest request) {
        ExerciseResponse exerciseResponse = exerciseService.createExercise(request);
        return new ResponseEntity<>(exerciseResponse, HttpStatus.CREATED);
    }

    //Returns one exercise by its id and throws ResourceNotFoundException if id is not found
    @GetMapping ("/{id}")
    public ResponseEntity<ExerciseResponse> getExerciseById(@PathVariable Long id) {
        ExerciseResponse exerciseResponse = exerciseService.getExerciseById(id);
        return new ResponseEntity<>(exerciseResponse, HttpStatus.OK);
    }

    //Returns all exercises
    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getAllExercises() {
        List<ExerciseResponse> exerciseResponses = exerciseService.getAllExercises();
        return new ResponseEntity<>(exerciseResponses, HttpStatus.OK);
    }



}
