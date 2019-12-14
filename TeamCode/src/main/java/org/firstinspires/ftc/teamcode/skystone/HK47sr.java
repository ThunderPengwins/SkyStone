package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-stone-red", group = "Sreal")
public class HK47sr extends HoloLumi {
    //
    public void runOpMode() {
        //
        motorHardware();
        //
        firstHarwares();
        //
        secondaryMotorReversals();
        //
        motorsWithEncoders();
        resetEncoders();
        //
        telemetry.addData("Hardware initialized","");
        //
        initGyro();
        //
        leftColor.enableLed(false);
        rightColor.enableLed(false);
        //
        double origin = getAngle();
        //
        waitForStartify();
        //
        leftHook.setPosition(hookUpLeft);//0 is down, 0.6 is up
        rightHook.setPosition(hookUpRight);//0 is down, 0.6 is up
        //
        grabber.setPosition(grabOpen);
        //
        //forward(.5, .5);
        //
        message = "forward-1";
        moveToPosition(26 + gap1, .3, true);//24.5
        //moveToPosition(4, .2, false);
        //
        //while (!(backR.getDistance(DistanceUnit.INCH) > 15 + gap1) && opModeIsActive()){ };
        //
        message = "correct-1";
        turnToAngle(0,.05);
        //
        still();
        //
        sleep(500);
        //
        telemetry.addData("Skystone", leftColor.red() == 0);
        telemetry.update();
        //
        message = "left-pos1";
        Integer position = 0;
        moveWithSensor("left",39.0,true,.4,-1.0,0.0,false);
        //
        if (leftColor.red() == 0){
            position = 1;
            still();
        }else {
            message = "left-pos2";
            moveWithSensor("left",32.5,true,.4,-1.0,0.0,false);
            //
            if (leftColor.red() == 0) {
                position = 2;
                still();
            }else {
                message = "left-pos3";
                position = 3;
                moveWithSensor("left",25.0,true,.4,-1.0,0.0,true);
            }
        }
        //
        message = "backup-1";
        moveToPosition(-7, .5, true);
        //
        leftHook.setPosition(hookDownLeft);
        //
        sleep(500);
        //
        message = "toStone-1";
        motorsWithEncoders();
        move(0, 1, .4);//charge (to stone)
        while (!(qbert.getState()) && opModeIsActive() && !(backR.getDistance(DistanceUnit.INCH) > 60)) {
            if (upity.getDistance(DistanceUnit.INCH) < 3.5){
                lifter.setPower(0.4);
            }else if (upity.getDistance(DistanceUnit.INCH) > 4){
                lifter.setPower(-0.4);
                extender.setPower(-.3);
            }else {
                lifter.setPower(0);
                extender.setPower(0);
            }
            telemetry.addData("stage",message);
            telemetry.addData("backR",backR.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
        still();
        boolean fail = false;
        if (backR.getDistance(DistanceUnit.INCH) > 60){//safety
            fail = true;
        }
        //
        message = "gantryDown-1";
        lifter.setPower(-.4);//gantry down to stone
        while (!george.getState() && opModeIsActive() && !fail){
            telemetry.addData("stage",message);
            telemetry.addData("fail",fail);
            telemetry.update();
        }
        lifter.setPower(0);
        //
        if (fail){
            lifter.setPower(-.4);
            while (!(upity.getDistance(DistanceUnit.INCH) < 2.2)){}
            lifter.setPower(0);
        }
        //
        message = "grabStone-1";
        setStage();
        //
        grabber.setPosition(grabClosed);//grab it
        //
        sleep(500);
        //
        message = "gantryUp-1";
        setStage();
        //
        lifter.setPower(0.4);//gantry up
        while (!(upity.getDistance(DistanceUnit.INCH) > 2.2) && opModeIsActive()){}
        lifter.setPower(0);
        //
        leftHook.setPosition(hookUpLeft);
        //back up
        message = "backup-2";
        moveWithSensor("back",28.0,true,0.4,0.0,-1.0,true);//
        //</editor-fold>
        //
        sleep(200);
        //
        message = "turn-toTape";
        turnWithGyro(87, .3);
        //
        message = "correct-2";
        turnToAngle(90,.05);//premature correct
        //
        if (position == 1){
            message = "move-tape-pos1";
            moveToPosition(40,.8,true);
        }else if (position == 2){
            message = "move-tape-pos1";
            moveToPosition(45,.8,true);
        }else{
            message = "move-tape-pos1";
            moveToPosition(50,.8,true);
        }
        //
        message = "release-stone";
        setStage();
        grabber.setPosition(grabOpen);
        //
        sleep(500);
        //
        moveToPosition(-15,.5,false);
        //
        double dis = 35 - ((position - 1) * 7.5);
        //
        message = "back-overTape";
        moveWithSensor("back",dis,true,.8,0.0,-1.0,true);
        //
        message = "turn-forStones";
        turnWithGyro(180,0.5);
        //
        message = "correct-3";
        turnToAngle(-92,.05);//correction
        //
        if (position != 3){
            leftHook.setPosition(hookDownLeft);
        }
        //
        message = "slide-intoDMs";
        moveWithSensor("left",34.0,false,.5,1.0,0.0,true);
        //
        leftHook.setPosition(hookUpLeft);
        //
        telemetry.addData("moving to","skystone");
        telemetry.update();
        //
        if (position == 3){
            message = "grab-2-pos3";
            moveToPosition(10,.3,false);
            //
            message = "turn1-secureStone-pos3";
            turnToAngle(-135,.6);
            message = "turn2-secureStone-pos3";
            turnToAngle(-180, .1);
            //
            message = "toStone-2-pos3";
            setStage();
            move(0, 1, .2);
            while (!qbert.getState() && opModeIsActive() && leftTouch.getState() && rightTouch.getState()) {}
            still();
        }else {
            message = "toStone-2-pos1,pos2";
            setStage();
            move(0, 1, .2);
            while (!qbert.getState() && opModeIsActive() && leftTouch.getState() && rightTouch.getState()) {}
            still();
        }
        //
        message = "gantryDown-2";
        setStage();
        lifter.setPower(-.4);//gantry down to stone
        while (!george.getState() && opModeIsActive()){}
        lifter.setPower(0);
        //
        message = "grabStone-2";
        setStage();
        grabber.setPosition(grabClosed);
        sleep(500);
        //
        message = "gantryUp-2";
        setStage();
        lifter.setPower(0.4);//gantry up
        while (!(upity.getDistance(DistanceUnit.INCH) > 2.2) && opModeIsActive()){}
        lifter.setPower(0);
        //
        if (position == 3){
            message = "turn-toTape-pos3";
            turnWithGyro(90,-.5);
        }else{
            message = "turn-toTape-pos1,pos2";
            turnWithGyro(180,-.5);
        }
        //
        if (position == 1){
            //
        }else if (position == 2){
            //
            message = "wrapMove-pos2";
            move(.34,.94,.5);
            //
        }else{
            //62
        }
        motorsWithEncoders();
        double distanceHold = backR.getDistance(DistanceUnit.INCH);
        while (!(distanceHold < 38) && opModeIsActive()) {
            if (Math.abs(backR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && leftR.getDistance(DistanceUnit.INCH) < 500) {
                distanceHold = backR.getDistance(DistanceUnit.INCH);
            }
            telemetry.addData("stage",message);
            telemetry.addData("leftR", leftR.getDistance(DistanceUnit.INCH));
            telemetry.addData("used", distanceHold);
            telemetry.update();
        }
        still();
        message = "overTape-2";
        moveWithSensor("back",62.0,false,.5,0.0,1.0,true);
        //
        message = "ontoTape";
        moveToPosition(-10,.5,false);
    }
    //
}
