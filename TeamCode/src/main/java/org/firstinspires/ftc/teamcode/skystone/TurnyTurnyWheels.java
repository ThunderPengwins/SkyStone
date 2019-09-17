package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "The Turny Turn Turn", group = "Tele-Op")
public class TurnyTurnyWheels extends LinearOpMode {

    DcMotor topRight;
    DcMotor topLeft;
    DcMotor bottomLeft;
    DcMotor bottomRight;

    double aWheelsPower;
    double bWheelsPower;

   /* left joystick angle in which it moves
    right joystick turn robot */



    public void moveForward(){
       aWheelsPower = Math.acos(gamepad1.left_stick_y + 45);
       bWheelsPower = Math.asin(gamepad1.left_stick_x + 45);
       topRight.setPower(bWheelsPower);
       bottomLeft.setPower(bWheelsPower);
       topLeft.setPower(aWheelsPower);
       bottomRight.setPower(aWheelsPower);
    }

    public void turnRobot(double rightStickInput){
//I MADE A CHANGE SO LET ME PUSH AGAIN
    }

    public void runOpMode(){

        topRight = hardwareMap.dcMotor.get("?");
        topLeft = hardwareMap.dcMotor.get("?");
        bottomRight = hardwareMap.dcMotor.get("?");
        bottomLeft = hardwareMap.dcMotor.get("?");

        waitForStart();

        while (opModeIsActive()){
            moveForward();
        }

    }

    }
