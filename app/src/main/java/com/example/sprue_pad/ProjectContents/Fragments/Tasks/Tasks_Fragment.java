package com.example.sprue_pad.ProjectContents.Fragments.Tasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sprue_pad.ProjectContents.Fragments.Tasks.Model.Task;
import com.example.sprue_pad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tasks_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tasks_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tasks_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tasks_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tasks_Fragment newInstance(String param1, String param2) {
        Tasks_Fragment fragment = new Tasks_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private TaskRecyclerAdapter adapter;
    private List<Task> tasks;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        Button addTasksButton = view.findViewById(R.id.addTasksButton);

        tasks = new ArrayList<>();
        tasks.add(new Task(false, "Task 1"));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TaskRecyclerAdapter(tasks);
        recyclerView.setAdapter(adapter);

        addTasksButton.setOnClickListener(v -> {
            int newTaskPosition = tasks.size() + 1;
            Task tasks = new Task(false, "New Task");
            adapter.addTask(tasks);
        });

        return view;
    }
}