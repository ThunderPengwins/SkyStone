package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="L3-Red-Foundation", group="auto")
public class L3fr extends Myriad {
    //
    public void runOpMode(){
        //
        fullDOInit();
        //
        waitForStartify();
        //
        moveToPosition(20,.2);
        //
        sleep(200);
        //
        strafeToPosition(13,.2);
        //
        sleep(200);
        //
        moveToPosition(14,.2);
        //
        scooper.setPower(0.5);
        sleep(700);
        //
        moveToPosition(-60,.3);
        //
        scooper.setPower(-0.5);
        while (scoopUp.getState()) {}
        scooper.setPower(0);
        //
        turnPast(0,.05,false);
        //
        strafeToPosition(-42,.2);
        //
    }
    //
}
