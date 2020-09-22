package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous (name="L3-Tape", group="auto")
@Disabled
public class L3t extends Myriad {
    //
    public void runOpMode(){
        //
        fullDOInit();
        //
        waitForStartify();
        //
        moveToPosition(10,.3);
        //
    }
    //
}
