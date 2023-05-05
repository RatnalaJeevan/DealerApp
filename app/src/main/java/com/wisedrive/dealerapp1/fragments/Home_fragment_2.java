package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.wisedrive.dealerapp1.R;

import java.util.ArrayList;
import java.util.List;


public class Home_fragment_2 extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_2, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        GraphView graph = rootView.findViewById(R.id.graphView);

// create sine wave data points
        DataPoint[] dataPoints = new DataPoint[100];
        for (int i = 0; i < 100; i++) {
            double x = i * 0.1;
            double y = Math.sin(x);
            dataPoints[i] = new DataPoint(x, y);
        }

// create a line graph series
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

// set labels and styling
        series.setTitle("Sine Wave");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.addSeries(series);

        return rootView;
    }
}





