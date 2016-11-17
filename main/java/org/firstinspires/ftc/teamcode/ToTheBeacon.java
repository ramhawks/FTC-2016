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

enum Direction {
    LEFT, RIGHT;
}

@Autonomous(name = "To The Beacon", group = "Main")
public class ToTheBeacon extends OpMode {
    private RamhawkHardware robot;

    private double turnSpeed;

    private boolean done;

    @Override
    public void init() {
        robot = new RamhawkHardware();
        robot.init(hardwareMap);
        done = false;

        turnSpeed = 0.5;
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

    public void turn90(Direction direction) {
        robot.rightMotor.setPower(direction == Direction.RIGHT ? -turnSpeed : turnSpeed);
        robot.leftMotor.setPower(direction == Direction.RIGHT ? turnSpeed : -turnSpeed);

        try {
            Thread.sleep(1600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        robot.rightMotor.setPower(0.0);
        robot.leftMotor.setPower(0.0);
    }

    public void turn45(Direction direction) {
        robot.rightMotor.setPower(direction == Direction.RIGHT ? -turnSpeed : turnSpeed);
        robot.leftMotor.setPower(direction == Direction.RIGHT ? turnSpeed : -turnSpeed);

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
                    turn90(Direction.RIGHT);
                    driveForward(48);

                    //driveForward(48);
                }
            }

            done = true;
        }
    }
}
