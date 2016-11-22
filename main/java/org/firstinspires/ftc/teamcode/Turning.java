package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Team3090 on 11/19/2016.
 */

@TeleOp(name="Turning", group="Testing")
public class Turning extends DebuggableOpMode {
    //private RamhawkHardware robot;
    private Robot robot;
    private boolean go;

    private double theta = -Math.PI / 2;

    private SensorManager manager;
    private Sensor sensor;

    //private double percent;

    @Override
    void m_init() {
        /*robot = new RamhawkHardware();
        robot.init(hardwareMap);
        go = false;
        percent = 0.95;*/

        robot = new Robot(hardwareMap);

        /*manager = (SensorManager) robot.hwMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        manager.registerListener(new SensorEventListener() {
            private static final double NS2S = 1.0 / 1000000000.0;
            private double timestamp;
            public double totalRotation = 0;

            private boolean started = false;

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (timestamp != 0) {
                    final double dT = (event.timestamp - timestamp) * NS2S;

                    // We are just listening to axis Z
                    double axisZ = (double)event.values[2];

                    totalRotation += axisZ * dT;

                    if (go) {
                        if (!started) {
                            totalRotation = 0;
                            started = true;
                        }

                        if (Math.abs(totalRotation) >= Math.abs(theta)) {
                            go = false;
                            started = false;
                        }
                    }
                }

                timestamp = event.timestamp;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);*/

        addDebugVar("Percent", robot.turningPercent, 0.01);
    }

    @Override
    void m_loop() {
        telemetry.addData("Percent", robot.turningPercent);

        if (gamepad1.a && !go) {
            robot.turn(Math.PI / 2);
        }
    }

    /*private void turn90() {
        robot.leftMotor.setPower(0.5);
        robot.rightMotor.setPower(-0.5);

        theta = percent * -Math.PI /2;

        go = true;

        while (go) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
    }*/

    @Override
    void m_init_loop() {

    }

    @Override
    void setDebugVars(Object[] values) {
        robot.turningPercent = (double) values[0];
    }
}
