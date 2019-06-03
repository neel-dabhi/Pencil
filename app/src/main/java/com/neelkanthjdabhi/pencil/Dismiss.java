package com.neelkanthjdabhi.pencil;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Neelkanth Dabhi on 30-12-2017.
 */

public class Dismiss extends BroadcastReceiver {
    private static int NOTIFICATION_ID = 1;



    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db_write = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db_write,1,1);

    }
}
