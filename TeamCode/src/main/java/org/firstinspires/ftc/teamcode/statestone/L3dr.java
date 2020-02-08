package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="L3-Red-Double", group="auto")
public class L3dr extends Myriad {
    //
    public void runOpMode(){
        //
        fullDOInit();
        //
        waitForStartify();
        //
        telemetry.addData("Waiting for","DogeCV");
        telemetry.update();
        while (skyStoneDetector.getAltRectx() == 0 && opModeIsActive()){}
        telemetry.addData("DogeCV","Initialized");
        telemetry.update();
        //
        int position = getSkystonePositionRed();
        telemetry.addData("position",position);
        telemetry.addData("X pos",skyStoneDetector.getAltRectx());
        telemetry.addData("angle",getAngle());
        telemetry.update();
        //
        //to decision point
        moveToPosition(8.6, 0.2);
        //
        if (position == 1){//1,2,3, 3 is wall
            strafeToPosition(8,.2);
        }else if (position == 3){
            strafeToPosition(-8,.2);
        }
        //
        if (position == 3) {
            moveToPosition(30, 0.3);
            //scoop it
            scooper.setPower(0.5);
            //
            sleep(700);
            //
            scooper.setPower(0);
            //
            sleep(500);
            //pull back
            moveToPosition(-16, .3);
            //turn -90
            turnWithGyro(83, .2);
            //
            turnPast(88,.05,true);
            //
            //scoop up
            scooper.setPower(-0.5);
            while (scoopUp.getState()) {}
            scooper.setPower(0);
            //across tape 56
            stageToPosition(54,.4,.2);
            //
            moveToPosition(-12,.2);
            //
        }else {
            //to stone
            moveToPosition(30, 0.2);
            //scoop it
            scooper.setPower(0.5);
            //
            sleep(700);
            //
            scooper.setPower(0);
            //
            sleep(500);
            //pull back
            moveToPosition(-16, .2);
            //turn -90
            turnWithGyro(85, .2);
            //scoop up
            scooper.setPower(-0.5);
            while (scoopUp.getState()) {}
            scooper.setPower(0);
            //across tape
            int x = 0;
            if (position == 2) {
                x = 8;
            }
            //
            moveToPosition(40 + x, .3);
            //
            turnPast(88,.05,false);
            //
            sleep(500);
            //back
            moveToPosition(-61.5 - x, 0.3);
            //turn 90
            turnWithGyro(87, -.2);
            //to 2nd stone
            moveToPosition(17, .2);
            //scoop it
            scooper.setPower(0.5);
            //
            sleep(700);
            //
            scooper.setPower(0);
            //
            sleep(500);
            //go back
            moveToPosition(-16, .2);
            //turn -90
            turnWithGyro(87, .2);
            //scoop up
            scooper.setPower(-0.5);
            while (scoopUp.getState()) {}
            scooper.setPower(0);
            //
            turnPast(90,.1,false);
            //
            moveToPosition(62 + x, .3);
            //
            moveToPosition(-12, .2);
        }
    }
    //
}
