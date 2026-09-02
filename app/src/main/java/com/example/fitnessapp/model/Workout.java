package com.example.fitnessapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_table")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public Workout(String name) {
        this.name = name;
    }
}