package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

@Autonomous(name = "To The Beacon", group = "Main")
public class ToTheBeacon extends OpMode {
    //private RamhawkHardware hardware;

    /*private double turnSpeed;

    private boolean done;

    private boolean turning;
    private double theta;

    private SensorManager sensorManager;
    private Sensor sensor;*/

    private Robot robot;

    private boolean done;

    private float[] hsvValues = {0f,0f,0f};

    @Override
    public void init() {
        /*hardware = new RamhawkHardware();
        hardware.init(hardwareMap);
        hardware.colorSensor.enableLed(false);*/

        robot = new Robot(hardwareMap);
        done = false;

        /*turnSpeed = 0.5;
        turning = false;

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
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);*/
    }

    /*private double inchesToSeconds(double distance) {
        if (distance < 0) return -1;

        if (distance <= 4)
            return Math.sqrt(distance / 14.78);
        else
            return (distance + 4.14) / 11.0;

        // 11 used to be 15.5
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

    public void turn(double theta) {
        hardware.rightMotor.setPower(theta < 0 ? -turnSpeed : turnSpeed);
        hardware.leftMotor.setPower(theta < 0 ? turnSpeed : -turnSpeed);

        turning = true;
        this.theta = theta * 0.70;

        while (turning) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        hardware.rightMotor.setPower(0.0);
        hardware.leftMotor.setPower(0.0);
    }*/

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
                Toast.makeText(hardwareMap.appContext, "CRITICAL! Could not write to file! Using default values t,t,t", Toast.LENGTH_LONG).show();
                options[0] = 't';
                options[1] = 't';
                options[2] = 't';
                //options[3] = 't';
            }

            boolean startNearVortex = options[0] == 't';
            boolean endNearVortex = options[1] == 't';
            boolean blue = options[2] == 't';
            //boolean hitCapball = options[3] == 't';

            // if on blue, turn right; if on red, turn left

            /*if (startNearVortex) {
                if (hitCapball) {
                    driveForward(12);
                    turn((blue ? 1 : -1) * Math.PI / 4);
                    driveForward(Math.sqrt(288));
                    turn((blue ? -1 : 1) * Math.PI / 4);
                    driveForward(48);
                } else {

                }
            }*/

            // TODO: 16.5 x 16.5 (with wheels, each front wheel is 1)

            if (startNearVortex) {
                if (endNearVortex) {
                    /*driveForward(48);
                    turn((blue ? -1 : 1) * Math.PI / 2.0);
                    driveForward(44);*/

                    robot.driveForward(22);
                    robot.turn((blue ? -1 : 1) * Math.PI / 4);
                    robot.driveForward(Math.sqrt(24*24 + 24*24));
                    robot.turn((blue ? -1 : 1) * Math.PI / 4);
                    robot.driveForward(22);


                } else {
                    /*driveForward(24);
                    turn((blue ? -1 : 1) * Math.PI / 4.0);
                    // 288 = 12^2 + 12^2
                    driveForward(Math.sqrt(288));
                    turn((blue ? 1 : -1) * Math.PI / 4.0);
                    driveForward(72);
                    turn((blue ? -1 : 1) * Math.PI / 2);
                    driveForward(44);*/

                    robot.driveForward(22);
                    robot.turn((blue ? -1 : 1) * Math.PI / 4);
                    robot.driveForward(Math.sqrt(24*24 + 24*24));
                    robot.turn((blue ? 1 : -1) * Math.PI / 4);
                    robot.driveForward(48);
                    robot.turn((blue ? -1 : 1) * Math.PI / 2);
                    robot.driveForward(22);
                }

                Color.RGBToHSV(robot.hardware.colorSensor.red() * 8, robot.hardware.colorSensor.green() * 8, robot.hardware.colorSensor.blue() * 8, hsvValues);

                if (hsvValues[0] > 180) {
                    telemetry.addData("BLUE", "BLUE");

                    if (!blue) {
                        robot.driveForward(4);
                    }
                } else {
                    telemetry.addData("RED", "RED");

                    if (blue) {
                        robot.driveForward(4);
                    }
                }

                telemetry.update();
            }

            done = true;
        } else {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
