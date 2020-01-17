package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
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
        //moveWithSensor("left",22.0,true,.4,-1.0,0.1,true);
        //
        strafeToPosition(-10.5,.3,false);
        //
        moveToPosition(34,.3,false);
        //
        leftHook.setPosition(hookDownLeft);
        rightHook.setPosition(hookDownRight);
        //
        sleep(5100);
        //
        moveToPosition(-38,.4,false);
        //
        turnWithGyro(20,-.4);
        //
        leftHook.setPosition(hookUpLeft);
        rightHook.setPosition(hookUpRight);
        //
        turnToAngle(0,.05);
        //
        strafeToPosition(35,.3,false);
    }
    //
}
