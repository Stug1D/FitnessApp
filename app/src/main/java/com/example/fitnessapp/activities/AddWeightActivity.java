package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.WeightEntry;

public class AddWeightActivity extends AppCompatActivity {

    EditText editWeight;
    Button btnSave;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddweight);

        editWeight = findViewById(R.id.editWeight);
        btnSave = findViewById(R.id.btnSave);

        db = AppDatabase.getDatabase(this);

        btnSave.setOnClickListener(v -> {

            String text = editWeight.getText().toString();

            if (!text.isEmpty()) {

                float weight = Float.parseFloat(text);

                WeightEntry entry = new WeightEntry(
                        weight,
                        System.currentTimeMillis()
                );

                db.fitnessDao().insertWeight(entry);

                finish(); // zurück zur MainActivity
            }
        });
    }
}