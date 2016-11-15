package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "90 Degree Turn", group = "Testing")
public class NinetyDegreeTurn extends DebuggableOpMode {
    private RamhawkHardware robot;

    private double turnTime;

    private double turnSpeed;

    private boolean running;

    private ElapsedTime timer;

    private boolean go;

    @Override
    void m_init() {
        robot = new RamhawkHardware();

        // So far, best value
        turnTime = 1.8;

        turnSpeed = 0.5;

        addDebugVar("Turn Time", turnTime);

        addDebugVar("Turn Speed", turnSpeed);

        running = false;

        timer = new ElapsedTime();

        go = false;
    }

    @Override
    void m_loop() {
        if (running) {
            robot.leftMotor.setPower(turnSpeed);
            robot.rightMotor.setPower(-turnSpeed);
            timer.reset();

            while (timer.seconds() < turnTime) {
                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", timer.seconds());
                telemetry.update();
            }

            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }

        if (gamepad1.b) {
            if (!go) {
                running = true;
                go = true;
            }
        } else go = false;
    }

    @Override
    void m_init_loop() {

    }

    @Override
    void setDebugVars(Object[] values) {
        turnTime = (double) values[0];
        turnSpeed = (double) values[1];
    }



    /*
    private RamhawkHardware robot = new RamhawkHardware();

    private static double turnTime = 1.8;

    private static final double TURN_SPEED = 0.5;
    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        // Layout for Robot Controller
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
    */
}
