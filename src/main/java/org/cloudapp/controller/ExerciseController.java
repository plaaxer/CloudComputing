package org.cloudapp.controller;

import org.cloudapp.dto.ExerciseDTO;
import org.cloudapp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@Tag(name = "Exercise", description = "Exercise tracking API")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    @Operation(summary = "Get all exercises", description = "Retrieves a list of all recorded exercises")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise list")
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        List<ExerciseDTO> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exercise by ID", description = "Retrieves a specific exercise by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise found"),
            @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content)
    })
    public ResponseEntity<ExerciseDTO> getExerciseById(
            @Parameter(description = "ID of the exercise to retrieve") @PathVariable Long id) {
        return exerciseService.getExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new exercise", description = "Records a new exercise activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exercise created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid exercise data provided")
    })
    public ResponseEntity<ExerciseDTO> createExercise(
            @Parameter(description = "Exercise details") @Valid @RequestBody ExerciseDTO exerciseDTO) {
        ExerciseDTO createdExercise = exerciseService.createExercise(exerciseDTO);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an exercise", description = "Updates an existing exercise record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully"),
            @ApiResponse(responseCode = "404", description = "Exercise not found"),
            @ApiResponse(responseCode = "400", description = "Invalid exercise data provided")
    })
    public ResponseEntity<ExerciseDTO> updateExercise(
            @Parameter(description = "ID of the exercise to update") @PathVariable Long id,
            @Parameter(description = "Updated exercise details") @Valid @RequestBody ExerciseDTO exerciseDTO) {
        return exerciseService.updateExercise(id, exerciseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an exercise", description = "Removes an exercise record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exercise deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    public ResponseEntity<Void> deleteExercise(
            @Parameter(description = "ID of the exercise to delete") @PathVariable Long id) {
        boolean deleted = exerciseService.deleteExercise(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search/description")
    @Operation(summary = "Search exercises by description", description = "Finds exercises containing the specified text in description")
    public ResponseEntity<List<ExerciseDTO>> searchByDescription(
            @Parameter(description = "Text to search for in exercise descriptions")
            @RequestParam String query) {
        List<ExerciseDTO> exercises = exerciseService.findByDescription(query);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/search/daterange")
    @Operation(summary = "Search exercises by date range", description = "Finds exercises recorded between two dates")
    public ResponseEntity<List<ExerciseDTO>> searchByDateRange(
            @Parameter(description = "Start date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ExerciseDTO> exercises = exerciseService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/search/duration")
    @Operation(summary = "Search exercises by minimum duration", description = "Finds exercises with duration greater than or equal to specified minutes")
    public ResponseEntity<List<ExerciseDTO>> searchByMinDuration(
            @Parameter(description = "Minimum duration in minutes")
            @RequestParam Integer minutes) {
        List<ExerciseDTO> exercises = exerciseService.findByMinDuration(minutes);
        return ResponseEntity.ok(exercises);
    }
}