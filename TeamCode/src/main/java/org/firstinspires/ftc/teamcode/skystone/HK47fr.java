package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-found-red", group = "Sreal")
public class HK47fr extends HoloLumi {
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
        move(0,1,.4);//press against foundation
        telemetry.update();
        while ((leftTouch.getState() || rightTouch.getState()) && opModeIsActive()) {}
        still();
        //
        sleep(1000);
        //
        leftHook.setPosition(hookDownLeft);
        rightHook.setPosition(hookDownRight);
        //
        sleep(500);
        //
        //double distanceHold = backR.getDistance(DistanceUnit.INCH);
        //int counter = 0;
        move(0,-1,.3);//pull back
        while (!(backR.getDistance(DistanceUnit.INCH) < 8) && opModeIsActive()) {
            /*if (Math.abs(backR.getDistance(DistanceUnit.INCH) - distanceHold) < 20 && backR.getDistance(DistanceUnit.INCH) < 500){
                distanceHold = backR.getDistance(DistanceUnit.INCH);
                counter++;
            }*/
            /*if (distanceHold < 8){
                break;
            }*/
            //telemetry.addData("counter", counter);
            telemetry.addData("backR",backR.getDistance(DistanceUnit.INCH));
            //telemetry.addData("criteria", Math.abs(backR.getDistance(DistanceUnit.INCH) - distanceHold));
            //telemetry.addData("used",distanceHold);
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
        double distanceHold = rightR.getDistance(DistanceUnit.INCH);
        //
        move(-1,0,.5);//go left
        while (!(distanceHold > 33) && opModeIsActive()) {
            if (Math.abs(rightR.getDistance(DistanceUnit.INCH) - distanceHold) < 5 && rightR.getDistance(DistanceUnit.INCH) < 500){
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
        move(0,1,.6);//go forward
        while (!(backR.getDistance(DistanceUnit.INCH) > 40) && opModeIsActive()) {}
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
        turnToAngle(180, .07);
        //
        strafeToPosition(45, .5, false);
    }
    //
}
