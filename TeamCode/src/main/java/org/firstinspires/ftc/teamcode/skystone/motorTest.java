package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "motor test", group = "testing")
public class motorTest extends LinearOpMode{
    //
    DcMotor test;
    //
    public void runOpMode(){
        //
        test = hardwareMap.dcMotor.get("left");
        //
        waitForStart();
        //
        while (opModeIsActive()){
            //
            test.setPower(gamepad1.left_stick_y);
            telemetry.addData("power", test.getPower());
            telemetry.update();
            //
        }
        //
        test.setPower(0);
        //
    }
    //
}
