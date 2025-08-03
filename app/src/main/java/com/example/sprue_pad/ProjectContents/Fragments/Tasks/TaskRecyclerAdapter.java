package com.example.sprue_pad.ProjectContents.Fragments.Tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprue_pad.ProjectContents.Fragments.Tasks.Model.Task;
import com.example.sprue_pad.R;

import java.util.List;


public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {
    private final List<Task> tasks;

    public TaskRecyclerAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.task_checkbox.setText(task.getText());
        holder.task_checkbox.setChecked(task.isChecked());

        holder.task_checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addTask(Task tasks){
        this.tasks.add(this.tasks.size(), new Task(false, "New Task"));
        notifyItemInserted(this.tasks.size() - 1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task_checkbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_checkbox = itemView.findViewById(R.id.task_checkbox);
        }
    }
}
