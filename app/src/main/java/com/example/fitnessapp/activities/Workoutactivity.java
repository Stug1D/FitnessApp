package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.model.WorkoutWithExercises;

public class Workoutactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        LinearLayout container =
                findViewById(R.id.exerciseContainer);

        int workoutId =
                getIntent().getIntExtra(
                        "WORKOUT_ID",
                        -1
                );

        AppDatabase db =
                AppDatabase.getDatabase(this);

        WorkoutWithExercises workout =
                db.fitnessDao()
                        .getWorkoutWithExercises(workoutId);

        for (Exercise exercise : workout.exercises) {

            TextView title = new TextView(this);
            title.setText(exercise.name);
            title.setTextSize(20);

            EditText weight = new EditText(this);
            weight.setHint("Gewicht");

            EditText reps = new EditText(this);
            reps.setHint("Wiederholungen");

            container.addView(title);
            container.addView(weight);
            container.addView(reps);
        }
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_exercises);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }
            else if (id == R.id.nav_exercises) {
                Intent intent = new Intent(this, TemplateSelectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

}