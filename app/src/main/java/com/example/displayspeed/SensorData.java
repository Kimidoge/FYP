package com.example.displayspeed;

public class SensorData {
    private double accelerometerX;
    private double accelerometerY;
    private double accelerometerZ;

    private double gyroX;
    private double gyroY;
    private double gyroZ;

    private float kmSpeed;

    //constructors


    public SensorData(double accelerometerX, double accelerometerY, double accelerometerZ, double gyroX, double gyroY, double gyroZ, float kmSpeed) {
        this.accelerometerX = accelerometerX;
        this.accelerometerY = accelerometerY;
        this.accelerometerZ = accelerometerZ;
        this.gyroX = gyroX;
        this.gyroY = gyroY;
        this.gyroZ = gyroZ;
        this.kmSpeed = kmSpeed;
    }

    public SensorData(){
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "accelerometerX=" + accelerometerX +
                ", accelerometerY=" + accelerometerY +
                ", accelerometerZ=" + accelerometerZ +
                ", gyroX=" + gyroX +
                ", gyroY=" + gyroY +
                ", gyroZ=" + gyroZ +
                ", kmSpeed=" + kmSpeed +
                '}';
    }

    public double getAccelerometerX() {
        return accelerometerX;
    }

    public void setAccelerometerX(double accelerometerX) {
        this.accelerometerX = accelerometerX;
    }

    public double getAccelerometerY() {
        return accelerometerY;
    }

    public void setAccelerometerY(double accelerometerY) {
        this.accelerometerY = accelerometerY;
    }

    public double getAccelerometerZ() {
        return accelerometerZ;
    }

    public void setAccelerometerZ(double accelerometerZ) {
        this.accelerometerZ = accelerometerZ;
    }

    public double getGyroX() {
        return gyroX;
    }

    public void setGyroX(double gyroX) {
        this.gyroX = gyroX;
    }

    public double getGyroY() {
        return gyroY;
    }

    public void setGyroY(double gyroY) {
        this.gyroY = gyroY;
    }

    public double getGyroZ() {
        return gyroZ;
    }

    public void setGyroZ(double gyroZ) {
        this.gyroZ = gyroZ;
    }

    public float getKmSpeed() {
        return kmSpeed;
    }

    public void setKmSpeed(float kmSpeed) {
        this.kmSpeed = kmSpeed;
    }
}
