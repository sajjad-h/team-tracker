package com.example.teamtracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Project;

public class ProjectFragment extends Fragment {

    private Project project;

    public ProjectFragment() {
    }


    public ProjectFragment(Project project) {
        this.project = project;
    }

    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fragment timerFragment = new TimerFragment(project);
        Fragment taskViewFragment = new TaskViewFragment(project);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.timer_fragment_container, timerFragment, "TimerFragment");
        fragmentTransaction.replace(R.id.task_view_fragment_container, taskViewFragment, "TaskViewFragment");
        fragmentTransaction.commit();
    }
}