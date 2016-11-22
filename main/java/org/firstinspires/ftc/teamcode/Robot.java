package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Team11581 on 11/19/2016.
 *
 */

public class Robot {

    public RamhawkHardware hardware;

    private final double turnSpeed;
    private boolean turning;
    public double turningPercent;
    private boolean done;
    private double theta;

    private SensorManager sensorManager;
    private Sensor sensor;

    public double actualSpeed;

    public Robot(HardwareMap hwMap) {
        hardware = new RamhawkHardware(hwMap);

        turnSpeed = 0.5;
        turning = false;
        turningPercent = 0.7;
        done = false;
        theta = -Math.PI / 2;

        sensorManager = (SensorManager) hardware.hwMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(new SensorEventListener() {
            private static final double NS2S = 1.0 / 1000000000.0;
            private double timestamp;
            public double totalRotation = 0;
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
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);

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
        hardware.leftMotor.setPower(.6);

        try {
            Thread.sleep((long) inchesToSeconds(distance) * 1000);
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
