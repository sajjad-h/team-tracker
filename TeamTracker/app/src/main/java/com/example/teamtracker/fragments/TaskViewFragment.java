package com.example.teamtracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.adapters.TaskListAdapter;
import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskViewFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Task> taskList = new ArrayList<>();
    TaskListAdapter taskListAdapter;
    private RoomDB database;
    private Project project;

    public TaskViewFragment(Project project) {
        this.project = project;
    }

    public static TaskViewFragment newInstance(Project project) {
        TaskViewFragment fragment = new TaskViewFragment(project);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = RoomDB.getInstance(getContext());
        taskList = database.taskDao().findTaskByProjectId(project.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTasksRecycler();
        Fragment fragment = new TimerFragment(project);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.timer_fragment_container, fragment).addToBackStack("TimerFragment").commit();
    }

    private void updateTasksRecycler() {
        recyclerView = getView().findViewById(R.id.tasksRecyclerView);
        layoutManager = new LinearLayoutManager(getView().getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        taskListAdapter = new TaskListAdapter(getContext(), taskList);
        recyclerView.setAdapter(taskListAdapter);
        taskListAdapter.notifyDataSetChanged();
    }

}