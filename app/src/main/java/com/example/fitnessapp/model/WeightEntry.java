package com.example.fitnessapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weight_table")
public class WeightEntry {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public float weight;

    public long date;

    public WeightEntry(float weight, long date) {
        this.weight = weight;
        this.date = date;
    }
}