package com.example.teamtracker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Project;

public class GraphFragment extends Fragment {


    private Project project;

    public GraphFragment(Project project) {
        this.project = project;
    }

    public static GraphFragment newInstance(Project project) {
        GraphFragment fragment = new GraphFragment(project);
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
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment personalContributionBarchartFragment = new PersonalContributionBarchartFragment(project);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.personal_contribution_barchart_fragment_container, personalContributionBarchartFragment, "GraphFragment");
        fragmentTransaction.commit();
    }
}