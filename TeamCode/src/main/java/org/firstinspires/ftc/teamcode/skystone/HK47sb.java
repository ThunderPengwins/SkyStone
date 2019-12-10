package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-stone-blue", group = "Sreal")
public class HK47sb extends HoloLumi {
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
        telemetry.addData("Hardware initialized", "");
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
        moveToPosition(26 + gap1, .3, true);//24
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
        move(1, 0, .4);
        while (!(rightR.getDistance(DistanceUnit.INCH) < 37) && opModeIsActive()) {
            telemetry.addData("distance", rightR.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
        //
        if (rightColor.red() == 0) {
            position = 1;
            still();
        } else {
            motorsWithEncoders();
            move(1, 0, .4);
            while (!(rightR.getDistance(DistanceUnit.INCH) < 32) && opModeIsActive()) {
                telemetry.addData("distance", rightR.getDistance(DistanceUnit.INCH));
                telemetry.update();
            }
            //
            if (rightColor.red() == 0) {
                position = 2;
                still();
            } else {
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
        //
        motorsWithEncoders();
        move(0, 1, .4);//charge (to stone)
        double fix = backR.getDistance(DistanceUnit.INCH);
        while (!(qbert.getState()) && opModeIsActive() && !(fix > 60)) {
            if (Math.abs(backR.getDistance(DistanceUnit.INCH) - fix) < 20 && backR.getDistance(DistanceUnit.INCH) < 500) {
                fix = backR.getDistance(DistanceUnit.INCH);
            }
            if (upity.getDistance(DistanceUnit.INCH) < 3.5) {
                lifter.setPower(0.4);
            } else if (upity.getDistance(DistanceUnit.INCH) > 4) {
                lifter.setPower(-0.3);
                extender.setPower(-.3);
            } else {
                lifter.setPower(0);
                extender.setPower(0);
            }
        }
        still();
        boolean fail = false;
        if (backR.getDistance(DistanceUnit.INCH) > 60) {//safety
            fail = true;
        }
        //
        lifter.setPower(-.4);//gantry down to stone
        while (!george.getState() && opModeIsActive() && !fail) {
        }
        lifter.setPower(0);
        //
        if (fail) {
            lifter.setPower(-.4);
            while (!(upity.getDistance(DistanceUnit.INCH) < 2.2)) {
            }
            lifter.setPower(0);
        }
        //
        grabber.setPosition(grabClosed);//grab it
        //
        sleep(500);
        //
        lifter.setPower(0.4);//gantry up
        while (!(upity.getDistance(DistanceUnit.INCH) > 2.2) && opModeIsActive()) {
        }
        lifter.setPower(0);
        //
        rightHook.setPosition(hookUpRight);
        //
        motorsWithEncoders();//back up
        move(0, -1, .4);
        while (!(backR.getDistance(DistanceUnit.INCH) < 20) && opModeIsActive()) {
        }
        still();
        //
        sleep(200);
        //
        //turnToAngle(0,.07);
        //
        if (position == 3) {
            strafeToPosition(-65, .7, true);
        } else if(position == 2){
            strafeToPosition(-55, .7, true);//go under bridge
        }else{
            strafeToPosition(-45, .7, true);
        }
        //
        turnWithGyro(90, -.3);
        //
        grabber.setPosition(grabOpen);
        //
        moveToPosition(-17,.4,false);
    }
    //
}
