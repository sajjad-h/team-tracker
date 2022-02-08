package com.example.teamtracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.adapters.TaskListAdapter;
import com.example.teamtracker.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskViewFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Task> taskList = new ArrayList<>();
    TaskListAdapter taskListAdapter;

    public TaskViewFragment() {
    }

    public static TaskViewFragment newInstance() {
        TaskViewFragment fragment = new TaskViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task task1 = new Task("Task1", "Implementing Task1");
        Task task2 = new Task("Task2", "Implementing Task2");
        Task task3 = new Task("Task3", "Implementing Task3");
        Task task4 = new Task("Task4", "Implementing Task4");
        Task task5 = new Task("Task5", "Implementing Task5");
        Task task6 = new Task("Task6", "Implementing Task6");

        taskList.add(0, task1);
        taskList.add(1, task2);
        taskList.add(2, task3);
        taskList.add(3, task4);
        taskList.add(4, task5);
        taskList.add(5, task6);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTasksRecycler();
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