package com.hands_on_android.tasklist.database;

import android.provider.BaseColumns;

public class TaskListContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TaskList.db";

    //This class is not meant to be initialized
    private TaskListContract() {
    }

    public static class TaskEntry implements BaseColumns {
        //Name of the table
        public static final String TABLE_NAME = "taskEntry";

        //Listing all columns with a format COLUMN_NAME_<name of column>
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_DUE_DATE = "dueDate";
    }
}


