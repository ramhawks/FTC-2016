package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Test Drive", group = "Testing")
public class TestDrive extends LinearOpMode {
    private RamhawkHardware robot;
    private double driveTime;
    private double speed;
    private boolean yBefore;
    private boolean aBefore;
    private boolean lbBefore;
    private boolean rbBefore;
    private boolean index;
    private ElapsedTime timer;
    private boolean init = false;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        timer = new ElapsedTime();
        timer.reset();

        while (opModeIsActive() && timer.seconds() <= driveTime) {
            robot.leftMotor.setPower(speed);
            robot.rightMotor.setPower(speed);

            robot.waitForTick(40);
        }

        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
    }

    @Override
    protected void handleLoop() {
        super.handleLoop();

        if (!init) {
            // init
            robot = new RamhawkHardware();
            robot.init(hardwareMap);
            driveTime = 1.0;
            speed = 0.5;
            yBefore = false;
            aBefore = false;
            lbBefore = false;
            rbBefore = false;
            index = true;
            init = true;
        }

        telemetry.addData((index ? "--> " : "    ") + "Drive Time", driveTime);
        telemetry.addData((index ? "    " : "--> ") + "Speed", speed);
        telemetry.update();

        if (gamepad1.left_bumper) {
            if (!lbBefore) {
                index = !index;
                lbBefore = true;
            }
        } else lbBefore = false;

        if (gamepad1.right_bumper) {
            if (!rbBefore) {
                index = !index;
                rbBefore = true;
            }
        } else rbBefore = false;

        if (gamepad1.y) {
            if (!yBefore) {
                if (index)
                    driveTime += 0.05;
                else
                    speed += 0.05;
                yBefore = true;
            }
        } else yBefore = false;

        if (gamepad1.a) {
            if (!aBefore) {
                if (index)
                    driveTime -= 0.05;
                else
                    speed -= 0.05;
                aBefore = true;
            }
        } else aBefore = false;
    }
}
