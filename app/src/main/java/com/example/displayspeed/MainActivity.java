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
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.CompoundButton;
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
    public float accelX, accelY, accelZ, gyroX, gyroY, gyroZ , offsetAccelY, offsetAccelZ;
    private boolean flagToggle;
    private double globalSpeed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);
        Log.d("TAG Database helper","myDB created");

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
            sM.registerListener(this, accelerometer, 1000000);
            Log.d("TAG 1 Accelerometer ", "onCreate initializing accelerometer");

        } else{
            xText.setText("Accelerometer not supported");
            yText.setText("Accelerometer not supported");
            zText.setText("Accelerometer not supported");
        }


        //GYRO Sensor.
        gyroscope = sM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscope != null){

            //Register sensor listener;
            sM.registerListener(this, gyroscope, 1000000);
            Log.d("TAG 2 Gyroscope", "onCreate initializing gyroscope");

        } else{
            xTextGyro.setText("GYROSCOPE not supported");
            yTextGyro.setText("GYROSCOPE not supported");
            zTextGyro.setText("GYROSCOPE not supported");
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flagToggle = false;
                Log.d("TAG initial switch:", ""+flagToggle);

                //Log.d("Switch state", " "+isChecked);

                if(toggle.isChecked()){
                    flagToggle = true;
                    Toast.makeText(MainActivity.this, "Recording data", Toast.LENGTH_SHORT).show();
                    Log.d("TAG Switch :", " "+isChecked);

                } else {
                    flagToggle = false;
                    Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                    Log.d("TAG Switch :", " "+isChecked);
                }

            }
        });



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
        Location location = null;



        if(sensorType.getType()==Sensor.TYPE_ACCELEROMETER) {


            accelX = event.values[0];
            accelY = event.values[1];
            accelZ = event.values[2];

           xText.setText("X: " + event.values[0]);
           yText.setText("Y: " + event.values[1]);
           zText.setText("Z: " + event.values[2]);

            xText.setText("X: " + accelX);
            yText.setText("Y: " + accelY);
            zText.setText("Z: " + accelZ);




        }  else if (sensorType.getType() == Sensor.TYPE_GYROSCOPE){
            xTextGyro.setText("X: " + event.values[0]);
            yTextGyro.setText("Y: " + event.values[1]);
            zTextGyro.setText("Z: " + event.values[2]);

           gyroX = event.values[0];
           gyroY = event.values[1];
           gyroZ = event.values[2];
        }


        if (flagToggle) {

            //a bunch of new codes
            DatabaseHelper.getInstance().insertTable(accelX, accelY, accelZ, gyroX, gyroY, gyroZ, globalSpeed);
            Log.d("TAG sent:","data sent");


          // DatabaseHelper.getInstance().insertTable(accelX, accelY, accelZ, gyroX, gyroY, gyroZ, globalSpeed);
          // Log.d("TAG Send Data:", "Data sent");

        }
      


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //NOT USED

    }

    @Override
    protected void onResume() {
        super.onResume();
        sM.registerListener(accelerometerListener, accelerometer,1000000);
        sM.registerListener(gyroscopeEventListener, gyroscope,1000000);
        Toast.makeText(this, "onResume started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause started", Toast.LENGTH_SHORT).show();
        sM.unregisterListener(accelerometerListener);
        sM.unregisterListener(gyroscopeEventListener);
        super.onPause();
       // sM.unregisterListener(this);

    }
    

    @Override
    public void onLocationChanged(Location location) {

        TextView speedText = (TextView)findViewById(R.id.speedTextView);

        if(location == null){
            speedText.setText("0.0 km/h");
        }
        else{
            double currentSpeed = location.getSpeed();
            //globalSpeed = currentSpeed;

            speedText.setText(currentSpeed*3.6 + "km/h");
            globalSpeed = currentSpeed*3.6;
          //  Log.d("TAG speed", " "+ globalSpeed);
            Log.d("TAG GLOBALSPEED :", ""+globalSpeed);
        }



    }


}