package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Test Left Move", group = "Stest")
public class TestLeftMove extends HoloLumi {
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
        move(-1,0,.6);
        //
        Long currentTime = java.lang.System.currentTimeMillis();
        Long taskTimer = java.lang.System.currentTimeMillis();
        while (currentTime < taskTimer + 5000){
            currentTime = java.lang.System.currentTimeMillis();
            telemetry.addData("frontLeft", frontLeft.getPower());
            telemetry.addData("frontRight", frontRight.getPower());
            telemetry.addData("backLeft",backLeft.getPower());
            telemetry.update();
        }
        still();
        //
    }
    //
}
