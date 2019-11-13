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
            extender.setPower(-gamepad2.right_stick_y);
            //
            //telemetry.addData("front left distance", frontLeftD.getDistance(DistanceUnit.INCH));
            //telemetry.addData("front right distance", frontRightD.getDistance(DistanceUnit.INCH));
            telemetry.addData("left skystone?", leftColor.red());
            telemetry.addData("right skystone?", rightColor.red());
            telemetry.addData("grabber", grabber.getPosition());
            telemetry.addData("upity", upity.getDistance(DistanceUnit.INCH));
            telemetry.addData("left hook", leftHook.getPosition());
            telemetry.addData("right hook", rightHook.getPosition());
            telemetry.addData("left distance", leftD.getDistance(DistanceUnit.INCH));
            telemetry.addData("right distance", rightD.getDistance(DistanceUnit.INCH));
            //telemetry.addData("back distance", backD.getDistance(DistanceUnit.INCH));
            telemetry.addData("qbert", qbert.getState());
            telemetry.addData("george", george.getState());
            telemetry.addData("in", inTouch.getState());
            telemetry.addData("out", outTouch.getState());
            telemetry.addData("left touch", leftTouch.getState());
            telemetry.addData("right touch", rightTouch.getState());
            telemetry.addData("Issue", "CAD");
            telemetry.update();
            //
        }
        //
    }
    //
}
