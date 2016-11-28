package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test", group = "Testing")
public class Test extends OpMode {
    private Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("0", robot.linear_acceleration[0]);
        telemetry.addData("1", robot.linear_acceleration[1]);
        telemetry.addData("2", robot.linear_acceleration[2]);
    }
}
