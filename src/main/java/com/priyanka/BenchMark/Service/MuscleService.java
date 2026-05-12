package com.priyanka.BenchMark.Service;

import com.priyanka.BenchMark.Entity.Muscle;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.MuscleRepository;
import com.priyanka.BenchMark.dto.request.MuscleRequest;
import com.priyanka.BenchMark.dto.response.MuscleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MuscleService {
    private final MuscleRepository muscleRepository;

    // Creates a new muscle and saves it to the database
    // Throws DuplicateException if a muscle with the same name already exists
    public MuscleResponse createMuscle(MuscleRequest muscleRequest) {

        if(muscleRepository.existsByName(muscleRequest.getName())) {
            throw new DuplicateException("Muscle already exists" + muscleRequest.getName());
        }
        Muscle muscle = new Muscle();
        muscle.setName(muscleRequest.getName());

        Muscle savedMuscle = muscleRepository.save(muscle);
        //saved entity to response
        return  new MuscleResponse(savedMuscle.getId(), savedMuscle.getName());
    }

    //Returns a muscle by id
    //Throws ResourceNotFoundException if the id doesn't exist
    public MuscleResponse getMuscleById(Long id) {
       Muscle muscle = muscleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Muscle not found " + id));
       return new MuscleResponse(muscle.getId(), muscle.getName());

    }

    //Returns a list of response DTO for all muscles
    public List<MuscleResponse> getAllMuscles() {
        return muscleRepository.findAll()
                .stream().map(
                        muscle -> new MuscleResponse(muscle.getId(), muscle.getName())
                ).toList();
    }

}
