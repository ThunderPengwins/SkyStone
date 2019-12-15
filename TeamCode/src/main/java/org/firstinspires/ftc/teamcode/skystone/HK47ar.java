package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-all-red", group = "Sreal")
public class HK47ar extends HoloLumi {
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
        //
        telemetry.addData("Hardware initialized","");
        //
        initGyro();
        //
        if (light){
            leftColor.enableLed(true);
            rightColor.enableLed(true);
        }else {
            leftColor.enableLed(false);
            rightColor.enableLed(false);
        }
        //
        double origin = getAngle();
        //
        waitForStartify();
        //
        //<editor-fold desc="Identify Skystone">
        leftHook.setPosition(hookUpLeft);//0 is down, 0.6 is up
        rightHook.setPosition(hookUpRight);//0 is down, 0.6 is up
        //
        grabber.setPosition(grabOpen);
        //
        //forward(.5, .5);
        //
        moveToPosition(26 + gap1, .3, true);//24.5
        //moveToPosition(4, .2, false);
        //
        //while (!(backR.getDistance(DistanceUnit.INCH) > 15 + gap1) && opModeIsActive()){ };
        //
        still();
        //
        sleep(500);
        //
        telemetry.addData("Skystone", leftColor.red() == 0);
        telemetry.update();
        //
        Integer position = 0;
        moveWithSensor("left",39.0,true,.4,-1.0,0.0,false);
        //
        if (leftColor.red() == 0){
            position = 1;
            still();
        }else {
            moveWithSensor("left",32.0,true,.4,-1.0,0.0,false);
            //
            if (leftColor.red() == 0) {
                position = 2;
                still();
            }else {
                position = 3;
                moveWithSensor("left",25.0,true,.4,-1.0,0.0,true);
            }
        }
        //
        moveToPosition(-7, .5, true);
        //
        leftHook.setPosition(hookDownLeft);
        //
        sleep(500);
        //
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
        }
        still();
        boolean fail = false;
        if (backR.getDistance(DistanceUnit.INCH) > 60){//safety
            fail = true;
        }
        //
        lifter.setPower(-.4);//gantry down to stone
        while (!george.getState() && opModeIsActive() && !fail){}
        lifter.setPower(0);
        //
        if (fail){
            lifter.setPower(-.4);
            while (!(upity.getDistance(DistanceUnit.INCH) < 2.2)){}
            lifter.setPower(0);
        }
        //
        grabber.setPosition(grabClosed);//grab it
        //
        sleep(500);
        //
        lifter.setPower(0.4);//gantry up
        while (!(upity.getDistance(DistanceUnit.INCH) > 2.2) && opModeIsActive()){}
        lifter.setPower(0);
        //
        leftHook.setPosition(hookUpLeft);
        //back up
        moveWithSensor("back",28.0,true,0.4,0.0,-1.0,true);//
        //</editor-fold>
        //
        sleep(200);
        //
        turnToAngle(0,.07);//premature correct
        //
        strafeToPosition(55, .7, true);//go under bridge
        //
        turnToAngle(0,.07);//correct
        //
        double distanceHold = rightR.getDistance(DistanceUnit.INCH);
        motorsWithEncoders();
        move(1, 0, .4);//go to foundation
        while (!(distanceHold < 17) && opModeIsActive()) {
            if (Math.abs(rightR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && rightR.getDistance(DistanceUnit.INCH) < 500) {
                distanceHold = rightR.getDistance(DistanceUnit.INCH);
            }
            if (upity.getDistance(DistanceUnit.INCH) < 6){
                lifter.setPower(0.4);
            }else if (upity.getDistance(DistanceUnit.INCH) > 10){
                lifter.setPower(-0.4);
                extender.setPower(-.3);
            }else {
                lifter.setPower(0);
                extender.setPower(0);
            }
            if (Math.abs(rightR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && rightR.getDistance(DistanceUnit.INCH) < 500) {
                distanceHold = rightR.getDistance(DistanceUnit.INCH);
            }
            telemetry.addData("rightR", rightR.getDistance(DistanceUnit.INCH));
            telemetry.addData("used", distanceHold);
            telemetry.update();
        }
        still();
        sleep(200);
        //
        turnToAngle(0,.07);
        //
        move(0,1,.2);//press against foundation
        extender.setPower(1.0);
        while ((leftTouch.getState() || rightTouch.getState()) && opModeIsActive()) {}
        still();
        //
        sleep(1000);
        //
        extender.setPower(0);
        //
        grabber.setPosition(grabOpen);
        //
        leftHook.setPosition(hookDownLeft);
        rightHook.setPosition(hookDownRight);
        //
        sleep(500);
        //
        int counter = 0;
        move(0,-1,.3);//pull back
        extender.setPower(-.7);
        while (!(distanceHold < 8) && opModeIsActive()) {
            if (Math.abs(backR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && backR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = backR.getDistance(DistanceUnit.INCH);
                counter++;
            }
            if (upity.getDistance(DistanceUnit.INCH) < 3.3){
                lifter.setPower(0.4);
            }else if (upity.getDistance(DistanceUnit.INCH) > 4){
                lifter.setPower(-0.4);
            }else {
                lifter.setPower(0);
            }
            if (distanceHold < 8){
                break;
            }
            telemetry.addData("counter", counter);
            telemetry.addData("backR",backR.getDistance(DistanceUnit.INCH));
            telemetry.addData("criteria", Math.abs(backR.getDistance(DistanceUnit.INCH) - distanceHold));
            telemetry.addData("used",distanceHold);
            telemetry.update();
        }
        still();
        lifter.setPower(0);
        leftHook.setPosition(hookUpLeft);
        rightHook.setPosition(hookUpRight);
        sleep(200);
        //
        turnToAngle(0, .07);
        //
        distanceHold = rightR.getDistance(DistanceUnit.INCH);
        //
        move(-1,0,.5);//go left
        while (!(distanceHold > 33) && opModeIsActive()) {
            if (Math.abs(rightR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && rightR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = rightR.getDistance(DistanceUnit.INCH);
                telemetry.addData("new", distanceHold);
            }
            telemetry.addData("right range", rightR.getDistance(DistanceUnit.INCH));
            telemetry.addData("done",distanceHold > 33);
            telemetry.update();
        }
        still();
        extender.setPower(0);
        //
        sleep(200);//200
        //
        telemetry.addData("fix","angle");
        telemetry.update();
        turnToAngle(0,.07);
        //
        distanceHold = backR.getDistance(DistanceUnit.INCH);
        move(0,1,.6);//go forward
        while (!(distanceHold > 40) && opModeIsActive()) {
            if (Math.abs(backR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && backR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = backR.getDistance(DistanceUnit.INCH);
            }
        }
        still();
        //
        turnWithGyro(170, .3);
        //
        telemetry.addData("begin","move");
        telemetry.update();
        //
        distanceHold = leftR.getDistance(DistanceUnit.INCH);
        motorsWithEncoders();
        move(-1,0,.4);//move in front of foundation - P
        while (!(distanceHold < 15) && opModeIsActive()) {
            if (Math.abs(leftR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && leftR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = leftR.getDistance(DistanceUnit.INCH);
            }
            telemetry.addData("leftD", leftR.getDistance(DistanceUnit.INCH));
            telemetry.addData("used", distanceHold);
            telemetry.addData("frontLeft",frontLeft.getPower());
            telemetry.addData("frontRight",frontRight.getPower());
            telemetry.update();
        }
        still();
        //
        telemetry.addData("done","move");
        telemetry.update();
        //
        sleep(100);
        //
        moveToPosition(30, .5, true);
        //
        strafeToPosition(45, .8, false);
    }
    //
}
