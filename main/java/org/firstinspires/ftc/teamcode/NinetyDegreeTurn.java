package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "90 degree turn", group = "Testing")
public class NinetyDegreeTurn extends LinearOpMode {
    // Turn time
    private RamhawkHardware robot           = new RamhawkHardware();

    private static double turnTime = 1.8;

    static final double TURN_SPEED = 0.5;
    private ElapsedTime runtime = new ElapsedTime();


    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        final View relativeLayout = ((Activity) robot.hwMap.appContext).findViewById(R.id.RelativeLayout);



        waitForStart();

        //Message to driver station
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        robot.leftMotor.setPower(TURN_SPEED);
        robot.rightMotor.setPower(-TURN_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < turnTime)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    @Override
    protected void handleLoop() {
        super.handleLoop();

        telemetry.addData("a", gamepad1.a);
        telemetry.addData("y", gamepad1.y);
        telemetry.update();

        if (gamepad1.y) {
            turnTime = turnTime + 0.05;
            sleep(1000);
        }
        if (gamepad1.a) {
            turnTime = turnTime - 0.05;
            sleep(1000);
        }

        telemetry.addData("turnTime", turnTime);
    }
}
