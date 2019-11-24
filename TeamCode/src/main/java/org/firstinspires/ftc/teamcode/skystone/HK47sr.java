package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-stone-red", group = "Sreal")
public class HK47sr extends HoloLumi {
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
        moveToPosition(25 + gap1, .3, true);//24
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
        move(-1,0,.4);
        while (!(leftR.getDistance(DistanceUnit.INCH) < 39) && opModeIsActive()){
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
            move(-1,0,.4);
            while (!(leftR.getDistance(DistanceUnit.INCH) < 32) && opModeIsActive()){
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
                move(-1, 0, .4);
                while (!(leftR.getDistance(DistanceUnit.INCH) < 25) && opModeIsActive()) {
                    telemetry.addData("distance", leftR.getDistance(DistanceUnit.INCH));
                    telemetry.addData("left front motor", frontLeft.getPower());
                    telemetry.update();
                }
                still();
                //
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
        //
        motorsWithEncoders();//back up
        move(0, -1, .4);
        while (!(backR.getDistance(DistanceUnit.INCH) < 28) && opModeIsActive()) {}
        still();
        //
        sleep(200);
        //
        strafeToPosition(55, .7, true);//go under bridge
        //
        turnWithGyro(90, .3);
        //
        grabber.setPosition(grabOpen);
        //
        moveToPosition(-17,.4,false);
    }
    //
}
