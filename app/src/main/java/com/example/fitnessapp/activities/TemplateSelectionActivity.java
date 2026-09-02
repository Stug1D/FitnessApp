package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.WorkoutWithExercises;

import java.util.ArrayList;
import java.util.List;

public class TemplateSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_selection);

        ListView listView = findViewById(R.id.listTemplates);

        AppDatabase db = AppDatabase.getDatabase(this);

        List<WorkoutWithExercises> workouts =
                db.fitnessDao().getWorkoutsWithExercises();

        ArrayList<String> names = new ArrayList<>();

        for (WorkoutWithExercises workout : workouts) {
            names.add(workout.workout.name);
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        names
                );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            int workoutId =
                    workouts.get(position).workout.id;

            Intent intent =
                    new Intent(
                            TemplateSelectionActivity.this,
                            Workoutactivity.class
                    );

            intent.putExtra("WORKOUT_ID", workoutId);

            startActivity(intent);
        });
    }
}