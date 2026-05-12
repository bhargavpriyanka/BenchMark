package com.priyanka.BenchMark;


import com.priyanka.BenchMark.Entity.Muscle;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.MuscleRepository;
import com.priyanka.BenchMark.Service.MuscleService;
import com.priyanka.BenchMark.dto.request.MuscleRequest;
import com.priyanka.BenchMark.dto.response.MuscleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MuscleServiceTest {
    @Mock
    private MuscleRepository muscleRepository;

    @InjectMocks
    private MuscleService muscleService;

    @Test
    void createMuscle_shouldReturnMuscle() {

        MuscleRequest request = new MuscleRequest();
        request.setName("Lats");

        Muscle saved =  new Muscle();
        saved.setId(1L);
        saved.setName("Lats");
        when(muscleRepository.existsByName("Lats")).thenReturn(false);
        when(muscleRepository.save(any(Muscle.class))).thenReturn(saved);

        MuscleResponse response = muscleService.createMuscle(request);

        assertNotNull(response);
        assertEquals("Lats", response.getName());
        assertEquals(saved.getId(), response.getId());
    }



    @Test
    void createMuscle_shouldThrowException_whenDuplicate() {

        MuscleRequest request = new MuscleRequest();
        request.setName("Lats");

        when(muscleRepository.existsByName("Lats")).thenReturn(true);

        assertThrows(DuplicateException.class, () -> {
            muscleService.createMuscle(request);
        });
    }

    @Test
    void getMuscleById_shouldReturnMuscle() {

        Muscle saved = new Muscle();
        saved.setName("Lats");
        saved.setId(1L);

        when(muscleRepository.findById(1L)).thenReturn(Optional.of(saved));

        MuscleResponse response = muscleService.getMuscleById(1L);
        assertNotNull(response);
        assertEquals("Lats", response.getName());
        assertEquals(1L, response.getId());
    }

    @Test
    void getAllMuscles_shouldReturnAllMuscles() {
        Muscle saved = new Muscle();
        saved.setName("Lats");
        saved.setId(1L);

        Muscle savedTwo = new Muscle();
        savedTwo.setName("Traps");
        savedTwo.setId(2L);

        when(muscleRepository.findAll()).thenReturn(Arrays.asList(saved, savedTwo));
        List<MuscleResponse> response = muscleService.getAllMuscles();
        assertNotNull(response);
        assertEquals("Lats", response.get(0).getName());
        assertEquals("Traps", response.get(1).getName());
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());
    }

    @Test
    void getMuscleById_shouldThrowException() {
        when(muscleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            muscleService.getMuscleById(99L);
        });
    }

    @Test
    void getAllMuscle_shouldReturnEmptyList_whenNoMuscles() {

        when(muscleRepository.findAll()).thenReturn(List.of());

        List<MuscleResponse> response = muscleService.getAllMuscles();

        assertNotNull(response);
        assertEquals(0, response.size());
    }

}
