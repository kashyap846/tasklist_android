package com.hands_on_android.tasklist;

import android.content.Context;

import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {

    private static final long THREE_DAYS = 3 * 24 * 60 * 60 * 1000;

    public static String getDateString(long dateInMilli) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return format.format(new Date(dateInMilli));
    }

    public static int getDateColour(Context context, long dateInMilli) {
        long currentTime = new Date().getTime();
        if (dateInMilli - currentTime > THREE_DAYS) {
            return ContextCompat.getColor(context, R.color.due_date_good);
        } else {
            return ContextCompat.getColor(context, R.color.due_date_bad);
        }
    }
}
