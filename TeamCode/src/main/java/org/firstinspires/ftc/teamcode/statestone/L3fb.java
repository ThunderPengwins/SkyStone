package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="L3-Blue-Foundation", group="auto")
public class L3fb extends Myriad {
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
        strafeToPosition(-20,.2);
        //
        sleep(200);
        //
        moveToPosition(14,.2);
        //
        scooper.setPower(0.5);
        sleep(700);
        //
        moveToPosition(-55,.3);
        //
        scooper.setPower(-0.5);
        while (scoopUp.getState()) {}
        scooper.setPower(0);
        //
        //turnToAngle(-180,.05);
        //
        strafeToPosition(57,.2);
        //
    }
    //
}