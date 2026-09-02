package com.example.fitnessapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.fitnessapp.model.Calories;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.model.Workout;
import com.example.fitnessapp.model.WorkoutExerciseCrossRef;
import com.example.fitnessapp.model.WorkoutWithExercises;
import com.example.fitnessapp.model.WeightEntry;

import java.util.List;

@Dao
public interface FitnessDao {

    // Übungen
    @Insert
    long insertExercise(Exercise exercise);

    @Query("SELECT * FROM exercise_table")
    List<Exercise> getAllExercises();

    // Workouts
    @Insert
    long insertWorkout(Workout workout);

    @Insert
    void insertWorkoutExerciseCrossRef(WorkoutExerciseCrossRef crossRef);

    @Transaction
    @Query("SELECT * FROM workout_table")
    List<WorkoutWithExercises> getWorkoutsWithExercises();

    @Insert
    void insertWeight(WeightEntry weightEntry);

    @Query("SELECT * FROM weight_table ORDER BY date ASC")
    List<WeightEntry> getAllWeights();

    @Insert
    void insertCalories(Calories calories);

    @Query("SELECT * FROM Calories_table ORDER BY date ASC")
    List<Calories> getAllCalories();

    @Transaction
    @Query("SELECT * FROM workout_table WHERE id = :workoutId")
    WorkoutWithExercises getWorkoutWithExercises(int workoutId);
}