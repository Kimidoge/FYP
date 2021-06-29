package com.example.displayspeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SENSOR_TABLE = "SENSOR_TABLE";
    public static final String COLUMN_ACCEL_X = "ACCEL_X";
    public static final String COLUMN_ACCEL_Y = "ACCEL_Y";
    public static final String COLUMN_ACCEL_Z = "ACCEL_Z";

    public static final String COLUMN_GYRO_X = "GYRO_X";
    public static final String COLUMN_GYRO_Y = "GYRO_Y";
    public static final String COLUMN_GYRO_Z = "GYRO_Z";
    public static final String COLUMN_CURRENT_SPEED = "CURRENT_SPEED";
    public static final String COLUMN_ACTIVITY = "ACTIVITY";

    SQLiteDatabase db;
    private static DatabaseHelper mInstance;

    public DatabaseHelper(Context context) {
        super(context, "TESTDATA.db", null, 1);
        //super(context, String.valueOf(Calendar.getInstance().getTime())+".db", null, 1);

         db = this.getWritableDatabase();
    }

    public static DatabaseHelper getInstance(){
        if(mInstance == null){
            synchronized (DatabaseHelper.class){
                if(mInstance == null){
                    mInstance = new DatabaseHelper(BaseApp.getApp());
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableStatement= "CREATE TABLE " + SENSOR_TABLE + "( " +  COLUMN_ACCEL_X + " REAL, " + COLUMN_ACCEL_Y + " REAL, " + COLUMN_ACCEL_Z + " REAL, time DATETIME DEFAULT CURRENT_TIME)";
        String createTableStatement= "CREATE TABLE " + SENSOR_TABLE + "( TIME DATETIME DEFAULT CURRENT_TIME, " +  COLUMN_ACCEL_X + " REAL, " + COLUMN_ACCEL_Y + " REAL, " + COLUMN_ACCEL_Z + " REAL, " + COLUMN_GYRO_X + " REAL, " + COLUMN_GYRO_Y + " REAL, " + COLUMN_GYRO_Z + " REAL, " + COLUMN_CURRENT_SPEED + " REAL, " + COLUMN_ACTIVITY + " TEXT)";

        db.execSQL(createTableStatement);
        Log.d("TAG database :", "DATABASE CREATED");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SENSOR_TABLE);
        onCreate(db);

    }

    public void insertTable(float x, float y, float z ,float a, float b , float c, double speed, String activity){  //put onSensorChanged data to database

        ContentValues contentvalues = new ContentValues();
        contentvalues.put("ACCEL_X", x);
        contentvalues.put("ACCEL_Y", y);
        contentvalues.put("ACCEL_Z", z);

        contentvalues.put("GYRO_X", a);
        contentvalues.put("GYRO_Y", b);
        contentvalues.put("GYRO_Z", c);

        contentvalues.put("CURRENT_SPEED", speed);

       contentvalues.put("ACTIVITY", activity);


        db.insert(SENSOR_TABLE, null, contentvalues);



    }
}
