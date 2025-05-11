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

    // Convert Entity to DTO
    private ExerciseDTO convertToDTO(Exercise exercise) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getId());
        dto.setDescription(exercise.getDescription());
        dto.setDurationMinutes(exercise.getDurationMinutes());
        dto.setDateTime(exercise.getDateTime());
        dto.setNotes(exercise.getNotes());
        return dto;
    }

    // Convert DTO to Entity
    private Exercise convertToEntity(ExerciseDTO dto) {
        Exercise exercise = new Exercise();
        exercise.setId(dto.getId());
        exercise.setDescription(dto.getDescription());
        exercise.setDurationMinutes(dto.getDurationMinutes());
        exercise.setDateTime(dto.getDateTime() != null ? dto.getDateTime() : LocalDateTime.now());
        exercise.setNotes(dto.getNotes());
        return exercise;
    }

    // Get all exercises
    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get exercise by ID
    public Optional<ExerciseDTO> getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Create new exercise
    @Transactional
    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = convertToEntity(exerciseDTO);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return convertToDTO(savedExercise);
    }

    // Update exercise
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

    // Delete exercise
    @Transactional
    public boolean deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            return false;
        }

        exerciseRepository.deleteById(id);
        return true;
    }

    // Find exercises by description
    public List<ExerciseDTO> findByDescription(String description) {
        return exerciseRepository.findByDescriptionContainingIgnoreCase(description).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Find exercises between dates
    public List<ExerciseDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return exerciseRepository.findByDateTimeBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Find exercises with minimum duration
    public List<ExerciseDTO> findByMinDuration(Integer minutes) {
        return exerciseRepository.findByDurationMinutesGreaterThanEqual(minutes).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}