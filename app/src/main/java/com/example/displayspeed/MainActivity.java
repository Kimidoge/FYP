package com.example.displayspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LocationListener , SensorEventListener {

    DatabaseHelper myDb;


    private static final String TAG = "MainActivity";
    // for accelerometer component
    private TextView xText, yText , zText, xTextGyro, yTextGyro , zTextGyro;
    private ToggleButton toggle;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private SensorManager sM;
    private SensorEventListener accelerometerListener,gyroscopeEventListener;
    int readingRate = 500000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);

        //Assign TextViews to specific axises
        xText =  (TextView) findViewById(R.id.xText);
        yText =  (TextView) findViewById(R.id.yText);
        zText =  (TextView) findViewById(R.id.zText);


        xTextGyro = (TextView) findViewById(R.id.xTextGyro);
        yTextGyro = (TextView) findViewById(R.id.yTextGyro);
        zTextGyro = (TextView) findViewById(R.id.zTextGyro);

        toggle = (ToggleButton) findViewById(R.id.toggleButton);   //toggle for start/stop recording

        //Creating the sensor manager; SENSOR_SERVICE is used to access sensors.
        sM = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Accelerometer Sensor.
        accelerometer = sM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(accelerometer != null){

            //Register sensor listener;
            sM.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate initializing sensors");

        } else{
            xText.setText("Accelerometer not supported");
            yText.setText("Accelerometer not supported");
            zText.setText("Accelerometer not supported");
        }


        //GYRO Sensor.
        gyroscope = sM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscope != null){

            //Register sensor listener;
            sM.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate initializing sensors");

        } else{
            xTextGyro.setText("GYROSCOPE not supported");
            yTextGyro.setText("GYROSCOPE not supported");
            zTextGyro.setText("GYROSCOPE not supported");
        }


        //create class for location manager
        LocationManager locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.onLocationChanged(null);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensorType = event.sensor;

        if(sensorType.getType()==Sensor.TYPE_ACCELEROMETER) {
            xText.setText("X: " + event.values[0]);
            yText.setText("Y: " + event.values[1]);
            zText.setText("Z: " + event.values[2]);



        }  else if (sensorType.getType() == Sensor.TYPE_GYROSCOPE){
            xTextGyro.setText("X: " + event.values[0]);
            yTextGyro.setText("Y: " + event.values[1]);
            zTextGyro.setText("Z: " + event.values[2]);
        }


        //call sqlite helper function to insert new line
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //NOT USED

    }

    @Override
    protected void onResume() {
        super.onResume();
        sM.registerListener(accelerometerListener, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sM.registerListener(gyroscopeEventListener, gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sM.unregisterListener(this);

    }


    @Override
    public void onLocationChanged(Location location) {

        TextView speedText = (TextView)findViewById(R.id.speedTextView);

        if(location == null){
            speedText.setText("0.0 km/h");
        }
        else{
            double currentSpeed = location.getSpeed();

            speedText.setText(currentSpeed*3.6 + "km/h");
        }

    }


}