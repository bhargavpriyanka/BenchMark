package com.priyanka.BenchMark;

import com.priyanka.BenchMark.Entity.User;
import com.priyanka.BenchMark.Entity.WorkoutSession;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.UserRepository;
import com.priyanka.BenchMark.Repository.WorkoutSessionRepository;
import com.priyanka.BenchMark.Service.ExerciseService;
import com.priyanka.BenchMark.Service.WorkoutSessionService;
import com.priyanka.BenchMark.dto.request.WorkoutSessionRequest;
import com.priyanka.BenchMark.dto.response.MuscleResponse;
import com.priyanka.BenchMark.dto.response.WorkoutSessionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutSessionServiceTest {
    @Mock
    private WorkoutSessionRepository workoutSessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WorkoutSessionService workoutSessionService;



    @Test
    void createWorkoutSession_shouldReturnWorkoutSession() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("person");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setId(1L);
        user.setUsername("person");
        user.setName("Person");

        WorkoutSessionRequest request = new WorkoutSessionRequest();
        request.setName("Upper Body A");
        request.setDate(LocalDate.of(2025, 3, 23));
        request.setStartTime(LocalTime.of(11, 0));
        request.setDescription("Upper Body workout focusing on arms and chest");

        WorkoutSession saved = new WorkoutSession();
        saved.setId(1L);
        saved.setName("Upper Body A");
        saved.setDate(LocalDate.of(2025, 3, 23));
        saved.setStartTime(LocalTime.of(11, 0));
        saved.setDescription("Upper Body workout focusing on arms and chest");
        saved.setUser(user);

        when(userRepository.findByUsername("person")).thenReturn(Optional.of(user));
        when(workoutSessionRepository.save(any(WorkoutSession.class))).thenReturn(saved);

        WorkoutSessionResponse response = workoutSessionService.createWorkoutSession(request);

        assertNotNull(response);
        assertEquals("Upper Body A", response.getName());
        assertEquals(LocalDate.of(2025, 3, 23), response.getDate());
        assertEquals(LocalTime.of(11, 0), response.getStartTime());
        assertEquals("Upper Body workout focusing on arms and chest", response.getDescription());
        assertEquals(1L, response.getId());
        assertEquals("person", response.getUserName());
    }

    @Test
    void createWorkoutSession_shouldThrowException_whenUserNotFound(){
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("person");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername("person")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            workoutSessionService.createWorkoutSession(new WorkoutSessionRequest());
        });
    }

    @Test
    void getWorkoutSessionById_shouldReturnWorkoutSession() {

        User user = new User();
        user.setId(1L);
        user.setUsername("person");
        user.setName("Person");

        WorkoutSession saved = new WorkoutSession();
        saved.setId(1L);
        saved.setName("Upper Body A");
        saved.setDate(LocalDate.of(2025, 3, 23));
        saved.setStartTime(LocalTime.of(11, 0));
        saved.setDescription("Upper Body workout focusing on arms and chest");
        saved.setUser(user);

        when(workoutSessionRepository.findById(1L)).thenReturn(Optional.of(saved));

        WorkoutSessionResponse response = workoutSessionService.findWorkoutSessionById(1L);

        assertNotNull(response);
        assertEquals("Upper Body A", response.getName());
        assertEquals(LocalDate.of(2025, 3, 23), response.getDate());
        assertEquals(LocalTime.of(11, 0), response.getStartTime());
        assertEquals("Upper Body workout focusing on arms and chest", response.getDescription());
        assertEquals(1L, response.getId());
        assertEquals("person", response.getUserName());

    }

    @Test
    void findWorkoutSessionById_shouldThrowException(){
        when(workoutSessionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            workoutSessionService.findWorkoutSessionById(99L);
        });
    }

    @Test
    void findAllWorkoutSessions_shouldReturnAllSessions(){
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("person");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setId(1L);
        user.setUsername("person");
        user.setName("Person");

        WorkoutSession saved = new WorkoutSession();
        saved.setId(1L);
        saved.setName("Upper Body A");
        saved.setDate(LocalDate.of(2025, 3, 23));
        saved.setStartTime(LocalTime.of(11, 0));
        saved.setDescription("Upper Body workout focusing on arms and chest");
        saved.setUser(user);

        WorkoutSession savedTwo = new WorkoutSession();
        savedTwo.setId(2L);
        savedTwo.setName("Lower Body A");
        savedTwo.setDate(LocalDate.of(2025, 8, 23));
        savedTwo.setStartTime(LocalTime.of(11, 0));
        savedTwo.setDescription("Lower Body workout");
        savedTwo.setUser(user);

        when(workoutSessionRepository.findByUser_Username("person")).thenReturn(Arrays.asList(saved, savedTwo));

        List<WorkoutSessionResponse> response = workoutSessionService.findAllWorkoutSessions();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Upper Body A", response.get(0).getName());
        assertEquals("Lower Body A", response.get(1).getName());

        assertEquals("person", response.get(0).getUserName());
        assertEquals("person", response.get(1).getUserName());

        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());

        assertEquals(LocalDate.of(2025, 3, 23),  response.get(0).getDate());
        assertEquals(LocalDate.of(2025, 8, 23),  response.get(1).getDate());

        assertEquals(LocalTime.of(11, 0),  response.get(0).getStartTime());
        assertEquals(LocalTime.of(11, 0),  response.get(1).getStartTime());

    }

    @Test
    void findAllWorkoutSessions_shouldReturnEmptyList(){
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("person");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setId(1L);
        user.setUsername("person");
        user.setName("Person");

        when(workoutSessionRepository.findByUser_Username("person")).thenReturn(Collections.emptyList());
        List<WorkoutSessionResponse> response = workoutSessionService.findAllWorkoutSessions();

        assertNotNull(response);
        assertEquals(0, response.size());
    }
}
