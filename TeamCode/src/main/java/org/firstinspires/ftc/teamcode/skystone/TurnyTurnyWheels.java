package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "The Turny Turn Turn", group = "Tele-Op")
public class TurnyTurnyWheels extends LinearOpMode {
    //
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor backRight;
    //
    double aWheelsPower;
    double bWheelsPower;
    double totalPower;
    double powerFactor = .5;
    //
    float leftx;
    float lefty;
    float righty;
    //
    /* left joystick angle in which it moves
    right joystick turn robot */
    //
    public void runOpMode(){
        //
        frontRight = hardwareMap.dcMotor.get("frontright");
        frontLeft = hardwareMap.dcMotor.get("frontleft");
        backRight = hardwareMap.dcMotor.get("backright");
        backLeft = hardwareMap.dcMotor.get("backleft");
        //
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //
        waitForStart();
        //
        while (opModeIsActive()){
            //
            leftx = gamepad1.left_stick_x;
            lefty = -gamepad1.left_stick_y;
            righty = -gamepad1.right_stick_y;
            //
            if(leftx == 0 && lefty == 0 && righty == 0){
                //
                still();
                //
            }else if(righty == 0){
                //
                moveForward();
                //
            }else{
                //
                telemetry.addData("You are", "turning");
                //
            }
            telemetry.update();
        }
        //
    }
    //
    public void moveForward(){
        //
        totalPower = Math.sqrt(Math.pow(lefty,  2) + Math.pow(leftx, 2));
        //
        double angle;
        //
        if(lefty > 0){
            angle = Math.acos(leftx / totalPower) * 180 / Math.PI;
        }else {
            angle = 180 + (Math.acos(leftx / totalPower) * 180 / Math.PI);
        }
        angle -= 45;
        //
        aWheelsPower = Math.cos(angle * Math.PI / 180);
        bWheelsPower = Math.sin(angle * Math.PI / 180);
        //
        frontRight.setPower(bWheelsPower * totalPower * powerFactor);
        backLeft.setPower(bWheelsPower * totalPower * powerFactor);
        frontLeft.setPower(aWheelsPower * totalPower * powerFactor);
        backRight.setPower(aWheelsPower * totalPower * powerFactor);
        //
        telemetry.addData("a power", aWheelsPower);
        telemetry.addData("b power", bWheelsPower);
        telemetry.addData("total power", totalPower);
    }
    //
    public void turnRobot(double rightStickInput){
        //I MADE A CHANGE SO LET ME PUSH AGAIN
    }
    //
    public void still(){
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
