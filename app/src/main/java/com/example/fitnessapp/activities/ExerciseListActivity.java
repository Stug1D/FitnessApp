package com.example.fitnessapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.Exercise;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.stream.Collectors;

public class ExerciseListActivity extends AppCompatActivity {

    private ListView exerciseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        exerciseListView = findViewById(R.id.exercise_list);

        // Room-Datenbank
        AppDatabase db = AppDatabase.getDatabase(this);
        List<Exercise> exercises = db.fitnessDao().getAllExercises();

        List<String> exerciseNames = exercises.stream()
                .map(e -> e.name + " | " + e.rest + "s | " + e.notes)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                exerciseNames
        );
        exerciseListView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_exercises);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                finish();
                return true;
            } else return id == R.id.nav_exercises;
        });
    }
}