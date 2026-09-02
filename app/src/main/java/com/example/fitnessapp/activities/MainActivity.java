package com.example.fitnessapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.AddWeightActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.WeightEntry;
import com.example.fitnessapp.activities.CaloriesActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonAddExercise;
    Button buttonAddWorkout;
    Button buttonAddWeight;
    Button buttonCalories;

    Button buttonWorkout;
    LineChart lineChart;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        // Buttons
        buttonAddExercise = findViewById(R.id.buttonAddExercise);
        buttonAddWorkout = findViewById(R.id.buttonAddWorkout);
        buttonAddWeight = findViewById(R.id.btnAdd);
        buttonCalories = findViewById(R.id.buttonCalories);
        buttonWorkout = findViewById(R.id.buttonWorkout);

        // Chart
        lineChart = findViewById(R.id.lineChart);


        buttonAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Workoutactivity.class);
            startActivity(intent);
        });

        // Exercise
        buttonAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExerciseActivity.class);
            startActivity(intent);
        });

        // Workout
        buttonAddWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddWorkoutActivity.class);
            startActivity(intent);
        });

        // Weight
        buttonAddWeight.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddWeightActivity.class);
            startActivity(intent);
        });

        buttonWorkout = findViewById(R.id.buttonWorkout);

        buttonWorkout.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            TemplateSelectionActivity.class
                    );

            startActivity(intent);
        });

        buttonCalories.setOnClickListener(v -> {

            Intent intent =
                    new Intent(MainActivity.this,
                            CaloriesActivity.class);

            startActivity(intent);
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;

            } else if (id == R.id.nav_exercises) {
                Intent intent = new Intent(MainActivity.this,
                        ExerciseListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }

            return false;
        });

        // ERSTES LADEN
        loadChart();
    }

    // 🔥 GRAPH LADEN
    private void loadChart() {

        List<WeightEntry> weights = db.fitnessDao().getAllWeights();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < weights.size(); i++) {
            entries.add(new Entry(i, weights.get(i).weight));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Gewicht");

        dataSet.setColor(Color.BLUE);
        dataSet.setLineWidth(3f);
        dataSet.setDrawValues(false);

        lineChart.setData(new LineData(dataSet));
        lineChart.invalidate();
    }

    // 🔥 WICHTIG: wenn du zurückkommst → Graph neu laden
    @Override
    protected void onResume() {
        super.onResume();
        loadChart();
    }
}