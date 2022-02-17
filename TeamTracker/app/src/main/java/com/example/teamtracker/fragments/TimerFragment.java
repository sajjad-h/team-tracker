package com.example.teamtracker.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teamtracker.R;
import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.util.DateTimeUtil;

public class TimerFragment extends Fragment {
    private Chronometer chronometer;
    private ToggleButton toggleButton;
    private RoomDB database;
    private Project project;
    private Long startTime;

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
        chronometer = view.findViewById(R.id.chronometer);
        toggleButton = view.findViewById(R.id.toggleButton);
        toggleButton.setChecked(true);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isRunning) {
                if (isRunning) {
                    chronometer.stop();
                    Long duration = SystemClock.elapsedRealtime() - chronometer.getBase();
                    showAddTaskDialog(getContext(), duration);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                } else {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    startTime = System.currentTimeMillis();
                }
            }
        });
    }

    private void showAddTaskDialog(Context context, Long duration) {
        String time = DateTimeUtil.milliSecondToTimeFormat(duration);
        final View addTaskCustomLayout = getLayoutInflater().inflate(R.layout.custom_add_task_alert_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Hey you've worked for " + time + " !!")
                .setMessage("What were you doing?")
                .setView(addTaskCustomLayout)
                .setPositiveButton("Add Task", null)
                .setNegativeButton("Cancel", null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        EditText edtTaskTitle = addTaskCustomLayout.findViewById(R.id.task_Title);
                        EditText edtTaskDescription = addTaskCustomLayout.findViewById(R.id.task_Description);
                        String taskTitle = String.valueOf(edtTaskTitle.getText()).trim();
                        String taskDescription = String.valueOf(edtTaskDescription.getText()).trim();
                        if (TextUtils.isEmpty(taskTitle)) {
                            edtTaskTitle.setError("Title can't be empty.");
                        } else if (TextUtils.isEmpty(taskDescription)) {
                            edtTaskDescription.setError("Description can't be empty.");
                        } else {
                            Task task = new Task(taskTitle, taskDescription, startTime, duration, String.valueOf(project.getId()));
                            database = RoomDB.getInstance(getContext());
                            database.taskDao().insert(task);
                            Toast.makeText(context, "Task Created Successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner_menu_colored_rectangle);
    }
}