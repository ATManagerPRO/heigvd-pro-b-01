package com.heig.atmanager;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;


public class GoogleCalendarHandler {

    public static final String TAG = "GoogleCalendarHandler";

    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[]{
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private static final int MY_CAL_WRITE_REQ = 50;
    private static final int MY_CAL_READ_REQ = 51;

    private Activity mActivity;
    private long calendarId;

    public GoogleCalendarHandler(Activity activity) {
        mActivity = activity;

        checkWritePermission(activity);

        createCalendar(activity);

       // calendarId = getCalendarID(activity);
    }

    private int getCalendarID(Activity activity) {
        Cursor cur = null;
        ContentResolver cr = activity.getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        String selection =  "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{MainActivity.user.getMail(), BuildConfig.APPLICATION_ID,
                MainActivity.user.getMail()};

        checkWritePermission(activity);
        checkReadPermission(activity);
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        Log.d(TAG, "getCalendarID: " + cur.getInt(cur
        .getColumnIndex(Calendars._ID)));
        return  cur.getInt(PROJECTION_ID_INDEX);
    }

    private void checkWritePermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_CAL_WRITE_REQ);
        }
    }

    private void checkReadPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CALENDAR}, MY_CAL_READ_REQ);
        }
    }

    private void createCalendar(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(Calendars.ACCOUNT_NAME, MainActivity.user.getMail());
        values.put(Calendars.ACCOUNT_TYPE, BuildConfig.APPLICATION_ID);
        values.put(Calendars.NAME, activity.getString(R.string.app_name));
        values.put(Calendars.CALENDAR_DISPLAY_NAME, activity.getString(R.string.app_name));
        values.put(Calendars.CALENDAR_COLOR, R.color.colorAccent);
        values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
        values.put(Calendars.OWNER_ACCOUNT, MainActivity.user.getMail());

        Uri uri = Calendars.CONTENT_URI;

        uri = uri.buildUpon()
                // Sync with server
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(Calendars.ACCOUNT_NAME, MainActivity.user.getMail())
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, BuildConfig.APPLICATION_ID)
                .build();

        uri = activity.getContentResolver().insert(uri, values);
        calendarId =  ContentUris.parseId(uri);
    }

    public void addTask(String title, Date date) {
        ContentResolver cr = mActivity.getContentResolver();
        ContentValues values = new ContentValues();

        values.put(Events.TITLE, title);
        values.put(Events.DTSTART, date.getTime());
        values.put(Events.DTEND, date.getTime());
        values.put(Events.CALENDAR_ID, calendarId);
        values.put(Events.EVENT_TIMEZONE, String.valueOf(TimeZone.getDefault()));

        checkWritePermission(mActivity);

        Uri uri = cr.insert(Events.CONTENT_URI, values);
    }


}
