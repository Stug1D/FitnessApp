package com.example.fitnessapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.model.Calories;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CaloriesActivity extends AppCompatActivity {

    EditText editCalories, editGoal;
    Button btnSave, btnSaveGoal;

    LineChart lineChart;
    AppDatabase db;

    float goalValue = 2000f; // Standardwert

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        editCalories = findViewById(R.id.editCalories);
        editGoal = findViewById(R.id.editGoal);

        btnSave = findViewById(R.id.btnSave);
        btnSaveGoal = findViewById(R.id.btnSaveGoal);

        lineChart = findViewById(R.id.lineChart);

        db = AppDatabase.getDatabase(this);

        loadChart();

        btnSave.setOnClickListener(v -> {
            String text = editCalories.getText().toString();

            if (!text.isEmpty()) {
                float calories = Float.parseFloat(text);

                db.fitnessDao().insertCalories(
                        new Calories(calories, System.currentTimeMillis())
                );

                editCalories.setText("");
                loadChart();
            }
        });

        btnSaveGoal.setOnClickListener(v -> {
            String text = editGoal.getText().toString();

            if (!text.isEmpty()) {
                goalValue = Float.parseFloat(text);
                loadChart();
            }
        });

        BottomNavigationView bottomNav =
                findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            }

            if (item.getItemId() == R.id.nav_exercises) {
                startActivity(new Intent(this, ExerciseListActivity.class));
                return true;
            }

            return false;
        });
    }

    private void loadChart() {

        List<Calories> list =
                db.fitnessDao().getAllCalories();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, list.get(i).calories));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Kalorien");
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(3f);

        LineData data = new LineData(dataSet);
        lineChart.setData(data);

        // X ACHSE = DATUM
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                int index = (int) value;

                if (index >= 0 && index < list.size()) {
                    long date = list.get(index).date;

                    return new SimpleDateFormat(
                            "dd.MM",
                            Locale.getDefault()
                    ).format(new Date(date));
                }

                return "";
            }
        });

        // Y ACHSE + GOAL LINE
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();

        LimitLine goalLine = new LimitLine(goalValue, "Ziel");
        goalLine.setLineColor(Color.GREEN);
        goalLine.setLineWidth(2f);
        goalLine.setTextColor(Color.GREEN);

        leftAxis.addLimitLine(goalLine);

        lineChart.getAxisRight().setEnabled(false);

        lineChart.invalidate();
    }
}