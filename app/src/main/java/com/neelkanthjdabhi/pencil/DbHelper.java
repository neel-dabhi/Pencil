package com.neelkanthjdabhi.pencil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neelkanth Dabhi on 02-01-2018.
 */

public class DbHelper extends SQLiteOpenHelper {



    public DbHelper(Context context) {
        super(context, "string.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table string (msg text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists string");
        onCreate(sqLiteDatabase);
    }
}
