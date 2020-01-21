package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "mouse", group = "Stest")
@Disabled
public class Mouse extends LinearOpMode {
    //right for in and out
    //
    Servo grabber;
    DcMotor lifter;
    DcMotor extender;
    //
    Float hold = 0F;
    Boolean holding = false;
    Double pos = 0.0;
    Boolean posing = false;
    //
    public void runOpMode(){
        //
        grabber = hardwareMap.servo.get("grabber");
        lifter = hardwareMap.dcMotor.get("lifter");
        extender = hardwareMap.dcMotor.get("extender");
        //
        waitForStart();
        //
        while (opModeIsActive()){
            //
            if (gamepad2.dpad_left && !posing){
                pos -= .1;
                posing = true;
            }else if(gamepad2.dpad_right && ! posing){
                pos += .1;
                posing = true;
            }else if (posing && (!gamepad2.dpad_right && !gamepad2.dpad_left)){
                posing = false;
            }
            //
            if (gamepad2.left_bumper && holding){
                lifter.setPower(hold);
                telemetry.addData("lifter", hold);
            }else if (gamepad2.left_bumper && !holding){
                hold = -gamepad2.left_stick_y;
                telemetry.addData("lifter", hold);
            }else{
                lifter.setPower(-gamepad2.left_stick_y);
                telemetry.addData("lifter", -gamepad2.left_stick_y);
            }
            //
            extender.setPower(-gamepad2.right_stick_y);
            telemetry.addData("extender", -gamepad2.right_stick_y);
            telemetry.addData("position", pos);
            telemetry.update();
            //
        }
        //
    }
    //
}
