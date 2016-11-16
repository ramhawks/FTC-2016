package org.firstinspires.ftc.teamcode;

import android.os.Environment;
import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Team 11581 on 11/14/2016.
 */

@Autonomous(name = "To The Beacon", group = "Main")
public class ToTheBeacon extends OpMode {
    private RamhawkHardware robot;

    private double turnSpeed;

    @Override
    public void init() {
        robot = new RamhawkHardware();

        turnSpeed = 0.5;
    }

    private double inchesToSeconds(double distance) {
        // 11 in / sec
        return distance / 11.0;
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

    public void turn90(boolean right) {
        robot.rightMotor.setPower(right ? -turnSpeed : turnSpeed);
        robot.leftMotor.setPower(right ? turnSpeed : -turnSpeed);

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        robot.rightMotor.setPower(0.0);
        robot.leftMotor.setPower(0.0);
    }

    public void turn45(boolean right) {
        robot.rightMotor.setPower(right ? -turnSpeed : turnSpeed);
        robot.leftMotor.setPower(right ? turnSpeed : -turnSpeed);

        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        robot.rightMotor.setPower(0.0);
        robot.leftMotor.setPower(0.0);
    }

    @Override
    public void loop() {
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
                driveForward(24);
                turn45(true);
                driveForward(Math.sqrt(12 ^ 2 + 12 ^ 2));
                turn45(true);
                driveForward(24);
            }
        }
    }
}
