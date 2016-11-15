package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

/**
 * Created by edwargix on 11/4/16.
 * Special OpMode that allows for modifying variables in "debug mode"
 */

public abstract class DebuggableOpMode extends OpMode {

    private boolean debug;
    private boolean leave_debug;

    private boolean debug_buttons;
    private boolean dpad_up_before;
    private boolean dpad_down_before;
    private boolean y_before;
    private boolean a_before;
    private boolean y;
    private boolean a;

    private ArrayList<DebugVar> vars;
    private int index;

    // Init method for subclass
    abstract void m_init();

    @Override
    public final void init() {
        debug = false;

        leave_debug = true;
        vars = new ArrayList<>();

        debug_buttons = false;
        dpad_up_before = false;
        dpad_down_before = false;
        y_before = false;
        a_before = false;
        y = false;
        a = false;

        m_init();
    }

    // Init method for subclass
    abstract void m_loop();

    @Override
    public final void loop() {
        if (gamepad1.start) {
            if (!debug_buttons) {
                debug = !debug;
                if (!debug) leave_debug = true;
                debug_buttons = true;
            }
        } else debug_buttons = false;

        if (leave_debug) {
            Object[] values = new Object[vars.size()];

            DebugVar var;
            for (int i = 0; i < values.length; i++) {
                var = vars.get(i);
                values[i] = var.value;
            }

            setDebugVars(values);

            leave_debug = false;
        }

        if (debug) {

            telemetry.addLine("DEBUG MODE");

            DebugVar obj;
            for (int i = 0; i < vars.size(); i++) {
                obj = vars.get(i);
                telemetry.addData(
                        (i == index ? "--> " : "    ") + obj.name, obj.value);
            }

            telemetry.update();

            obj = vars.get(index);

            y = false;
            if (gamepad1.y) {
                if (!y_before) {
                    y = true;
                    y_before = true;
                }
            } else y_before = false;

            a = false;
            if (gamepad1.a) {
                if (!a_before) {
                    a = true;
                    a_before = true;
                }
            } else a_before = false;

            telemetry.addData("y", y);
            telemetry.addData("a", a);
            telemetry.update();

            if (obj.value instanceof Integer)
                obj.value = (Integer) obj.value + (y ? 1 : (a ? -1 : 0));
            else if (obj.value instanceof Double)
                obj.value = (Double) obj.value + (y ? 0.05d : (a ? -0.05d : 0.0d));
            else if (obj.value instanceof Boolean && (y || a))
                obj.value = !((Boolean) obj.value);
            vars.set(index, obj);

            if (gamepad1.dpad_up) {
                if (!dpad_up_before) {
                    index--;
                    if (index < 0) index = vars.size() - 1;
                    dpad_up_before = true;
                }
            } else if (gamepad1.dpad_down) {
                dpad_up_before = false;
                if (!dpad_down_before) {
                    index++;
                    if (index >= vars.size()) index = 0;
                    dpad_down_before = true;
                }
            } else {
                dpad_up_before = false;
                dpad_down_before = false;
            }
        } else m_loop();
    }

    // Init loop for subclass
    abstract void m_init_loop();

    @Override
    public final void init_loop() {


        m_init_loop();
    }


    /*
    After debug mode is finished, give changes to subclass.
    IMPORTANT: The subclass must remember the order of the tracked variables
     */
    abstract void setDebugVars(Object[] values);

    /**
     * Used by subclass to add variable to track
     *
     * @param name is merely used to display vars on driver station
     */

    protected final void addDebugVar(String name, Object object) {
        vars.add(new DebugVar(name, object));
    }

    // Simple structure for
    private class DebugVar {
        String name;
        Object value;

        DebugVar(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}
