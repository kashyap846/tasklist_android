package com.hands_on_android.tasklist.model;

import java.util.Random;

public class Task {
    private long id;
    public String name;
    private String category;
    private long dueDate;

    public Task(String name, String category, long dueDate) {
        this.id = new Random().nextLong();
        this.name = name;
        this.category = category;
        this.dueDate = dueDate;
    }

    public Task(long id, String name, String category, long dueDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public long getDueDate() {
        return dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id == task.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
