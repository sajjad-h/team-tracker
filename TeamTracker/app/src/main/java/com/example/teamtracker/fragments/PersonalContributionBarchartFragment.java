package com.example.teamtracker.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Project;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalContributionBarchartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalContributionBarchartFragment extends Fragment {


    private Project project;

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
        BarChart personalContributionBarchart = view.findViewById(R.id.personal_contribution_barchart);


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
    }
    public ArrayList<String> getDate() {
        String[] days = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thurs", "Wed", "Fri"};
        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            label.add(days[i]);
        return label;
    }
}
