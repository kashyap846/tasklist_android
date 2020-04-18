package com.hands_on_android.tasklist;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton addButton = findViewById(R.id.add_new);
        addButton.setOnClickListener(this::onAddButtonClick);
    }

    private void onAddButtonClick(View view) {
        new AddNewDialogFragment.Builder()
                .addOnNewTaskListener(adapter::notifyDataSetChanged)
                .show(getSupportFragmentManager(), "add_new_dialog");
    }
}