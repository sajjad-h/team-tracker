package com.example.teamtracker.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.teamtracker.R;
import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;

public class TimerFragment extends Fragment {
    private static final int SECONDS_IN_MINUTE = 60,MINUTES_IN_HOUR=60,MILLI=1000;
    private Chronometer chronometer;
    private ToggleButton toggleButton;
    private RoomDB database;
    private Project project;

    public TimerFragment(Project project) {
        this.project = project;
    }

    public static TimerFragment newInstance(Project project) {
        TimerFragment fragment = new TimerFragment(project);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chronometer= view.findViewById(R.id.chronometer);
        toggleButton=view.findViewById(R.id.toggleButton);
        toggleButton.setChecked(true);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isRunning) {
                if(isRunning){
                    chronometer.stop();
                    Long duration = SystemClock.elapsedRealtime() - chronometer.getBase();
                    showAddTaskDialog(getContext(),duration);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }else {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }
            }
        });
    }

    private void showAddTaskDialog(Context context, Long duration) {
        final View addTaskCustomLayout = getLayoutInflater().inflate(R.layout.custom_add_task_alert_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Hey you've worked for "+formatDuration(duration)+" !!")
                .setMessage("What were you doing?")
                .setView(addTaskCustomLayout)
                .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText taskTitle, taskDescription;
                        taskTitle = addTaskCustomLayout.findViewById(R.id.task_Title);
                        taskDescription = addTaskCustomLayout.findViewById(R.id.task_Description);
                        String tempTaskTitle = String.valueOf(taskTitle.getText());
                        String tempTaskDescription = String.valueOf(taskDescription.getText());
                        Task task = new Task(tempTaskTitle, tempTaskDescription, String.valueOf(project.getId()));
                        database = RoomDB.getInstance(getContext());
                        database.taskDao().insert(task);
                        Toast.makeText(context, "Task Created Successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner_menu_colored_rectangle);
    }
    private String formatDuration(Long duration){
        String time = "";
        duration/=MILLI; // converting millisecond to second
        Long hour = duration/(SECONDS_IN_MINUTE*MINUTES_IN_HOUR); // calculating hour
        duration%=(SECONDS_IN_MINUTE*MINUTES_IN_HOUR); // remaining seconds
        Long minute = duration/SECONDS_IN_MINUTE; // calculating minute
        Long sec =  duration%SECONDS_IN_MINUTE; //calculating second
        // making the time string
        if(hour != 0) time+= hour + "h ";
        if(minute != 0) time+= minute + "m ";
        if(sec != 0) time+= sec + "s";
        else if(hour == 0 && sec == 0) time+= sec + "s";
        return time;
    }
}