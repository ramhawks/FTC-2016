package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "90 Degree Turn", group = "Testing")
public class NinetyDegreeTurn extends LinearOpMode {
    private RamhawkHardware robot = new RamhawkHardware();

    private static double turnTime = 1.8;

    private static final double TURN_SPEED = 0.5;
    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        // Layour for Robot Controller
        final View relativeLayout = ((Activity) robot.hwMap.appContext).findViewById(R.id.RelativeLayout);

        waitForStart();

        // Message to driver station
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        robot.leftMotor.setPower(TURN_SPEED);
        robot.rightMotor.setPower(-TURN_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < turnTime)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.waitForTick(40);
    }

    private boolean yBefore = false;
    private boolean aBefore = false;

    // Adjust Turn Time prior to turning
    @Override
    protected void handleLoop() {
        super.handleLoop();

        telemetry.addData("a", gamepad1.a);
        telemetry.addData("y", gamepad1.y);
        telemetry.update();

        if (gamepad1.y) {
            if (!yBefore) {
                turnTime += 0.05;
                yBefore = true;
            }
        } else yBefore = false;

        if (gamepad1.a) {
            if (!aBefore) {
                turnTime -= 0.05;
                aBefore = true;
            }
        } else aBefore = false;

        telemetry.addData("turn time", turnTime);
    }
}
