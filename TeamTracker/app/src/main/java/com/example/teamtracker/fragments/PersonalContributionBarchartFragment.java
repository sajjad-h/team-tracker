package com.example.teamtracker.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.viewmodels.TaskViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalContributionBarchartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalContributionBarchartFragment extends Fragment {


    private Project project;
    private TaskViewModel taskViewModel;
    private BarChart personalContributionBarchart;

    public PersonalContributionBarchartFragment() {
        // Required empty public constructor
    }

    public PersonalContributionBarchartFragment(Project project) {
        this.project = project;
        // Required empty public constructor
    }

    public static PersonalContributionBarchartFragment newInstance(String param1, String param2) {
        PersonalContributionBarchartFragment fragment = new PersonalContributionBarchartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasksByProjectId(project.getId()).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Toast.makeText(getContext(), "Hello Data changed!", Toast.LENGTH_SHORT).show();
                personalContributionBarchart.clear();
                Long duration = 0L;
                for (Task task : tasks) {
                    duration +=  task.getDuration();
                }
                String[] days = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thurs", "Wed", "Fri"};

                ArrayList<BarEntry> dummyData = new ArrayList<>();
                for(int i=0; i < 7; i++) {
                    dummyData.add(new BarEntry(i, duration/1000));
                }
                BarDataSet barDataSet = new BarDataSet(dummyData, "Contribution");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);
                barDataSet.setStackLabels(days);

                BarData barData = new BarData(barDataSet);
                personalContributionBarchart.setData(barData);
                personalContributionBarchart.notifyDataSetChanged();
//                taskListAdapter.submitList(tasks);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        recyclerView.smoothScrollToPosition(0);
                    }
                }, 500);
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


        String[] days = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thurs", "Wed", "Fri"};

        ArrayList<BarEntry> dummyData = new ArrayList<>();
        for(int i=0; i < 7; i++) {
            dummyData.add(new BarEntry(i,200*i));
        }
        BarDataSet barDataSet = new BarDataSet(dummyData, "Contribution");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barDataSet.setStackLabels(days);

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = personalContributionBarchart.getXAxis();

        xAxis.setValueFormatter(new IndexAxisValueFormatter(getDate()));



        personalContributionBarchart.setFitBars(true);
        personalContributionBarchart.setData(barData);
        personalContributionBarchart.animateY(1000);
        personalContributionBarchart.notifyDataSetChanged();
    }
    public ArrayList<String> getDate() {
        String[] days = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thurs", "Wed", "Fri"};
        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            label.add(days[i]);
        return label;
    }
}
