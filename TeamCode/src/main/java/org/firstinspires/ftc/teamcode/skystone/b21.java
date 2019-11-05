package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "2-1B", group = "Stest")
public class b21 extends HoloLumi {
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
        initGyro();
        //
        waitForStartify();
        //
        while (opModeIsActive()){
            //
            telemetry.addData("front left distance", frontLeftD.getDistance(DistanceUnit.INCH));
            telemetry.addData("front right distance", frontRightD.getDistance(DistanceUnit.INCH));
            telemetry.addData("left skystone?", leftColor.red() < 50);
            telemetry.addData("right skystone?", rightColor.red() < 50);
            telemetry.addData("grabber", grabber.getPosition());
            telemetry.addData("upity", upity.getDistance(DistanceUnit.INCH));
            telemetry.addData("left hook", leftHook.getPosition());
            telemetry.addData("right hook", rightHook.getPosition());
            telemetry.addData("qbert", qbert.getState());
            telemetry.addData("george", george.getState());
            telemetry.addData("left touch", leftTouch.getState());
            telemetry.addData("right touch", rightTouch.getState());
            telemetry.update();
            //
        }
        //
    }
    //
}
