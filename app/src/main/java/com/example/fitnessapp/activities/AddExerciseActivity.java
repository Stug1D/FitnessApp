package com.example.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.Exercise;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {

    private EditText name, rest, notes;
    private Button button;
    private ListView listView;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexercise);

        name = findViewById(R.id.exercisename);
        rest = findViewById(R.id.exerciserest);
        notes = findViewById(R.id.Notizen);
        button = findViewById(R.id.submitexercise);
        listView = findViewById(R.id.exercise_list);

        db = AppDatabase.getDatabase(this);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        // Liste laden
        List<Exercise> exercises = db.fitnessDao().getAllExercises();
        for (Exercise e : exercises) {
            list.add(e.name + " | " + e.rest + " | " + e.notes);
        }

        // Neue Übung hinzufügen
        button.setOnClickListener(v -> {
            String n = name.getText().toString();
            String r = rest.getText().toString();
            String no = notes.getText().toString();

            if (!n.isEmpty()) {
                Exercise e = new Exercise(n, r, no);
                db.fitnessDao().insertExercise(e);

                list.add(n + " | " + r + " | " + no);
                adapter.notifyDataSetChanged();

                name.setText("");
                rest.setText("");
                notes.setText("");
            }
        });

        //Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(AddExerciseActivity.this, com.example.fitnessapp.activities.MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_exercises) {
                Intent intent = new Intent(AddExerciseActivity.this, com.example.fitnessapp.activities.ExerciseListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }

            return false;
        });
    }

}