package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="90 degree turn", group="Testing")
public class NinetyDegreeTurn extends LinearOpMode {
    private RamhawkHardware hardware;

    static final double     TURN_SPEED    = 0.5;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        hardware.init(hardwareMap);

        waitForStart();

        // turn 90 degrees
        int turnTime;

        //Message to driver station
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        hardware.leftMotor.setPower(TURN_SPEED);
        hardware.rightMotor.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

    }
}
