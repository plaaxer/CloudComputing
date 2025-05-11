package org.cloudapp.service;

import org.cloudapp.dto.ExerciseDTO;
import org.cloudapp.model.Exercise;
import org.cloudapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    private ExerciseDTO convertToDTO(Exercise exercise) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getId());
        dto.setDescription(exercise.getDescription());
        dto.setDurationMinutes(exercise.getDurationMinutes());
        dto.setDateTime(exercise.getDateTime());
        dto.setNotes(exercise.getNotes());
        return dto;
    }

    private Exercise convertToEntity(ExerciseDTO dto) {
        Exercise exercise = new Exercise();
        exercise.setId(dto.getId());
        exercise.setDescription(dto.getDescription());
        exercise.setDurationMinutes(dto.getDurationMinutes());
        exercise.setDateTime(dto.getDateTime() != null ? dto.getDateTime() : LocalDateTime.now());
        exercise.setNotes(dto.getNotes());
        return exercise;
    }

    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public Optional<ExerciseDTO> getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        try {
            exerciseDTO.setId(null);

            Exercise exercise = convertToEntity(exerciseDTO);
            Exercise savedExercise = exerciseRepository.save(exercise);
            return convertToDTO(savedExercise);
        } catch (org.springframework.orm.ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Unable to create exercise due to data conflict. Please do not provide an ID for new exercises.", e);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new RuntimeException("Unable to create exercise due to data integrity issue. Check that all required fields are provided correctly.", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating the exercise.", e);
        }
    }

    @Transactional
    public Optional<ExerciseDTO> updateExercise(Long id, ExerciseDTO exerciseDTO) {
        if (!exerciseRepository.existsById(id)) {
            return Optional.empty();
        }

        Exercise exercise = convertToEntity(exerciseDTO);
        exercise.setId(id);
        Exercise updatedExercise = exerciseRepository.save(exercise);
        return Optional.of(convertToDTO(updatedExercise));
    }

    @Transactional
    public boolean deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            return false;
        }

        exerciseRepository.deleteById(id);
        return true;
    }

    public List<ExerciseDTO> findByDescription(String description) {
        return exerciseRepository.findByDescriptionContainingIgnoreCase(description).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExerciseDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return exerciseRepository.findByDateTimeBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExerciseDTO> findByMinDuration(Integer minutes) {
        return exerciseRepository.findByDurationMinutesGreaterThanEqual(minutes).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}