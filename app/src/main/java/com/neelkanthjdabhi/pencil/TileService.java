package com.neelkanthjdabhi.pencil;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import 	androidx.core.app.RemoteInput;

import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import android.graphics.Color;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.widget.Toast;

/**
 * Created by Neelkanth Dabhi on 28-12-2017.
 */

    public class TileService extends android.service.quicksettings.TileService {

    public static String REMOTE_INPUT_KEY = "remote_key";
    public static int NOTIFICATION_ID = 1;
    String NOTIFICATION_CHANNEL_ID ="NOTIFICATION_CHANNEL_ID", NOTIFICATION_CHANNEL_NAME ="NOTIFICATION_CHANNEL_NAME";


    @Override
    public void onTileAdded() {
        Toast.makeText(getApplicationContext(),"Tap on tile to start using Pencil. ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTileRemoved() {
        Toast.makeText(getApplicationContext(),"Do not remove tile from Quick settings !!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {

        RemoteInput remoteInput = new RemoteInput.Builder(REMOTE_INPUT_KEY)
                .setLabel("Type here to add new note.")
                .build();

        Intent intent = new Intent(this, temp.class);
        intent.putExtra("notification", true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action actionNotification = new NotificationCompat.Action.Builder(
                R.drawable.icon,
                "Add Note", pendingIntent)
                .addRemoteInput(remoteInput)
                .build();



        Intent dismissNotification = new Intent(this,Dismiss.class);
        PendingIntent pendingIntentDismiss = PendingIntent.getBroadcast(this,0,dismissNotification,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action dismissAction = new NotificationCompat.Action.Builder(
                R.drawable.icon,
                "Dismiss",
                pendingIntentDismiss).build();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }






        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_action_name)
                .setOngoing(true)
                .setShowWhen(true)
                .addAction(actionNotification)
                .addAction(dismissAction)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setColor(getResources().getColor(R.color.notificationColor))
                .setContentTitle("Hey there!")
                .setContentText("Click on add note to proceed.")
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_MAX);


        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }





}
