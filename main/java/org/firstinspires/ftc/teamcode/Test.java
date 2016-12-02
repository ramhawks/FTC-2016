package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.text.DecimalFormat;

@TeleOp(name = "Test", group = "Testing")
public class Test extends OpMode {

    private DecimalFormat df;

    private float[] pos;

    private Robot robot;
    private float delta;

    private float[] speed;

    private long last_time;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        df = new DecimalFormat("##.#######");

        speed = new float[]{0.0f,0.0f,0.0f};

        pos = new float[]{0.0f,0.0f,0.0f};

        delta = -1;
        last_time = System.currentTimeMillis();
    }

    double[] floored = new double[3];

    @Override
    public void loop() {
        delta = (System.currentTimeMillis() - last_time) / 1000.0f;
        last_time = System.currentTimeMillis();

        for (int i = 0; i < robot.linear_acceleration.length; i++) {
            //telemetry.addData("a" + i, df.format(robot.linear_acceleration[i]));
            telemetry.addData("a" + i, "%.7f", robot.linear_acceleration[i]);
        }

        // 0.001

        for (int i = 0; i < floored.length; i++) {
            floored[i] = Math.floor(10.0 * robot.linear_acceleration[i]) / 10.0;
            telemetry.addData("f" + i, "%.7f", floored[i]);
        }

        /*float[] accel = {
                (float) Math.floor(100 * robot.linear_acceleration[0]) / 100f,
                (float) Math.floor(100 * robot.linear_acceleration[1]) / 100f,
                (float) Math.floor(100 * robot.linear_acceleration[2]) / 100f
        };*/

        /*for (int i = 0; i < speed.length; i++) {
            speed[i] += robot.linear_acceleration[i] * delta;
            telemetry.addData("s" + i, df.format(speed[i]));
        }

        for (int i = 0; i < pos.length; i++) {
            pos[i] += speed[i] * delta;
            telemetry.addData("p" + i, df.format(pos[i]));
        }*/
    }
}
