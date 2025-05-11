package org.cloudapp.repository;

import org.cloudapp.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // procura exercícios por sua descrição (case-insensitive, partial match)
    List<Exercise> findByDescriptionContainingIgnoreCase(String description);

    // exercícios criados entre duas datas
    List<Exercise> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    // exercícios cuja duração >= desejada
    List<Exercise> findByDurationMinutesGreaterThanEqual(Integer minutes);
}