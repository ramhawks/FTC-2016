package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Test Drive", group = "Testing")
public class TestDrive extends DebuggableOpMode {
    private RamhawkHardware robot;
    private double driveTime;
    private double speed;
    private boolean running;

    private boolean go;

    private ElapsedTime timer;

    @Override
    void m_init() {
        timer = new ElapsedTime();

        robot = new RamhawkHardware();
        robot.init(hardwareMap);
        driveTime = 1.0;
        speed = 0.5;
        running = false;

        go = false;

        addDebugVar("Drive Time", driveTime);
        addDebugVar("Speed", speed);

        timer.reset();
    }

    @Override
    void m_loop() {
        if (running) {
            while (timer.seconds() <= driveTime) {
                robot.leftMotor.setPower(speed);
                robot.rightMotor.setPower(speed);

                robot.waitForTick(40);
            }

            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            running = false;
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
        driveTime = (Double) values[0];
        speed = (Double) values[1];
    }
}
