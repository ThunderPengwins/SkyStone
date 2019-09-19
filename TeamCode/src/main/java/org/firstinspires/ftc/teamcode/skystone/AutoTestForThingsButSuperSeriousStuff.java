package org.firstinspires.ftc.teamcode.skystone;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name = "Robo Eyes", group = "Tele-Op")
public class AutoTestForThingsButSuperSeriousStuff extends LinearOpMode {

    ColorSensor roboEyes;
    RevBlinkinLedDriver coolLights;

    @Override
    public void runOpMode() {

        roboEyes = hardwareMap.get(ColorSensor.class, "anything");
        coolLights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
        boolean unicorns = true;
        waitForStart();

        while (opModeIsActive()) {

            while (unicorns = true){

            if(roboEyes.red()<50){
                coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);
                telemetry.addData("This is the Skystone", unicorns);
                telemetry.update();
            }
            else{
                coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
                telemetry.addData("This is not the Skystone", unicorns);
                telemetry.update();
            }}

            telemetry.addData("Alpha", roboEyes.alpha());
            telemetry.addData("Red  ", roboEyes.red());
            telemetry.addData("Green", roboEyes.green());
            telemetry.addData("Blue ", roboEyes.blue());
            telemetry.update();


        }
    }
}
