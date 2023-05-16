package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        BarChart barChart = findViewById(R.id.bar_chart);

// Set chart padding to 0dp
        barChart.setPadding(0, 0, 0, 0);

// Customize chart settings
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        barChart.getLegend().setEnabled(false);

// Hide axis lines, grid lines, and labels
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        // Create data set and add data
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 4));
        entries.add(new BarEntry(1, 5));
        entries.add(new BarEntry(2, 6));
        entries.add(new BarEntry(3, 7));
        entries.add(new BarEntry(4, 8));

        BarDataSet dataSet = new BarDataSet(entries, "Label");
        dataSet.setDrawValues(true); // Enables drawing values on top of bars
      //  dataSet.setDrawValueAboveBar(false); // Draws values inside bars instead of above them

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }
}