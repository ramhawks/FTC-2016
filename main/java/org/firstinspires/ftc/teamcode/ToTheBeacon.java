package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Team 11581 on 11/14/2016.
 */

enum Direction {
    LEFT, RIGHT;
}

@Autonomous(name = "To The Beacon", group = "Main")
public class ToTheBeacon extends OpMode {
    private RamhawkHardware robot;

    private double turnSpeed;

    private boolean done;

    private boolean turning;
    private double theta;

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    public void init() {
        robot = new RamhawkHardware();
        robot.init(hardwareMap);
        done = false;

        turnSpeed = 0.5;
        turning = false;

        sensorManager = (SensorManager) robot.hwMap.appContext.getSystemService(Context.SENSOR_SERVICE);
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
                    double axisZ = (double)event.values[2];

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
    }

    private double inchesToSeconds(double distance) {
        if (distance < 0) return -1;

        if (distance <= 4)
            return Math.sqrt(distance / 14.78);
        else
            return (distance + 4.14) / 15.5;

    }

    public void driveForward(double distance) {
        robot.rightMotor.setPower(.6);
        robot.leftMotor.setPower(.6);

        try {
            Thread.sleep((long) inchesToSeconds(distance) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        robot.rightMotor.setPower(0.0);
        robot.leftMotor.setPower(0.0);
    }

    public void turn(double theta) {
        robot.rightMotor.setPower(theta < 0 ? -turnSpeed : turnSpeed);
        robot.leftMotor.setPower(theta < 0 ? turnSpeed : -turnSpeed);

        turning = true;
        this.theta = theta * 0.955;

        while (turning) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        robot.rightMotor.setPower(0.0);
        robot.leftMotor.setPower(0.0);
    }

    @Override
    public void loop() {
        if (!done) {
            File settingsFile = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "auto_init.txt");

            char[] options = new char[3];

            try {
                FileReader fr = new FileReader(settingsFile);
                fr.read(options);
                fr.close();
            } catch (IOException e) {
                Toast.makeText(robot.hwMap.appContext, "CRITICAL! Could not write to file! Using default values t,t,t", Toast.LENGTH_LONG).show();
                options[0] = 't';
                options[1] = 't';
                options[2] = 't';
            }

            boolean startNearVortex = options[0] == 't';
            boolean endNearVortex = options[1] == 't';
            boolean blue = options[2] == 't';

            // if on blue, turn right; if on red, turn left

            if (startNearVortex && blue) {
                if (endNearVortex) {
/*                    driveForward(24);
                    turn45(Direction.RIGHT);
                    driveForward(Math.sqrt(12 * 12 + 12 * 12));
                    turn45(Direction.RIGHT);
                    driveForward(24);*/

                    driveForward(48);
                    turn(Math.PI / 2.0);
                    driveForward(48);

                    //driveForward(48);
                } else {
                    driveForward(48);
                    turn(Math.PI);
                    driveForward(48);
                }
            }

            done = true;
        }
    }
}
