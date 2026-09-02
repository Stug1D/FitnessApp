package com.example.fitnessapp.model;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        primaryKeys = {"workoutId", "exerciseId"},
        indices = {@Index("exerciseId"), @Index("workoutId")}
)
public class WorkoutExerciseCrossRef {

    public int workoutId;
    public int exerciseId;

    public WorkoutExerciseCrossRef(int workoutId, int exerciseId) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
    }
}