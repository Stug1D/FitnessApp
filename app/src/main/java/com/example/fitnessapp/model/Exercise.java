package com.example.fitnessapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String rest;
    public String notes;

    public Exercise(String name, String rest, String notes) {
        this.name = name;
        this.rest = rest;
        this.notes = notes;
    }
}