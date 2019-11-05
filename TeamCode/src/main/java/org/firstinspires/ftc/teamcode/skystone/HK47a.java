package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "HK47-all", group = "Sreal")
public class HK47a extends HoloLumi {
    //
    Servo grabber;
    DcMotor lifter;
    DcMotor extender;
    DigitalChannel qbert;//cube in
    DigitalChannel george;//gantry down
    DistanceSensor upity;
    //
    Servo leftHook;
    Servo rightHook;
    DigitalChannel leftTouch;
    DigitalChannel rightTouch;
    ColorSensor leftColor;
    ColorSensor rightColor;
    //
    DistanceSensor frontLeftD;
    DistanceSensor frontRightD;
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
