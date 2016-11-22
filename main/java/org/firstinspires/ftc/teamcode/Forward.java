package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Team3090 on 11/19/2016.
 */

@Autonomous(name="Forward", group="Main")
public class Forward extends OpMode {
    private boolean done;
    private Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        done = false;
    }

    @Override
    public void loop() {
        if (!done) {
            robot.driveForward(48);
            done = true;
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
