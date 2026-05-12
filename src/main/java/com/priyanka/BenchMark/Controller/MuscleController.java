package com.priyanka.BenchMark.Controller;

import com.priyanka.BenchMark.Service.MuscleService;
import com.priyanka.BenchMark.dto.request.ExerciseRequest;
import com.priyanka.BenchMark.dto.request.MuscleRequest;
import com.priyanka.BenchMark.dto.response.ExerciseResponse;
import com.priyanka.BenchMark.dto.response.MuscleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Handles muscle endpoints
@RestController
@RequestMapping("/api/muscles")
@RequiredArgsConstructor
public class MuscleController {
    private final MuscleService muscleService;

    //Creates a new muscle, @Valid triggers validation on the request body
    @PostMapping
    public ResponseEntity<MuscleResponse> createMuscle(@Valid @RequestBody MuscleRequest request) {
        MuscleResponse muscleResponse = muscleService.createMuscle(request);
        return new ResponseEntity<>(muscleResponse, HttpStatus.CREATED);
    }

    //Returns one muscle by its id and throws ResourceNotFoundException if id is not found
    @GetMapping("/{id}")
    public ResponseEntity<MuscleResponse> getMuscleById(@PathVariable Long id) {
        MuscleResponse muscleResponse = muscleService.getMuscleById(id);
        return new ResponseEntity<>(muscleResponse, HttpStatus.OK);
    }

    // Returns all muscles
    @GetMapping
    public ResponseEntity<List<MuscleResponse>> getAllMuscles() {
        List<MuscleResponse> muscleResponses = muscleService.getAllMuscles();
        return new ResponseEntity<>(muscleResponses, HttpStatus.OK);
    }
}
