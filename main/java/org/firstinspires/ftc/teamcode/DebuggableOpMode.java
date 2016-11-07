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
    private boolean all_before;

    private ArrayList<DebugVar> vars;
    private int index;

    // Init method for subclass
    abstract void m_init();

    @Override
    public final void init() {
        debug = false;
        all_before = false;
        leave_debug = true;
        vars = new ArrayList<>();

        m_init();
    }

    // Init method for subclass
    abstract void m_loop();

    @Override
    public final void loop() {
        if (gamepad1.a && gamepad1.b && gamepad1.x && gamepad1.y) {
            if (!all_before) {
                debug = !debug;
                if (!debug) leave_debug = true;
            }
        } else all_before = false;

        if (debug) {

            telemetry.addLine("DEBUG MODE");

            DebugVar obj = vars.get(index);

            if (obj.value instanceof Integer)
                obj.value = (Integer) obj.value + (gamepad1.y ? 1 : (gamepad1.a ? -1 : 0));
            else if (obj.value instanceof Double)
                obj.value = (Double) obj.value + (gamepad1.y ? 0.05d : (gamepad1.a ? -0.05d : 0.0d));
            else if (obj.value instanceof Boolean && (gamepad1.y || gamepad1.a))
                obj.value = !((Boolean) obj.value);
            vars.set(index, obj);

            if (gamepad1.dpad_up) {
                index--;
                if (index < 0) index = vars.size() - 1;
            } else if (gamepad1.dpad_down) {
                index++;
                if (index >= vars.size()) index = 0;
            }

            for (int i = 0; i < vars.size(); i++) {
                telemetry.addData(
                        (i == index ? "--> " : "    ") + obj.name, obj.value);
            }

            telemetry.update();

        }

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

        m_loop();
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
    Used by subclass to add variable to track
    @param name is merely used to display vars on driver station
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
