package com.hands_on_android.tasklist.database;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hands_on_android.tasklist.TaskListAppWidgetProvider;
import com.hands_on_android.tasklist.model.Task;
import com.hands_on_android.tasklist.model.TaskList;

import java.util.ArrayList;
import java.util.Date;

public class TaskListDBHelper extends SQLiteOpenHelper {

    //This is equivalent to:
    //CREATE TABLE taskEntry (_ID INTEGER PRIMARY KEY, name TEXT, category TEXT, dueDate INTEGER)
    private static final String SQL_CREATE = "CREATE TABLE " + TaskListContract.TaskEntry.TABLE_NAME + " (" +
            TaskListContract.TaskEntry._ID + " INTEGER PRIMARY KEY," +
            TaskListContract.TaskEntry.COLUMN_NAME_NAME+ " TEXT," +
            TaskListContract.TaskEntry.COLUMN_NAME_CATEGORY + " TEXT," +
            TaskListContract.TaskEntry.COLUMN_NAME_DUE_DATE + " INTEGER)";

    public static TaskListDBHelper INSTANCE;

    private Context context;

    public static void initialize(Context context) {
        INSTANCE = new TaskListDBHelper(context);
        TaskList.initialize();
    }

    public static TaskListDBHelper getInstance() {
        return INSTANCE;
    }

    private TaskListDBHelper(@Nullable Context context) {
        super(context, TaskListContract.DATABASE_NAME, null, TaskListContract.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
        insert(db, new Task("Task 1", "Category", new Date().getTime() + (1 * 24 * 60 * 60 * 1000)));
        insert(db, new Task("Task 2", "Category", new Date().getTime()));
        insert(db, new Task("Task 3", "Category", new Date().getTime() + (10 * 24 * 60 * 60 * 1000)));
        insert(db, new Task("Task 4", "Category", new Date().getTime() + (5 * 24 * 60 * 60 * 1000)));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing
    }

    private void sendAppWidgetBroadcast() {
        Intent intent = new Intent(context, TaskListAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(context, TaskListAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.sendBroadcast(intent);
    }

    private void insert(SQLiteDatabase db, Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskListContract.TaskEntry._ID, task.getId());
        values.put(TaskListContract.TaskEntry.COLUMN_NAME_NAME, task.getName());
        values.put(TaskListContract.TaskEntry.COLUMN_NAME_CATEGORY, task.getCategory());
        values.put(TaskListContract.TaskEntry.COLUMN_NAME_DUE_DATE, task.getDueDate());

        db.insert(TaskListContract.TaskEntry.TABLE_NAME, null, values);

        sendAppWidgetBroadcast();
    }

    public void insert(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        insert(db, task);
    }

    public ArrayList<Task> getTasks() {
        return getTasks(-1);
    }

    public ArrayList<Task> getTasks(int limit) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();

        String sortOrder = TaskListContract.TaskEntry.COLUMN_NAME_DUE_DATE+ " ASC";
        Cursor cursor = db.query(
                TaskListContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext() && (limit < 0 || tasks.size() < limit)) {

            long id = cursor.getLong(cursor.getColumnIndex(TaskListContract.TaskEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(TaskListContract.TaskEntry.COLUMN_NAME_NAME));
            String category = cursor.getString(cursor.getColumnIndex(TaskListContract.TaskEntry.COLUMN_NAME_CATEGORY));
            long dueDate = cursor.getLong(cursor.getColumnIndex(TaskListContract.TaskEntry.COLUMN_NAME_DUE_DATE));

            tasks.add(new Task(id, name, category, dueDate));
        }
        cursor.close();

        return tasks;
    }

    public void delete(Task task) {
        String where = TaskListContract.TaskEntry._ID + "= ?";
        String[] whereArgs = { String.valueOf(task.getId()) };

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TaskListContract.TaskEntry.TABLE_NAME, where, whereArgs);

        sendAppWidgetBroadcast();
    }
}