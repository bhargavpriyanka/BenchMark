package com.priyanka.BenchMark.Service;

import com.priyanka.BenchMark.Entity.Exercise;
import com.priyanka.BenchMark.Entity.User;
import com.priyanka.BenchMark.Entity.WorkoutSession;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.ExerciseRepository;
import com.priyanka.BenchMark.Repository.UserRepository;
import com.priyanka.BenchMark.Repository.WorkoutSessionRepository;
import com.priyanka.BenchMark.dto.request.WorkoutSessionRequest;
import com.priyanka.BenchMark.dto.response.WorkoutSessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserRepository userRepository;

    // Creates a new workout session and saves it to the database
    // Throws ResourceNotFoundException if user not found
    public WorkoutSessionResponse createWorkoutSession(WorkoutSessionRequest request) {

        WorkoutSession workoutSession = new WorkoutSession();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        workoutSession.setUser(user);
        workoutSession.setDate(request.getDate());
        workoutSession.setDescription(request.getDescription());
        workoutSession.setStartTime(request.getStartTime());
        workoutSession.setName(request.getName());

        WorkoutSession saved =  workoutSessionRepository.save(workoutSession);
        //saved entity to response
        return new WorkoutSessionResponse(saved.getId(), saved.getName(), saved.getStartTime(),saved.getDate(),saved.getDescription(), saved.getUser().getUsername());
    }

    //Returns a workout session by id
    //Throws ResourceNotFoundException if the id doesn't exist
    public WorkoutSessionResponse findWorkoutSessionById(Long id) {
        WorkoutSession workoutSession = workoutSessionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("WorkoutSession not found" + id));
        return new WorkoutSessionResponse(workoutSession.getId(), workoutSession.getName(), workoutSession.getStartTime(),workoutSession.getDate(),workoutSession.getDescription(), workoutSession.getUser().getUsername());
    }

    //Returns a list of response DTO for all categories for user
    public List<WorkoutSessionResponse> findAllWorkoutSessions(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return workoutSessionRepository.findByUser_Username(username)
                .stream()
                .map(workoutSession -> new WorkoutSessionResponse(
                        workoutSession.getId(),
                        workoutSession.getName(),
                        workoutSession.getStartTime(),
                        workoutSession.getDate(),
                        workoutSession.getDescription(),
                        workoutSession.getUser().getUsername()
                ))
                .toList();
    }



}
