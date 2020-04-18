package com.hands_on_android.tasklist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.hands_on_android.tasklist.listener.OnNewTaskListener;
import com.hands_on_android.tasklist.model.TaskList;

import java.util.Calendar;
import java.util.Date;

public class AddNewDialogFragment extends DialogFragment {

    public static class Builder {
        private AddNewDialogFragment fragment = new AddNewDialogFragment();

        public Builder addOnNewTaskListener(OnNewTaskListener listener) {
            fragment.listener = listener;
            return this;
        }

        public void show(FragmentManager fragmentManager, String tag) {
            fragment.show(fragmentManager, tag);
        }
    }

    private AddNewDialogFragment() {
        //Make sure the dialog can only be created through builder
    }

    private OnNewTaskListener listener;

    private TextInputEditText nameEditText;
    private TextInputEditText categoryEditText;
    private TextView pickDateTextView;

    private long dueDate = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.fragment_add_new, container, false);

        nameEditText = layout.findViewById(R.id.name);
        categoryEditText = layout.findViewById(R.id.category);

        Button addButton = layout.findViewById(R.id.add);
        addButton.setOnClickListener(this::addButtonClick);

        pickDateTextView = layout.findViewById(R.id.date);
        pickDateTextView.setOnClickListener(this::onPickDateClick);

        return layout;
    }

    private void onPickDateClick(View view) {
        Calendar c = Calendar.getInstance();
        c.setTime(dueDate > 0 ? new Date(dueDate) : new Date());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(view.getContext(), this::onDateSet, year, month, day).show();
    }

    private void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(); //Calling clear make sure all time value (hour minute seconds) are deleted
        calendar.set(year, month, dayOfMonth);
        dueDate = calendar.getTimeInMillis();
        pickDateTextView.setText(DateUtils.getDateString(dueDate));
    }

    private void addButtonClick(View view) {
        if (nameEditText.getText() == null || categoryEditText.getText() == null) {
            return;
        }
        String name = nameEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        if (name.isEmpty() || category.isEmpty()) {
            return;
        }

        Date date = dueDate > 0 ? new Date(dueDate) : new Date();
        TaskList.getInstance().addTask(name, category, date.getTime());
        if (listener != null) {
            listener.onNewTask();
        }
        dismiss();
    }
}