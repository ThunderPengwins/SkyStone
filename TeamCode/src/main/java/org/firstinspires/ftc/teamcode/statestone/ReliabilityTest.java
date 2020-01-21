package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Reliability", group = "Stest")
public class ReliabilityTest extends Myriad {
    //
    /*
    Test 1: Speed
      Step 1
     */
    //
    public void runOpMode() {
        //
        fullInit();
        //
        waitForStartify();
        //
        moveToPosition(90,.3,false);
        //
        sleep(200);
        //
        strafeToPosition(90, .3,false);
        //
        sleep(200);
        //
        moveToPosition(-90,.3,false);
        //
        sleep(200);
        //
        strafeToPosition(-90,.3,false);
        //
    }
    //
}
