package com.hands_on_android.tasklist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hands_on_android.tasklist.listener.OnTaskRemovedListener;
import com.hands_on_android.tasklist.model.Task;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView taskNameTextView;
    private TextView taskCategoryTextView;
    private TextView dueDateTextView;

    private Task task;
    private OnTaskRemovedListener listener;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskNameTextView = itemView.findViewById(R.id.task_name);
        taskCategoryTextView = itemView.findViewById(R.id.task_category);
        dueDateTextView = itemView.findViewById(R.id.due_date);
    }

    public void populate(Task task, OnTaskRemovedListener listener) {
        this.task = task;
        this.listener = listener;
        taskNameTextView.setText(task.getName());
        taskCategoryTextView.setText(task.getCategory());
        dueDateTextView.setText(DateUtils.getDateString(task.getDueDate()));
        dueDateTextView.setTextColor(DateUtils.getDateColour(itemView.getContext(), task.getDueDate()));
        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle(R.string.task_delete_title)
                .setMessage(R.string.task_delete_message)
                .setPositiveButton(R.string.delete, (d, which) -> listener.onTaskRemoved(task))
                .setNegativeButton(R.string.cancel, (d, which) -> {})
                .show();
    }
}
