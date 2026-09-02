package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.Exercise;
import com.example.fitnessapp.model.Workout;
import com.example.fitnessapp.model.WorkoutExerciseCrossRef;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutActivity extends AppCompatActivity {

    private ListView listView;
    private SearchView searchView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> exerciseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addworkout);

        listView = findViewById(R.id.workout_list);
        searchView = findViewById(R.id.search_view);
        Button saveWorkout = findViewById(R.id.save_workout);
        EditText workoutNameInput = findViewById(R.id.workout_name);

        // Datenbank

        AppDatabase db = AppDatabase.getDatabase(this);
        List<Exercise> exercises = db.fitnessDao().getAllExercises();

        // Namen extrahieren

        exerciseNames = new ArrayList<>();
        for (Exercise e : exercises) {
            exerciseNames.add(e.name);
        }

        // Adapter mit Checkboxen

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                exerciseNames
        );

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Suche
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        // Workout speichern

        saveWorkout.setOnClickListener(v -> {

            String workoutName = workoutNameInput.getText().toString();

            if (workoutName.isEmpty()) {
                Toast.makeText(this, "Bitte Namen eingeben", Toast.LENGTH_SHORT).show();
                return;
            }

            // Workout speichern

            Workout workout = new Workout(workoutName);
            long workoutId = db.fitnessDao().insertWorkout(workout);

            // Ausgewählte Übungen

            SparseBooleanArray checked = listView.getCheckedItemPositions();

            for (int i = 0; i < exerciseNames.size(); i++) {
                if (checked.get(i)) {

                    Exercise e = exercises.get(i);

                    WorkoutExerciseCrossRef crossRef =
                            new WorkoutExerciseCrossRef((int) workoutId, e.id);

                    db.fitnessDao().insertWorkoutExerciseCrossRef(crossRef);
                }
            }

            Toast.makeText(this, "Workout gespeichert!", Toast.LENGTH_SHORT).show();
        });

        //abbrechen

        Button abbrechen = findViewById(R.id.abbrechen);
        abbrechen.setOnClickListener(item -> {
            Intent intent = new Intent(AddWorkoutActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Bottom Navigation

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.buttonAddWorkout);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(AddWorkoutActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_exercises) {
                Intent intent = new Intent(AddWorkoutActivity.this, ExerciseListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }

            return false;
        });
    }
}