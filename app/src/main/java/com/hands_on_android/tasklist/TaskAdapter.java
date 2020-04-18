package com.hands_on_android.tasklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hands_on_android.tasklist.model.Task;
import com.hands_on_android.tasklist.model.TaskList;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    public TaskAdapter() {
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return TaskList.getInstance().getTask(position).getId();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = TaskList.getInstance().getTask(position);
        holder.populate(task, this::onTaskRemoved);
    }

    public void onTaskRemoved(Task task) {
        TaskList.getInstance().removeTask(task);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return TaskList.getInstance().getTaskCount();
    }
}

