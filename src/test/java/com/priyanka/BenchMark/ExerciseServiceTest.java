package com.priyanka.BenchMark;

import com.priyanka.BenchMark.Entity.Category;
import com.priyanka.BenchMark.Entity.Exercise;
import com.priyanka.BenchMark.Entity.Muscle;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.CategoryRepository;
import com.priyanka.BenchMark.Repository.ExerciseRepository;
import com.priyanka.BenchMark.Repository.MuscleRepository;
import com.priyanka.BenchMark.Service.ExerciseService;
import com.priyanka.BenchMark.dto.request.ExerciseRequest;
import com.priyanka.BenchMark.dto.response.ExerciseResponse;
import com.priyanka.BenchMark.dto.response.MuscleResponse;
import jakarta.validation.constraints.AssertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {
    @Mock
    ExerciseRepository exerciseRepository;
    @Mock
    MuscleRepository muscleRepository;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    ExerciseService exerciseService;


    @Test
    void createExercise_ReturnsExercise(){
        Category cat = new Category();
        cat.setName("Upper Body");
        cat.setId(1L);

        Muscle muscleOne =  new Muscle();
        muscleOne .setId(1L);
        muscleOne .setName("Biceps Brachii");

        Muscle muscleTwo  =  new Muscle();
        muscleTwo.setId(2L);
        muscleTwo.setName("Brachialis");


        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName("Bicep Curl");
        exerciseRequest.setIsMachine(false);
        exerciseRequest.setMuscleIds(Arrays.asList(1L, 2L));
        exerciseRequest.setCategoryId(1L);

        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Bicep Curl");
        exercise.setIsMachine(false);
        exercise.setCategory(cat);
        exercise.setMuscles((Arrays.asList(muscleOne, muscleTwo)));

        when(exerciseRepository.existsByName("Bicep Curl")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(muscleRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(muscleOne, muscleTwo));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        ExerciseResponse exerciseResponse = exerciseService.createExercise(exerciseRequest);

        List<String> names = Arrays.asList(muscleOne.getName(), muscleTwo.getName());

        assertNotNull(exerciseResponse);
        assertEquals("Bicep Curl", exerciseResponse.getName());
        assertEquals(1L,  exerciseResponse.getId());
        assertFalse(exerciseResponse.getIsMachine());
        assertEquals("Upper Body", exerciseResponse.getCategoryName());
        assertEquals(names, exerciseResponse.getMuscleName());
    }

    @Test
    void createExercise_shouldThrowException_whenCategoryNotFound(){

        Muscle muscleOne =  new Muscle();
        muscleOne .setId(1L);
        muscleOne .setName("Biceps Brachii");

        Muscle muscleTwo  =  new Muscle();
        muscleTwo.setId(2L);
        muscleTwo.setName("Brachialis");

        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName("Bicep Curl");
        exerciseRequest.setIsMachine(false);
        exerciseRequest.setMuscleIds(Arrays.asList(1L, 2L));
        exerciseRequest.setCategoryId(1L);

        when(exerciseRepository.existsByName("Bicep Curl")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            exerciseService.createExercise(exerciseRequest);
        });

    }

    @Test
    void createExercise_shouldThrowException_whenMuscleNotFound(){
        Category cat = new Category();
        cat.setName("Upper Body");
        cat.setId(1L);

        Muscle muscleOne =  new Muscle();
        muscleOne .setId(1L);
        muscleOne .setName("Biceps Brachii");

        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName("Bicep Curl");
        exerciseRequest.setIsMachine(false);
        exerciseRequest.setMuscleIds(Arrays.asList(1L, 2L));
        exerciseRequest.setCategoryId(1L);

        when(exerciseRepository.existsByName("Bicep Curl")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(muscleRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(muscleOne));

        assertThrows(ResourceNotFoundException.class, () -> {
            exerciseService.createExercise(exerciseRequest);
        });
    }

    @Test
    void createExercise_shouldException(){
        Category cat = new Category();
        cat.setName("Upper Body");
        cat.setId(1L);

        Muscle muscleOne =  new Muscle();
        muscleOne .setId(1L);
        muscleOne .setName("Biceps Brachii");

        Muscle muscleTwo  =  new Muscle();
        muscleTwo.setId(2L);
        muscleTwo.setName("Brachialis");


        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName("Bicep Curl");
        exerciseRequest.setIsMachine(false);
        exerciseRequest.setMuscleIds(Arrays.asList(1L, 2L));
        exerciseRequest.setCategoryId(1L);

        when(exerciseRepository.existsByName("Bicep Curl")).thenReturn(true);

        assertThrows(DuplicateException.class, () -> {
            exerciseService.createExercise(exerciseRequest);
        });
    }

    @Test
    void getExerciseById_ReturnsExercise(){
        Category cat = new Category();
        cat.setName("Upper Body");
        cat.setId(1L);

        Muscle muscleOne =  new Muscle();
        muscleOne .setId(1L);
        muscleOne .setName("Biceps Brachii");

        Muscle muscleTwo  =  new Muscle();
        muscleTwo.setId(2L);
        muscleTwo.setName("Brachialis");


        Exercise exercise = new Exercise();
        exercise.setName("Bicep Curl");
        exercise.setIsMachine(false);
        exercise.setCategory(cat);
        exercise.setMuscles((Arrays.asList(muscleOne, muscleTwo)));
        exercise.setId(1L);

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        ExerciseResponse exerciseResponse = exerciseService.getExerciseById(1L);

        List<String> names = Arrays.asList(muscleOne.getName(), muscleTwo.getName());

        assertNotNull(exerciseResponse);
        assertEquals("Bicep Curl", exerciseResponse.getName());
        assertEquals(1L,  exerciseResponse.getId());
        assertFalse(exerciseResponse.getIsMachine());
        assertEquals("Upper Body", exerciseResponse.getCategoryName());
        assertEquals(names, exerciseResponse.getMuscleName());
    }

    @Test
    void getExerciseById_shouldException(){
        when(exerciseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            exerciseService.getExerciseById(99L);
        });
    }

    @Test
    void getAllExercises_shouldReturnAllExercises(){
        Category cat = new Category();
        cat.setName("Upper Body");
        cat.setId(1L);

        Muscle muscleOne =  new Muscle();
        muscleOne .setId(1L);
        muscleOne .setName("Biceps Brachii");

        Muscle muscleTwo  =  new Muscle();
        muscleTwo.setId(2L);
        muscleTwo.setName("Brachialis");

        Exercise exercise = new Exercise();
        exercise.setName("Bicep Curl");
        exercise.setIsMachine(false);
        exercise.setCategory(cat);
        exercise.setMuscles((Arrays.asList(muscleOne, muscleTwo)));
        exercise.setId(1L);

        Exercise exerciseTwo = new Exercise();
        exerciseTwo.setName("Hammer Curls");
        exerciseTwo.setIsMachine(false);
        exerciseTwo.setCategory(cat);
        exerciseTwo.setMuscles((Arrays.asList(muscleOne, muscleTwo)));
        exerciseTwo.setId(2L);

        when(exerciseRepository.findAll()).thenReturn(Arrays.asList(exercise, exerciseTwo));
        List<ExerciseResponse> exerciseResponseList = exerciseService.getAllExercises();
        List<String> names = Arrays.asList(muscleOne.getName(), muscleTwo.getName());

        assertNotNull(exerciseResponseList);
        assertEquals(2, exerciseResponseList.size());
        assertEquals("Bicep Curl", exerciseResponseList.get(0).getName());
        assertEquals("Hammer Curls", exerciseResponseList.get(1).getName());
        assertEquals(names, exerciseResponseList.get(0).getMuscleName());
        assertEquals(names, exerciseResponseList.get(1).getMuscleName());
        assertEquals(1L, exerciseResponseList.get(0).getId());
        assertEquals(2L, exerciseResponseList.get(1).getId());
        assertFalse(exerciseResponseList.get(0).getIsMachine());
        assertFalse(exerciseResponseList.get(1).getIsMachine());
        assertEquals("Upper Body", exerciseResponseList.get(0).getCategoryName());
        assertEquals("Upper Body", exerciseResponseList.get(1).getCategoryName());

    }

    @Test
    void getAllExercises_shouldReturnEmptyList(){
        when(exerciseRepository.findAll()).thenReturn(List.of());

        List<ExerciseResponse> response = exerciseService.getAllExercises();

        assertNotNull(response);
        assertEquals(0, response.size());
    }

}
