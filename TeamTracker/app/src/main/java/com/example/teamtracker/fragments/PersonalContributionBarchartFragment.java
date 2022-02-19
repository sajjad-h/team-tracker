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
                ArrayList<Long> contributionData = getContributionOfLastWeek(tasks, currentTime);
                int endDay = DateTimeUtil.getDayFromEpoch(DateTimeUtil.getStartEpochOfDay(currentTime));//end day of the graph (current day)
                int startDay = (endDay+1)%7;//start day of the graph (day one week before)
                ArrayList<BarEntry> data = new ArrayList<>();
                for(int day=startDay; day<startDay+7; day++) {
                    data.add(new BarEntry(day-startDay, contributionData.get(day%7)));
                }
                makeBarChartGraph(data, currentTime);
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

    private void makeBarChartGraph(ArrayList<BarEntry> data, long currentTime) {
        BarDataSet barDataSet = new BarDataSet(data, "Daily Contribution by Hours");
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

    private ArrayList<String> getXAxisLabels(long currentTime) {
        int endDay = DateTimeUtil.getDayFromEpoch(DateTimeUtil.getStartEpochOfDay(currentTime)); //end day of the graph (current day)
        int startDay = (endDay+1)%7;//start day of the graph (day one week before)
        String[] days = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thurs",  "Fri"};
        ArrayList<String> label = new ArrayList<>();
        for (int day = startDay; day < startDay+7; day++)
            label.add(days[day%7]);
        return label;
    }

    private ArrayList<Long> getContributionOfLastWeek(List<Task> tasks, long currentTime) {
        ArrayList<Long> contributionList = new ArrayList<Long>(Collections.nCopies(7, 0L));
        Long milliSecondInAWeek = 1L*7*24*3600*1000;
        Long startDay = DateTimeUtil.getStartEpochOfDay(currentTime-milliSecondInAWeek); //start day of the graph(day one week before)
        Long endDay = DateTimeUtil.getStartEpochOfDay(currentTime);//end day of the graph (current day)
        for (Task task : tasks) {
            if(task.getStartTime()>=startDay && task.getStartTime()<=endDay){
                int day = DateTimeUtil.getDayFromEpoch(task.getStartTime());
                contributionList.set(day,contributionList.get(day)+task.getDuration()/1000);
            }
        }
        return (contributionList);
    }
}
