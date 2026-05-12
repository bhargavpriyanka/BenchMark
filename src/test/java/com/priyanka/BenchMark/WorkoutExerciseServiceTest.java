package com.priyanka.BenchMark;

import com.priyanka.BenchMark.Entity.*;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.ExerciseRepository;
import com.priyanka.BenchMark.Repository.UserRepository;
import com.priyanka.BenchMark.Repository.WorkoutExerciseRepository;
import com.priyanka.BenchMark.Repository.WorkoutSessionRepository;
import com.priyanka.BenchMark.Service.WorkoutExerciseService;
import com.priyanka.BenchMark.Service.WorkoutSessionService;
import com.priyanka.BenchMark.dto.request.WorkoutExerciseRequest;
import com.priyanka.BenchMark.dto.request.WorkoutSessionRequest;
import com.priyanka.BenchMark.dto.response.MuscleResponse;
import com.priyanka.BenchMark.dto.response.WorkoutExerciseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutExerciseServiceTest {
    @Mock
    private WorkoutExerciseRepository workoutExerciseRepository;
    @Mock
    private WorkoutSessionRepository workoutSessionRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WorkoutSessionService workoutSessionService;

    @InjectMocks
    private WorkoutExerciseService workoutExerciseService;

    private static Exercise getExercise() {
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
        exercise.setId(1L);
        exercise.setName("Bicep Curl");
        exercise.setIsMachine(false);
        exercise.setCategory(cat);
        exercise.setMuscles((Arrays.asList(muscleOne, muscleTwo)));
        return exercise;
    }

    @Test
    void createWorkoutExercise_shouldReturnWorkoutExercise() {
        User user = new User();
        user.setId(1L);
        user.setUsername("person");
        user.setName("Person");

        Exercise exercise = getExercise();

        WorkoutSession session = new WorkoutSession();
        session.setId(1L);
        session.setName("Upper Body A");
        session.setDate(LocalDate.of(2025, 3, 23));
        session.setStartTime(LocalTime.of(11, 0));
        session.setDescription("Upper Body workout focusing on arms and chest");
        session.setUser(user);
        
        WorkoutExerciseRequest request = new WorkoutExerciseRequest();
        request.setExerciseId(exercise.getId());
        request.setWeight(25.00);
        request.setSessionId(session.getId());
        request.setSets(3);
        request.setReps(15);

        WorkoutExercise saved = new WorkoutExercise();
        saved.setExercise(exercise);
        saved.setSession(session);
        saved.setSets(request.getSets());
        saved.setWeight(request.getWeight());
        saved.setReps(request.getReps());
        saved.setId(1L);

        when(exerciseRepository.findById(exercise.getId())).thenReturn(Optional.of(exercise));
        when(workoutSessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        when(workoutExerciseRepository.save(any(WorkoutExercise.class))).thenReturn(saved);

        WorkoutExerciseResponse response = workoutExerciseService.createWorkoutExercise(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Bicep Curl", response.getExerciseName());
        assertEquals(1L, response.getWorkoutSessionId());
        assertEquals(25.00, response.getWeight());
        assertEquals(15, response.getReps());
        assertEquals(3, response.getSets());
    }

    @Test
    void createWorkoutExercise_shouldThrowException_whenExerciseNotFound(){
        WorkoutExerciseRequest request = new WorkoutExerciseRequest();
        request.setExerciseId(99L);

        when(exerciseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            workoutExerciseService.createWorkoutExercise(request);
        });
    }

    @Test
    void createWorkoutExercise_shouldThrowException_whenSessionNotFound(){
        WorkoutExerciseRequest request = new WorkoutExerciseRequest();
        Exercise exercise = getExercise();
        request.setExerciseId(1L);
        request.setSessionId(99L);

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(workoutSessionRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            workoutExerciseService.createWorkoutExercise(request);
        });
    }

    @Test
    void findWorkoutExerciseById_shouldReturnWorkoutExercise(){
        User user = new User();
        user.setId(1L);
        user.setUsername("person");
        user.setName("Person");

        Exercise exercise = getExercise();

        WorkoutSession session = new WorkoutSession();
        session.setId(1L);
        session.setName("Upper Body A");
        session.setDate(LocalDate.of(2025, 3, 23));
        session.setStartTime(LocalTime.of(11, 0));
        session.setDescription("Upper Body workout focusing on arms and chest");
        session.setUser(user);

        WorkoutExercise workout = new WorkoutExercise();
        workout.setExercise(exercise);
        workout.setWeight(25.00);
        workout.setSession(session);
        workout.setSets(3);
        workout.setReps(15);
        workout.setId(1L);

        when(workoutExerciseRepository.findById(1L)).thenReturn(Optional.of(workout));
        WorkoutExerciseResponse response = workoutExerciseService.findWorkoutExerciseById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Bicep Curl", response.getExerciseName());
        assertEquals(1L, response.getWorkoutSessionId());
        assertEquals(25.00, response.getWeight());
        assertEquals(15, response.getReps());
        assertEquals(3, response.getSets());

    }

    @Test
    void findWorkoutExerciseById_shouldThrowException(){
        when(workoutExerciseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            workoutExerciseService.findWorkoutExerciseById(99L);
        });
    }

    @Test
    void findAllWorkoutExercises_shouldReturnAllWorkoutExercises(){

        User user = new User();
        user.setId(1L);
        user.setUsername("person");
        user.setName("Person");

        Exercise exercise = getExercise();

        WorkoutSession session = new WorkoutSession();
        session.setId(1L);
        session.setName("Upper Body A");
        session.setDate(LocalDate.of(2025, 3, 23));
        session.setStartTime(LocalTime.of(11, 0));
        session.setDescription("Upper Body workout focusing on arms and chest");
        session.setUser(user);

        WorkoutExercise workout = new WorkoutExercise();
        workout.setExercise(exercise);
        workout.setWeight(25.00);
        workout.setSession(session);
        workout.setSets(3);
        workout.setReps(15);
        workout.setId(1L);

        WorkoutExercise workoutTwo = new WorkoutExercise();
        workoutTwo.setExercise(exercise);
        workoutTwo.setWeight(45.00);
        workoutTwo.setSession(session);
        workoutTwo.setSets(4);
        workoutTwo.setReps(10);
        workoutTwo.setId(2L);

        when(workoutExerciseRepository.findAll()).thenReturn(Arrays.asList(workout,workoutTwo));

        List<WorkoutExerciseResponse> response = workoutExerciseService.findAllWorkoutExercises();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());

        assertEquals("Bicep Curl", response.get(0).getExerciseName());
        assertEquals("Bicep Curl", response.get(1).getExerciseName());

        assertEquals(25.00, response.get(0).getWeight());
        assertEquals(45.00, response.get(1).getWeight());

        assertEquals(3, response.get(0).getSets());
        assertEquals(4, response.get(1).getSets());

        assertEquals(15, response.get(0).getReps());
        assertEquals(10, response.get(1).getReps());

        assertEquals(1L, response.get(0).getWorkoutSessionId());
        assertEquals(1L, response.get(1).getWorkoutSessionId());

    }

    @Test
    void findAllWorkoutExercises_shouldReturnEmptyList(){
        when(workoutExerciseRepository.findAll()).thenReturn(List.of());

        List<WorkoutExerciseResponse> response = workoutExerciseService.findAllWorkoutExercises();

        assertNotNull(response);
        assertEquals(0, response.size());
    }
}
