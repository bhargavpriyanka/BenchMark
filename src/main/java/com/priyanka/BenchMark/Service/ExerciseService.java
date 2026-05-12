package com.priyanka.BenchMark.Service;

import com.priyanka.BenchMark.Entity.Category;
import com.priyanka.BenchMark.Entity.Exercise;
import com.priyanka.BenchMark.Entity.Muscle;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.CategoryRepository;
import com.priyanka.BenchMark.Repository.ExerciseRepository;
import com.priyanka.BenchMark.Repository.MuscleRepository;
import com.priyanka.BenchMark.dto.request.ExerciseRequest;
import com.priyanka.BenchMark.dto.response.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final CategoryRepository categoryRepository;
    private final MuscleRepository muscleRepository;

    // Creates a new exercise and saves it to the database
    // Throws DuplicateException if a exercise with the same name already exists
    public ExerciseResponse createExercise(ExerciseRequest exerciseRequest) {
        if(exerciseRepository.existsByName(exerciseRequest.getName())){
            throw new DuplicateException("Exercise already exists " + exerciseRequest.getName());
        }

        Category category = categoryRepository.findById(exerciseRequest.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + exerciseRequest.getCategoryId()));

        List<Long> muscleIds = exerciseRequest.getMuscleIds();
        List<Muscle> muscles = muscleRepository.findAllById(muscleIds);
        if (muscles.size() != muscleIds.size()) {
            throw new ResourceNotFoundException("One or more muscles not found");
        }

        Exercise exercise = new Exercise();
        exercise.setName(exerciseRequest.getName());
        exercise.setIsMachine(exerciseRequest.getIsMachine());
        exercise.setCategory(category);
        exercise.setMuscles(muscles);
        Exercise saved = exerciseRepository.save(exercise);

        List<String> muscleNames = saved.getMuscles().stream().map(Muscle::getName).toList();
        //saved entity to response
        return new ExerciseResponse(saved.getId(), saved.getName(), muscleNames, saved.getCategory().getName(), saved.getIsMachine());
    }

    //Returns an exercise by id
    //Throws ResourceNotFoundException if the id doesn't exist
    public ExerciseResponse getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Exercise not found with id: " + id));
        List<String> muscleNames = exercise.getMuscles().stream().map(Muscle::getName).toList();

        return new ExerciseResponse(exercise.getId(),exercise.getName(),muscleNames,exercise.getCategory().getName(),exercise.getIsMachine());
    }

    //Returns a list of response DTO for all exercises
    public List<ExerciseResponse> getAllExercises() {
        return exerciseRepository.findAll()
                .stream().map( exercise ->
                        new ExerciseResponse(
                                exercise.getId(),exercise.getName(),(exercise.getMuscles().stream().map(Muscle::getName).toList()),exercise.getCategory().getName(),exercise.getIsMachine()
                        )
                ).toList();
    }
}
