package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
        robot = new RamhawkHardware(hardwareMap);

        // So far, best value
        turnTime = 1.8;

        turnSpeed = 0.5;

        addDebugVar("Turn Time", turnTime, 0.05);

        addDebugVar("Turn Speed", turnSpeed, 0.05);

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


}
