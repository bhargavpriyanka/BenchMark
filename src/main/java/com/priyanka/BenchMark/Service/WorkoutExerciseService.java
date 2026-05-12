package com.priyanka.BenchMark.Service;

import com.priyanka.BenchMark.Entity.Exercise;
import com.priyanka.BenchMark.Entity.WorkoutExercise;
import com.priyanka.BenchMark.Entity.WorkoutSession;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.ExerciseRepository;
import com.priyanka.BenchMark.Repository.WorkoutExerciseRepository;
import com.priyanka.BenchMark.Repository.WorkoutSessionRepository;
import com.priyanka.BenchMark.dto.request.WorkoutExerciseRequest;
import com.priyanka.BenchMark.dto.response.WorkoutExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSessionRepository workoutSessionRepository;

    // Creates a new workout exercise and saves it to the database
    //Throws ResourceNotFoundException if exercise or workout session not found
    public WorkoutExerciseResponse createWorkoutExercise(WorkoutExerciseRequest workoutExerciseRequest) {
        WorkoutExercise workoutExercise = new WorkoutExercise();

        Exercise exercise = exerciseRepository.findById(workoutExerciseRequest.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found" +  workoutExerciseRequest.getExerciseId()));

        WorkoutSession workoutSession = workoutSessionRepository.findById(workoutExerciseRequest.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Workout session not found" +  workoutExerciseRequest.getSessionId()));
        workoutExercise.setReps(workoutExerciseRequest.getReps());
        workoutExercise.setSets(workoutExerciseRequest.getSets());
        workoutExercise.setExercise(exercise);
        workoutExercise.setSession(workoutSession);
        workoutExercise.setWeight(workoutExerciseRequest.getWeight());

        WorkoutExercise saved =  workoutExerciseRepository.save(workoutExercise);
        //saved entity to response
        return new WorkoutExerciseResponse(saved.getId(), saved.getExercise().getName(), saved.getSession().getId(), saved.getSets(), saved.getReps(), saved.getWeight());    }

    //Returns a workout exercise by id
    //Throws ResourceNotFoundException if the id doesn't exist
    public WorkoutExerciseResponse findWorkoutExerciseById(Long id) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout exercise not found" +  id));

        return new WorkoutExerciseResponse(workoutExercise.getId(), workoutExercise.getExercise().getName(), workoutExercise.getSession().getId(), workoutExercise.getSets(), workoutExercise.getReps(), workoutExercise.getWeight());
    }




    //Returns a list of response DTO for all workout exercises
    public List<WorkoutExerciseResponse> findAllWorkoutExercises() {
        return workoutExerciseRepository.findAll()
                .stream()
                .map(workoutExercise -> new WorkoutExerciseResponse(workoutExercise.getId(), workoutExercise.getExercise().getName(), workoutExercise.getSession().getId(), workoutExercise.getSets(), workoutExercise.getReps(), workoutExercise.getWeight()))
                .toList();
    }
}

