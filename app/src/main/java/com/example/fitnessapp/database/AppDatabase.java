package com.example.fitnessapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fitnessapp.model.Calories;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.model.WeightEntry;
import com.example.fitnessapp.model.Workout;
import com.example.fitnessapp.model.WorkoutExerciseCrossRef;

@Database(
        entities = {
                Exercise.class,
                Workout.class,
                WorkoutExerciseCrossRef.class,
                WeightEntry.class,
                Calories.class
        },
        version = 3,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FitnessDao fitnessDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "fitness-db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }
}