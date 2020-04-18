package com.hands_on_android.tasklist.model;

import com.hands_on_android.tasklist.database.TaskListDBHelper;

import java.util.ArrayList;
import java.util.Collections;

public class TaskList {
    private static TaskList INSTANCE;
    private ArrayList<Task> tasks;

    private static int compare(Task o1, Task o2) {
        if (o1.getDueDate() > o2.getDueDate()) {
            return 1;
        } else if (o1.getDueDate() < o2.getDueDate()) {
            return -1;
        } else {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public static void initialize() {
        INSTANCE = new TaskList();
    }

    private TaskList() {
        tasks = TaskListDBHelper.getInstance().getTasks();
    }

    public static TaskList getInstance() {
        return INSTANCE;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public Task getTask(int position) {
        return tasks.get(position);
    }

    public void addTask(String name, String category, long dueDate) {
        Task newTask = new Task(name, category, dueDate);
        tasks.add(newTask);
        TaskListDBHelper.getInstance().insert(newTask);
        Collections.sort(tasks, TaskList::compare);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        TaskListDBHelper.getInstance().delete(task);
    }
}
