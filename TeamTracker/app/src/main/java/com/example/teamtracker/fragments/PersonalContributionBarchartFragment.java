package com.example.teamtracker.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.util.DateTimeUtil;
import com.example.teamtracker.viewmodels.TaskViewModel;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonalContributionBarchartFragment extends Fragment {

    private Project project;
    private TaskViewModel taskViewModel;
    private BarChart personalContributionBarchart;

    public PersonalContributionBarchartFragment(Project project) {
        this.project = project;
    }

    public static PersonalContributionBarchartFragment newInstance(Project project) {
        PersonalContributionBarchartFragment fragment = new PersonalContributionBarchartFragment(project);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasksByProjectId(project.getId()).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Long currentTime = System.currentTimeMillis();
                ArrayList<Long> graphData = getGraphData(tasks, currentTime);
                String unit = "Second"; //setting unit of time which will be showed in graph
                Long secondInMinute = 60L;
                Long secondInHour = 60L * secondInMinute;
                Long maxDuration = Collections.max(graphData);
                if(maxDuration >= secondInHour) {
                    unit = "Hour";
                    for(int i = 0 ; i < graphData.size() ; i++) graphData.set(i,graphData.get(i)/secondInHour);
                } else if (maxDuration >= secondInMinute) {
                    unit = "Minute";
                    for(int i = 0 ; i < graphData.size() ; i++) graphData.set(i,graphData.get(i)/secondInMinute);
                }
                int endDay = DateTimeUtil.getDayFromEpoch(DateTimeUtil.getStartEpochOfDay(currentTime));//end day showed in the graph (current day)
                int startDay = (endDay+1)%7;//start day showed in the graph (day one week before)
                ArrayList<BarEntry> data = new ArrayList<>();
                for(int day = 0; day < 7; day++) {
                    data.add(new BarEntry(day, graphData.get((day + startDay) %7)));
                }
                makeBarChartGraph(data, currentTime, unit);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_contribution_barchart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        personalContributionBarchart = view.findViewById(R.id.personal_contribution_barchart);
    }

    private void makeBarChartGraph(ArrayList<BarEntry> data, long currentTime, String unit) {
        BarDataSet barDataSet = new BarDataSet(data, "Daily Contribution in " + unit + "s");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        XAxis xAxis = personalContributionBarchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getXAxisLabels(currentTime)));

        personalContributionBarchart.setFitBars(true);
        personalContributionBarchart.setData(barData);
        personalContributionBarchart.animateY(1000);
        personalContributionBarchart.setDescription(null);
    }

    private ArrayList<String> getXAxisLabels(long currentTime) {    //labels the x-axis starting from 1 week before to current day
        int endDay = DateTimeUtil.getDayFromEpoch(DateTimeUtil.getStartEpochOfDay(currentTime)); //end day showed on the graph (current day)
        int startDay = (endDay + 1) % 7;//start day showed on the graph (day one week before)
        String[] days = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thurs",  "Fri"};
        ArrayList<String> label = new ArrayList<>();
        for (int day = 0; day < 7; day++)
            label.add(days[(day + startDay) % 7]);
        return label;
    }

    private ArrayList<Long> getGraphData(List<Task> tasks, long currentTime) {   //returns daily contribution in seconds for the last week
        ArrayList<Long> contributionList = new ArrayList<Long>(Collections.nCopies(7, 0L));
        Long milliSecondInAWeek = 1L*7*24*60*60*1000;
        Long startDay = DateTimeUtil.getStartEpochOfDay(currentTime-milliSecondInAWeek); //start day showed on the graph(day one week before)
        Long endDay = DateTimeUtil.getStartEpochOfDay(currentTime);//end day showed on the graph (current day)
        for (Task task : tasks) {
            if(task.getStartTime()>=startDay && task.getStartTime()<=endDay){
                int day = DateTimeUtil.getDayFromEpoch(task.getStartTime());
                contributionList.set(day,contributionList.get(day)+task.getDuration()/1000);
            }
        }
        return (contributionList);
    }
}
