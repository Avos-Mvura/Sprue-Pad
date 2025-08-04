package com.example.sprue_pad;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sprue_pad.ProjectContents.Fragments.Inventory_Fragment;
import com.example.sprue_pad.ProjectContents.Fragments.Notes_Fragment;
import com.example.sprue_pad.ProjectContents.Fragments.Paint_Fragment;
import com.example.sprue_pad.ProjectContents.Fragments.Tasks.Model.Task;
import com.example.sprue_pad.ProjectContents.Fragments.Tasks.Tasks_Fragment;

import java.util.Collections;
import java.util.List;

public class ProjectPagerAdapter extends FragmentStateAdapter {
    private Project Project;

    public ProjectPagerAdapter(@NonNull FragmentActivity fragmentActivity, Project project) {
        super(fragmentActivity);
        this.Project = project;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

//        Fragment fragment; ???
        switch (position) {
            case 1:
                return new Paint_Fragment();
            case 2:
                return new Inventory_Fragment();
            case 3:
                return new Notes_Fragment();
            case 0:
            default:
                List<Task> tasks = Collections.emptyList();
                for (String task :
                        Project.getTasks()) {
                    tasks.add(new Task(task, false));
                }
                return new Tasks_Fragment(tasks);
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Tasks, Paint, Inventory, Notes
    }
}
