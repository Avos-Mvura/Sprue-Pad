package com.example.sprue_pad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Notes_Fragment extends Fragment {

    private static final String ARG_PROJECT = "project";
    private Project project;

    public Notes_Fragment() {
        // Required empty public constructor
    }

    public static Notes_Fragment newInstance(Project project) {
        Notes_Fragment fragment = new Notes_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable(ARG_PROJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        if (project != null) {
            TextView nameView = view.findViewById(R.id.notes_project_name);
            TextView brandView = view.findViewById(R.id.notes_project_brand);
            TextView scaleView = view.findViewById(R.id.notes_project_scale);
            TextView descView = view.findViewById(R.id.notes_project_description);

            nameView.setText(project.getName());
            brandView.setText(project.getBrand());
            scaleView.setText("Scale: " + project.getScale());
            descView.setText(project.getDescription());
        }

        return view;
    }
}
