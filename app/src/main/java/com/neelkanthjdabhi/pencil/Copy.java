package com.neelkanthjdabhi.pencil;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Neelkanth Dabhi on 05-01-2018.
 */

public class Copy extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("this is s1", temp.dbToString(context));
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context,"Copied to clipboard",Toast.LENGTH_SHORT).show();

    }
}
