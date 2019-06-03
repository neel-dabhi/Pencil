package com.neelkanthjdabhi.pencil;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import 	androidx.core.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.widget.Toast;
import java.util.Calendar;


/**
 * Created by Neelkanth Dabhi on 30-12-2017.
 */

public class temp extends BroadcastReceiver {
    public static String REMOTE_INPUT_KEY = "remote_key";
    public static int NOTIFICATION_ID = 1;
    int color = 0x2196F3;
    String NOTIFICATION_CHANNEL_ID ="NOTIFICATION_CHANNEL_ID", NOTIFICATION_CHANNEL_NAME ="NOTIFICATION_CHANNEL_NAME";



    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Note Added",
                Toast.LENGTH_LONG).show();



        RemoteInput remoteInput = new RemoteInput.Builder(REMOTE_INPUT_KEY)
                .setLabel("Type here to add new note.")
                .build();

        Intent intent1 = new Intent(context, temp.class);
        intent.putExtra("notification", true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action actionNotification = new NotificationCompat.Action.Builder(
                R.drawable.icon,
                "Add note", pendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        Intent dismissNotification = new Intent(context,Dismiss.class);
        PendingIntent pendingIntentDismiss = PendingIntent.getBroadcast(context,0,dismissNotification,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action  dismissAction = new NotificationCompat.Action .Builder(
                R.drawable.icon,
                "Dismiss",pendingIntentDismiss).build();

        Intent copyNotification = new Intent(context,Copy.class);
        PendingIntent pendingIntentCopy = PendingIntent.getBroadcast(context,0,copyNotification,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action  copyAction = new NotificationCompat.Action .Builder(
                R.drawable.icon,
                "Copy",pendingIntentCopy).build();

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db_write = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("msg",getMessageText(intent).toString());
        long row = db_write.insert("string",null,contentValues);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }





        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_action_name)
                .setOngoing(true)
                .setShowWhen(true)
                .addAction(actionNotification)
                .addAction(copyAction)
                .addAction(dismissAction)
                .setColor(color)
                .setContentTitle("Things to remember")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContentText(dbToString(context))
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_MAX);


        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());



    }



    public static String dbToString(Context context){

        DbHelper dbHelper1= new DbHelper(context);
        SQLiteDatabase dbw = dbHelper1.getWritableDatabase();
        String sql = "SELECT * FROM string ";
        StringBuilder stringBuilder = new StringBuilder(100);

        Cursor c = dbw.rawQuery(sql,null);
        while (c.moveToNext())
        {
            stringBuilder.append("\t• ");
            stringBuilder.append(c.getString(0));

            if(c.isLast()) {
                stringBuilder.append("");
            }else {
                stringBuilder.append("\n");
            }
        }

        String str = stringBuilder.toString();
        return str;
    }


    public CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            return remoteInput.getCharSequence(REMOTE_INPUT_KEY);
        }

        return null;
    }


    public static String greetings(){

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 6) {
            return  "Good night!";

        } else if (timeOfDay >= 6 && timeOfDay < 12) {
            return "Good morning!";

        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return "Good afternoon!";

        } else if (timeOfDay >= 16 && timeOfDay < 20) {
            return "Good evening!";

        } else if (timeOfDay >= 20 && timeOfDay < 24) {
            return "Good night!";
        }
        return null;
    }






}
