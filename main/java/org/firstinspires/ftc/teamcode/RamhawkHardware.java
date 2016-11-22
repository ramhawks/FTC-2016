package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

class RamhawkHardware {
    // Motors
    DcMotor leftMotor = null;
    DcMotor rightMotor = null;
    DcMotor armMotor1 = null;
    // DcMotor armMotor2 = null;

    // Color Sensor
    ColorSensor colorSensor = null;
    boolean ledOn = false;
    float[] hsvValues = {0f, 0f, 0f};

    // Arm thrusts for both direction
    static final double ARM_UP_POWER = 1;
    static final double ARM_DOWN_POWER = -0.45;

    // Distance Sensor
    OpticalDistanceSensor distanceSensor;

    // Hardware Maps
    HardwareMap hwMap = null;

    // Timer for tick
    private ElapsedTime period = new ElapsedTime();

    public RamhawkHardware(HardwareMap hwMap) {
        init(hwMap);
    }

    // Initialize hardware
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor = hwMap.dcMotor.get("left_drive");
        rightMotor = hwMap.dcMotor.get("right_drive");
        armMotor1 = hwMap.dcMotor.get("throw");

        // Set directions of motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        armMotor1.setDirection(DcMotor.Direction.REVERSE);
        // armMotor2.setDirection(DcMotor.Direction.REVERSE);

        // Initialize color sensor
        colorSensor = hwMap.colorSensor.get("color_sensor");
        colorSensor.enableLed(ledOn);

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        armMotor1.setPower(0);
        // armMotor2.setPower(0);

        // Set all motors to run without encoders.
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // armMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        distanceSensor = hwMap.opticalDistanceSensor.get("sensor_ods");
    }

    /***
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
