package com.example.displayspeed;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String SENSOR_TABLE = "SENSOR_TABLE";
    public static final String COLUMN_ACCEL_X = "ACCEL_X";
    public static final String COLUMN_ACCEL_Y = "ACCEL_Y";
    public static final String COLUMN_ACCEL_Z = "ACCEL_Z";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(Context context) {
        super(context, "Please.db", null, 1);

        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= "CREATE TABLE " + SENSOR_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCEL_X + " INTEGER, " + COLUMN_ACCEL_Y + " INTEGER, " + COLUMN_ACCEL_Z + " INTEGER)";

        db.execSQL(createTableStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
