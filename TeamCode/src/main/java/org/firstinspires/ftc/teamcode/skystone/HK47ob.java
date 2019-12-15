package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "HK47-other-blue", group = "Sreal")
public class HK47ob extends HoloLumi {
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
        leftHook.setPosition(hookUpLeft);//0 is down, 0.6 is up
        rightHook.setPosition(hookUpRight);//0 is down, 0.6 is up
        //
        moveWithSensor("left",22.0,true,.4,-1.0,0.1,true);
        //
        moveToPosition(38,.4,false);
        //
        leftHook.setPosition(hookDownLeft);
        rightHook.setPosition(hookDownRight);
        //
        sleep(500);
        //
        moveToPosition(-42,.4,false);
        //
        turnWithGyro(20,-.4);
        //
        leftHook.setPosition(hookUpLeft);
        rightHook.setPosition(hookUpRight);
        //
        turnToAngle(0,.05);
        //
        strafeToPosition(45,.3,false);
    }
    //
}
