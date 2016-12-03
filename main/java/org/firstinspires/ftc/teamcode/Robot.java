package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Team11581 on 11/19/2016.
 */

public class Robot {
    private final double turnSpeed;
    public RamhawkHardware hardware;
    public double turningPercent;
    public double[] linear_acceleration = new double[3];
    public double actualSpeed;
    // Driving
    private boolean driving;
    private double drivingDistance;
    // Turning
    private double theta;
    private boolean turning;
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor linearAccelerometer;

    public Robot(HardwareMap hwMap) {
        hardware = new RamhawkHardware(hwMap);

        turnSpeed = 0.5;
        turning = false;
        turningPercent = 0.7;
        theta = -Math.PI / 2;

        sensorManager = (SensorManager) hardware.hwMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sensorManager.registerListener(new SensorEventListener() {
            private static final double NS2S = 1.0 / 1000000000.0;
            public double totalRotation = 0;
            private double timestamp;
            private boolean started = false;

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (timestamp != 0) {
                    final double dT = (event.timestamp - timestamp) * NS2S;

                    // We are just listening to axis Z
                    double axisZ = (double) event.values[2];

                    totalRotation += axisZ * dT;

                    if (turning) {
                        if (!started) {
                            totalRotation = 0;
                            started = true;
                        }

                        if (Math.abs(totalRotation) >= Math.abs(theta)) {
                            turning = false;
                            started = false;
                        }
                    }
                }

                timestamp = event.timestamp;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        }, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(new SensorEventListener() {
            private float[] gravity = new float[3];
            //private float[] linear_acceleration = new float[3];

            @Override
            public void onSensorChanged(SensorEvent event) {
                // In this example, alpha is calculated as t / (t + dT),
                // where t is the low-pass filter's time-constant and
                // dT is the event delivery rate.

                final float alpha = 0.8f;

                // Isolate the force of gravity with the low-pass filter.
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                // Remove the gravity contribution with the high-pass filter.
                linear_acceleration[0] = event.values[0] - gravity[0];
                linear_acceleration[1] = event.values[1] - gravity[1];
                linear_acceleration[2] = event.values[2] - gravity[2];


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        }, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);

        actualSpeed = 11.0;

    }

    private double inchesToSeconds(double distance) {
        if (distance < 0) return -1;

        if (distance <= 4)
            return Math.sqrt(distance / 14.78);
        else
            return (distance + 4.14) / actualSpeed;

        // actualSpeed used to be 15.5
    }

    public void driveForward(double distance) {
        hardware.rightMotor.setPower(.6);
        hardware.leftMotor.setPower(.65);

        driving = true;
        drivingDistance = distance;

        while (driving)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        hardware.rightMotor.setPower(0.0);
        hardware.leftMotor.setPower(0.0);
    }

    public void turn(double mtheta) {
        hardware.rightMotor.setPower(theta < 0 ? -turnSpeed : turnSpeed);
        hardware.leftMotor.setPower(theta < 0 ? turnSpeed : -turnSpeed);

        turning = true;
        theta = mtheta * turningPercent;

        while (turning) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        hardware.rightMotor.setPower(0.0);
        hardware.leftMotor.setPower(0.0);
    }
}
