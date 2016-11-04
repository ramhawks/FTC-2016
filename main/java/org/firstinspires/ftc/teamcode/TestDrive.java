package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Test Drive", group = "Testing")
public class TestDrive extends LinearOpMode {
    private RamhawkHardware robot;
    private double driveTime;
    private boolean yBefore;
    private boolean aBefore;
    private ElapsedTime timer;
    private boolean init = false;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        timer = new ElapsedTime(0);

        while (opModeIsActive() && timer.seconds() <= driveTime) {
            robot.leftMotor.setPower(0.45);
            robot.rightMotor.setPower(0.45);

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
            yBefore = false;
            aBefore = false;
            init = true;
        }

        telemetry.addData("Drive Time", driveTime);
        telemetry.update();

        if (gamepad1.y) {
            if (!yBefore) {
                driveTime += 0.05;
                yBefore = true;
            }
        } else yBefore = false;

        if (gamepad1.a) {
            if (!aBefore) {
                driveTime -= 0.05;
                aBefore = true;
            }
        } else aBefore = false;
    }
}
