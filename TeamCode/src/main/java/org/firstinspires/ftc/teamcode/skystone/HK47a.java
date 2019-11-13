package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-all", group = "Sreal")
public class HK47a extends HoloLumi {
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
        waitForStartify();
        //
        leftHook.setPosition(0.35);//0 is down, 0.6 is up
        //
        move(0, 1, .3);
        //
        while (!(leftD.getDistance(DistanceUnit.INCH) < 6) && opModeIsActive()){}
        //
        still();
        //
        sleep(500);
        //
        telemetry.addData("Skystone", leftColor.red() < 6);
        telemetry.update();
        //
        if (leftColor.red() < 6){
            //
        }
        //
    }
    //
}
