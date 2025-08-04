package com.example.sprue_pad.ProjectContents.Fragments.Tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprue_pad.Project;
import com.example.sprue_pad.ProjectContents.Fragments.Tasks.Model.Task;
import com.example.sprue_pad.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tasks_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private FloatingActionButton addTaskButton;
    private static final String PREF_KEY = "projects";

    private SharedPreferences sharedPreferences;

    private Gson gson = new Gson();

    public Tasks_Fragment(List<Task> ProjectTasks) {
        this.taskList = ProjectTasks;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        addTaskButton = view.findViewById(R.id.addTaskButton);

        taskList = new ArrayList<>();

        adapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        addTaskButton.setOnClickListener(v -> showAddTaskDialog());

        return view;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Task");

        EditText input = new EditText(requireContext());
        input.setHint("Enter task title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = input.getText().toString().trim();
            if (!title.isEmpty()) {
                taskList.add(new Task(title, false));
                adapter.notifyItemInserted(taskList.size());
            }
        });

        sharedPreferences = requireContext().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(PREF_KEY, null);
        ArrayList<Project> projectList;
        if (json != null) {
            Type type = new TypeToken<ArrayList<Project>>() {
            }.getType();
            projectList = gson.fromJson(json, type);

            // Find the matching project by ID
            for (Project p : projectList) {
                if (p.getId().equals(((Project) requireActivity().getIntent().getSerializableExtra("project")).getId())) {
                    ArrayList<String> stringifiedTasks = new ArrayList<>();
                    for (Task t : taskList) {
                        stringifiedTasks.add(gson.toJson(t));
                    }

                    p.getTasks().clear();
                    p.getTasks().addAll(stringifiedTasks);
                    break;
                }
            }

            // Save updated list back to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PREF_KEY, gson.toJson(projectList));
            editor.apply();
        }

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}