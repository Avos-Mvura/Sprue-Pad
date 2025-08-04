package com.example.sprue_pad.ProjectContents.Fragments.Tasks;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprue_pad.ProjectContents.Fragments.Tasks.Model.Task;
import com.example.sprue_pad.R;

import java.util.List;

public class    TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.checkBox.setChecked(task.isCompleted());
        holder.textViewTitle.setText(task.getTitle());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
        });

        if (task.isCompleted()) {
            Spannable spannable = new SpannableString(task.getTitle());
            spannable.setSpan(new StrikethroughSpan(), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.textViewTitle.setText(spannable);
        } else {
            holder.textViewTitle.setText(task.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textViewTitle;

        TaskViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
