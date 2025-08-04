package com.example.sprue_pad.ProjectContents.Fragments.Tasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprue_pad.ProjectContents.Fragments.Tasks.Model.Task;
import com.example.sprue_pad.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class Tasks_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        fabAddTask = view.findViewById(R.id.fabAddTask);

        // Setup RecyclerView
        taskList = new ArrayList<>();
        taskList.add(new Task("Buy groceries", false));
        taskList.add(new Task("Walk the dog", true));
        taskList.add(new Task("Read a book", false));

        adapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Set up FAB click listener
        fabAddTask.setOnClickListener(v -> showAddTaskDialog());

        return view;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Task");

        // Input field
        EditText input = new EditText(requireContext());
        input.setHint("Enter task title");
        builder.setView(input);

        // Add buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = input.getText().toString().trim();
            if (!title.isEmpty()) {
                Task newTask = new Task(title, false);
                taskList.add(newTask);
                adapter.notifyItemInserted(taskList.size() - 1); // Notify adapter
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}