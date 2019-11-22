package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-all", group = "Sreal")
public class HK47a extends HoloLumi {
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
        waitForStartify();
        //
        leftHook.setPosition(hookUpLeft);//0 is down, 0.6 is up
        rightHook.setPosition(hookUpRight);//0 is down, 0.6 is up
        //
        //forward(.5, .5);
        //
        moveToPosition(24 + gap1, .3, true);//23
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
        //
        motorsWithEncoders();
        move(-1,0,.2);
        while (!(leftR.getDistance(DistanceUnit.INCH) < 37) && opModeIsActive()){
            telemetry.addData("distance", leftR.getDistance(DistanceUnit.INCH));
            telemetry.addData("left front motor", frontLeft.getPower());
            telemetry.update();
        }
        //
        if (leftColor.red() == 0){
            position = 1;
            still();
        }else {
            motorsWithEncoders();
            move(-1,0,.2);
            while (!(leftR.getDistance(DistanceUnit.INCH) < 30) && opModeIsActive()){
                telemetry.addData("distance", leftR.getDistance(DistanceUnit.INCH));
                telemetry.addData("left front motor", frontLeft.getPower());
                telemetry.update();
            }
            //
            if (leftColor.red() == 0) {
                position = 2;
                still();
            }else {
                position = 3;
                //
                motorsWithEncoders();
                move(-1, 0, .2);
                while (!(leftR.getDistance(DistanceUnit.INCH) < 23) && opModeIsActive()) {
                    telemetry.addData("distance", leftR.getDistance(DistanceUnit.INCH));
                    telemetry.addData("left front motor", frontLeft.getPower());
                    telemetry.update();
                }
                still();
                //
            }
        }
        //
        moveToPosition(-7, .3, true);
        //
        leftHook.setPosition(hookDownLeft);
        //
        sleep(500);
        //
        motorsWithEncoders();
        move(0, 2, .3);
        while (!(qbert.getState()) && opModeIsActive()) {
            if (upity.getDistance(DistanceUnit.INCH) < 3.3){
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
        //
        lifter.setPower(-.3);
        while (!george.getState() && opModeIsActive()){}
        lifter.setPower(0);
        //
        grabber.setPosition(grabClosed);
        //
        sleep(500);
        //
        lifter.setPower(0.6);
        //
        while (!(upity.getDistance(DistanceUnit.INCH) > 2.2) && opModeIsActive()){}
        lifter.setPower(0);
        //
        leftHook.setPosition(hookUpLeft);
        //
        motorsWithEncoders();
        move(0, -1, .3);
        while (!(backR.getDistance(DistanceUnit.INCH) < 26) && opModeIsActive()) {}
        still();
        //
        sleep(200);
        //
        strafeToPosition(55, .7, true);
        //
        motorsWithEncoders();
        move(1, 0, .2);
        while (!(rightR.getDistance(DistanceUnit.INCH) < 13) && opModeIsActive()) {
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
        move(0,1,.2);
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
        sleep(500);
        move(0,-1,.3);
        while (!(backR.getDistance(DistanceUnit.INCH) < 7) && opModeIsActive()) {
            if (upity.getDistance(DistanceUnit.INCH) < 3.3){
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
        leftHook.setPosition(hookUpLeft);
        rightHook.setPosition(hookUpRight);
        sleep(200);
        //
        move(-1,0,.3);
        extender.setPower(-.2);
        while ((rightR.getDistance(DistanceUnit.INCH) < 37) && opModeIsActive()) {
            telemetry.addData("right range", rightR.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
        still();
        //
        sleep(2000);//200
        //
        turnToAngle(0,.05);
        //
        move(0,1,.4);
        extender.setPower(-.2);
        while (!(backR.getDistance(DistanceUnit.INCH) > 42) && opModeIsActive()) {}
        still();
        extender.setPower(0);
        //
        turnWithGyro(180, .3);
        //
        move(-1,0,.3);
        while (!(leftR.getDistance(DistanceUnit.INCH) < 11) && opModeIsActive()) {}
        still();
        //
        sleep(200);
        //
        moveToPosition(20, .3, true);
        //
    }
    //
}
