package com.example.platforma_ctf;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserManager userManager;
    private ArrayList<User> leaderboardUsers;

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        userManager = new UserManager(this);
        leaderboardUsers = userManager.getLeaderboardUsers();
        setUpRecyclerView();
        setUpLineChart();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_leaderboard);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        leaderboardAdapter = new LeaderboardAdapter(leaderboardUsers);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(leaderboardAdapter);
    }

    private void setUpLineChart() {
        chart = findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < leaderboardUsers.size(); i++) {
            entries.add(new Entry(i, leaderboardUsers.get(i).getScore()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Scores");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setDrawValues(false);
        dataSet.setValueTextColor(Color.WHITE); // Change value label color to white

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE); // Change X axis label color to white
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < leaderboardUsers.size()) {
                    return leaderboardUsers.get(index).getUsername();
                } else {
                    return "";
                }
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1f);
        leftAxis.setTextColor(Color.WHITE); // Change left Y axis label color to white

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        // Add legend to the chart
        Legend legend = chart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12f);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.WHITE); // Change legend text color to white

        chart.invalidate(); // Refresh the chart
    }

}
