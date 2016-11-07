package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by edwargix on 11/4/16.
 * Simple class demonstrating the new Debugaable capabilities
 */

@TeleOp(name = "Test", group = "Testing")
public class Test extends DebuggableOpMode {
    int a;
    boolean b;
    double c;

    @Override
    void m_init() {
        a = 5;
        addDebugVar("a", a);
        b = false;
        addDebugVar("b", b);
        c = 0.5d;
        addDebugVar("c", c);
    }

    @Override
    void m_loop() {

    }

    @Override
    void m_init_loop() {

    }

    @Override
    void setDebugVars(Object[] values) {
        a = (int)values[0];
        b = (boolean)values[1];
        c = (double)values[2];
    }
}
