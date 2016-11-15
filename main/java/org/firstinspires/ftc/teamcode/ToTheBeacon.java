package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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

    public void driveForward(double distance){
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
        }catch (InterruptedException e) {
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
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        robot.rightMotor.setPower(0.0);
        robot.leftMotor.setPower(0.0);
    }

    @Override
    public void loop() {
        boolean startNearVortex = true;
        boolean endNearVortex = true;
        boolean blue = true;

        // if on blue, turn right; if on red, turn left

        if (startNearVortex && blue) {
            if (endNearVortex) {
                driveForward(24);
                turn45(true);
                driveForward(Math.sqrt(12^2 + 12^2));
                turn45(true);
                driveForward(24);
            }
        }
    }
}
