package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Team3090 on 11/19/2016.
 */

@Autonomous(name="Forward", group="Main")
public class Forward extends OpMode {
    public RamhawkHardware hardware;
    private boolean done;
    private Robot robot;
    private ElapsedTime timer;
    private double autoTime = 4.0;
    private boolean autoRun;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        done = false;
        autoRun = true;
        timer = new ElapsedTime();
    }

    @Override
    public void loop() {
        /*if (!done) {
            robot.driveForward(-28);
            done = true;
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        if (autoRun) {
            robot.hardware.leftMotor.setPower(-.95);
            robot.hardware.rightMotor.setPower(-1);
            timer.reset();

            while (timer.seconds() < autoTime) {
                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", timer.seconds());
                telemetry.update();
            }

            robot.hardware.leftMotor.setPower(0);
            robot.hardware.rightMotor.setPower(0);
            autoRun = false;

        }
    }
}
