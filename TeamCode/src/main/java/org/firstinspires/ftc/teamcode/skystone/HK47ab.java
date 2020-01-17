package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Disabled
@Autonomous(name = "HK47-all-blue", group = "Sreal")
public class HK47ab extends HoloLumi {
    //
    Double gap1 = 0.0;//2.0
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
        leftColor.enableLed(true);
        rightColor.enableLed(true);
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
        moveToPosition(25 + gap1, .3, true);//24
        //moveToPosition(4, .2, false);
        //
        //while (!(backR.getDistance(DistanceUnit.INCH) > 15 + gap1) && opModeIsActive()){ };
        //
        still();
        //
        sleep(500);
        //
        telemetry.addData("Skystone", rightColor.red() == 0);
        telemetry.update();
        //
        Integer position = 0;
        //
        motorsWithEncoders();
        move(1,0,.4);
        while (!(rightR.getDistance(DistanceUnit.INCH) < 37) && opModeIsActive()){
            telemetry.addData("distance", rightR.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
        //
        if (rightColor.red() == 0){
            position = 1;
            still();
        }else {
            motorsWithEncoders();
            move(1,0,.4);
            while (!(rightR.getDistance(DistanceUnit.INCH) < 32) && opModeIsActive()){
                telemetry.addData("distance", rightR.getDistance(DistanceUnit.INCH));
                telemetry.update();
            }
            //
            if (rightColor.red() == 0) {
                position = 2;
                still();
            }else {
                position = 3;
                //
                motorsWithEncoders();
                move(1, 0, .4);
                while (!(rightR.getDistance(DistanceUnit.INCH) < 23.5) && opModeIsActive()) {
                    telemetry.addData("distance", rightR.getDistance(DistanceUnit.INCH));
                    telemetry.update();
                }
                still();
                //
            }
        }
        //
        moveToPosition(-7, .5, true);
        //
        rightHook.setPosition(hookDownRight);
        //
        sleep(500);
        //
        motorsWithEncoders();
        move(0, 1, .4);//charge (to stone)
        double fix = backR.getDistance(DistanceUnit.INCH);
        while (!(qbert.getState()) && opModeIsActive() && !(fix > 60)) {
            if (upity.getDistance(DistanceUnit.INCH) < 3.5){
                lifter.setPower(0.4);
            }else if (upity.getDistance(DistanceUnit.INCH) > 4){
                lifter.setPower(-0.3);
                extender.setPower(-.3);
            }else {
                lifter.setPower(0);
                extender.setPower(0);
            }
            if (backR.getDistance(DistanceUnit.INCH) < 500){
                fix = backR.getDistance(DistanceUnit.INCH);
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
        rightHook.setPosition(hookUpRight);
        //
        motorsWithEncoders();//back up
        move(0, -1, .4);
        while (!(backR.getDistance(DistanceUnit.INCH) < 25) && opModeIsActive()) {}
        still();
        //
        sleep(200);
        //
        turnToAngle(0,.07);//premature correct
        //
        strafeToPosition(-55, .7, true);//go under bridge
        //
        turnToAngle(0,.07);//correct
        //
        motorsWithEncoders();
        //
        move(-1, 0, .4);//go to foundation
        double distanceHold = leftR.getDistance(DistanceUnit.INCH);
        while (!(distanceHold < 17) && opModeIsActive()) {
            if (Math.abs(leftR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && leftR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = leftR.getDistance(DistanceUnit.INCH);
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
        }
        still();
        sleep(200);
        //
        turnToAngle(0,0.07);
        //
        move(0,1,.2);
        extender.setPower(1.0);//press against foundation
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
        distanceHold = backR.getDistance(DistanceUnit.INCH);
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
        }
        still();
        lifter.setPower(0);
        leftHook.setPosition(hookUpLeft);
        rightHook.setPosition(hookUpRight);
        sleep(200);
        //
        turnToAngle(0, .07);
        //
        distanceHold = leftR.getDistance(DistanceUnit.INCH);
        //
        move(1,0,.5);//go right
        while (!(distanceHold > 33) && opModeIsActive()) {
            if (Math.abs(leftR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && leftR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = leftR.getDistance(DistanceUnit.INCH);
                telemetry.addData("new", distanceHold);
            }
            telemetry.addData("right range", leftR.getDistance(DistanceUnit.INCH));
            telemetry.addData("done",distanceHold > 33);
            telemetry.update();
        }
        still();
        extender.setPower(0);
        //
        sleep(200);//200
        //
        turnToAngle(0,.07);
        //
        move(0,1,.6);
        distanceHold = backR.getDistance(DistanceUnit.INCH);
        while (!(distanceHold > 40) && opModeIsActive()) {
            if (Math.abs(backR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && backR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = backR.getDistance(DistanceUnit.INCH);
            }
        }
        still();
        //
        turnWithGyro(170, -.3);
        //
        telemetry.addData("begin","move");
        telemetry.update();
        //
        distanceHold = rightR.getDistance(DistanceUnit.INCH);
        motorsWithEncoders();
        move(1,0,.4);//move in front of foundation - P
        while (!(distanceHold < 15) && opModeIsActive()) {
            if (Math.abs(rightR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && rightR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = rightR.getDistance(DistanceUnit.INCH);
            }
            telemetry.addData("rightD", rightR.getDistance(DistanceUnit.INCH));
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
        strafeToPosition(-50, .8, false);
    }
    //
}
