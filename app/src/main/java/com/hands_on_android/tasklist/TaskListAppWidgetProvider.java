package com.hands_on_android.tasklist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.hands_on_android.tasklist.database.TaskListDBHelper;
import com.hands_on_android.tasklist.model.Task;

import java.util.ArrayList;

public class TaskListAppWidgetProvider extends AppWidgetProvider {

    private static final int[] CONTAINER_IDS = { R.id.task1_container, R.id.task2_container, R.id.task3_container };
    private static final int[] TITLE_VIEW_IDS = { R.id.task1, R.id.task2, R.id.task3 };
    private static final int[] DUE_DATE_VIEW_IDS = { R.id.task1_due_date, R.id.task2_due_date, R.id.task3_due_date };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ArrayList<Task> tasks = TaskListDBHelper.getInstance().getTasks(3);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_task_list);

            for (int j = 0; j < 3; j++) {
                if (j < tasks.size()) {
                    //Task has value
                    remoteViews.setViewVisibility(CONTAINER_IDS[j], View.VISIBLE);
                    populateTask(context, remoteViews, tasks.get(j), TITLE_VIEW_IDS[j], DUE_DATE_VIEW_IDS[j]);
                } else {
                    //No task, make it invisible
                    remoteViews.setViewVisibility(CONTAINER_IDS[j], View.INVISIBLE);
                }
            }

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    private void populateTask(Context context, RemoteViews remoteViews, Task task, int titleView, int dueDateView) {
        remoteViews.setTextViewText(titleView, task.getName());

        remoteViews.setTextViewText(dueDateView, DateUtils.getDateString(task.getDueDate()));
        remoteViews.setTextColor(dueDateView, DateUtils.getDateColour(context, task.getDueDate()));
    }
}