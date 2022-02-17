package com.example.teamtracker.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.adapters.ProjectListAdapter;
import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.listeners.ProjectClickListener;
import com.example.teamtracker.models.Project;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Project> projectList;
    ProjectListAdapter projectListAdapter;
    FloatingActionButton fabAddButton;
    RoomDB database;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = RoomDB.getInstance(getContext());
        projectList = database.projectDao().getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateProjectsRecycler();
        fabAddButton = view.findViewById(R.id.fab_add_button);
        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddProjectDialog(getContext());
            }
        });
    }

    private final ProjectClickListener projectClickListener = new ProjectClickListener() {
        @Override
        public void onClick(Project project) {
            Fragment fragment = new ProjectFragment(project);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("TaskView").commit();
        }

        @Override
        public void onLongClick(Project project, TextView textView) {
            Toast.makeText(getContext(), "long selected: " + project.getName(), Toast.LENGTH_SHORT).show();
        }
    };

    private void updateProjectsRecycler() {
        recyclerView = getView().findViewById(R.id.projectsRecyclerView);
        layoutManager = new LinearLayoutManager(getView().getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        projectListAdapter = new ProjectListAdapter(getContext(), projectList, projectClickListener);
        recyclerView.setAdapter(projectListAdapter);
        projectListAdapter.notifyDataSetChanged();
    }

    private void showAddProjectDialog(Context context) {
        final EditText projectNameEditText = new EditText(context);
        projectNameEditText.setMaxLines(1);
        projectNameEditText.setLines(1);
        projectNameEditText.setSingleLine(true);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Create a Project")
                .setMessage("What is your project name?")
                .setView(projectNameEditText)
                .setPositiveButton("Create", null)
                .setNegativeButton("Cancel", null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String projectName = String.valueOf(projectNameEditText.getText()).trim();
                        if (TextUtils.isEmpty(projectName)) {
                            projectNameEditText.setError("Project name can't be empty.");
                        } else {
                            Project project = new Project(projectName);
                            database.projectDao().insert(project);
                            projectList.clear();
                            projectList.addAll(database.projectDao().getAll());
                            projectListAdapter.notifyDataSetChanged();
                            Toast.makeText(context, "Project Created Successfully!", Toast.LENGTH_SHORT).show();
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