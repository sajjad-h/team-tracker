package com.example.teamtracker.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.teamtracker.R;
import com.example.teamtracker.adapters.TaskListAdapter;
import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.sync.TaskSync;
import com.example.teamtracker.viewmodels.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskViewFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager layoutManager;
    TaskListAdapter taskListAdapter;
    private Project project;
    private TaskViewModel taskViewModel;
    private TaskSync taskSync;

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

        taskSync = new TaskSync(getContext());
        taskSync.sync(project.getId().toString());

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasksByProjectId(project.getId().toString()).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskListAdapter.submitList(tasks);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }, 500);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTasksRecycler();

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "syncing ...", Toast.LENGTH_SHORT).show();
                taskSync.sync(project.getId().toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateTasksRecycler() {
        recyclerView = getView().findViewById(R.id.tasksRecyclerView);
        layoutManager = new LinearLayoutManager(getView().getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        taskListAdapter = new TaskListAdapter();
        recyclerView.setAdapter(taskListAdapter);
    }

}