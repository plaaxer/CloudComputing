package org.cloudapp.repository;

import org.cloudapp.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // Find exercises by description (case-insensitive, partial match)
    List<Exercise> findByDescriptionContainingIgnoreCase(String description);

    // Find exercises created between two dates
    List<Exercise> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find exercises with duration greater than or equal to specified minutes
    List<Exercise> findByDurationMinutesGreaterThanEqual(Integer minutes);
}