package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Reliability", group = "Stest")
@Disabled
public class ReliabilityTest extends Myriad {
    //
    /*
    Test 1: Speed
      Step 1
     */
    //
    public void runOpMode() {
        //
        fullDOInit();
        //
        waitForStartify();
        //
        stageToPosition(50,.4,.2);
        //
        /*sleep(200);
        //
        strafeToPosition(90, .3);
        //
        sleep(200);
        //
        moveToPosition(-90,.3);
        //
        sleep(200);
        //
        strafeToPosition(-90,.3);*/
        //
    }
    //
}
