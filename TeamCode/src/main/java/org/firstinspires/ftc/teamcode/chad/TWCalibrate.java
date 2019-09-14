package org.firstinspires.ftc.teamcode.chad;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="TCalibrate", group="chad")
@Disabled
public class TWCalibrate extends LinearOpMode {
    //
    DcMotor left;
    DcMotor right;
    //Calculate encoder conversion
    Integer cpr = 28; //counts per rotation
    Integer gearratio = 20;
    Double diameter = 4.125;
    Double cpi = (cpr * gearratio)/(Math.PI * diameter); //counts per inch -> counts per rotation / circumference
    Double bias = 1.0;//adjust until your robot goes 20 inches
    //
    Double conversion = cpi * bias;
    //
    public void runOpMode(){
        //
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);//If your robot goes backward, switch this from right to left
        //
        moveToPosition(20, .2);//Don't change this line, unless you want to calibrate with different speeds
        //
    }
    //
    /*
    This function's purpose is simply to drive forward or backward.
    To drive backward, simply make the inches input negative.
     */
    public void moveToPosition(double inches, double speed){
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        if (inches < 5) {
            int move = (int) (Math.round(inches * conversion));
            //
            left.setTargetPosition(left.getCurrentPosition() + move);
            right.setTargetPosition(right.getCurrentPosition() + move);
            //
            left.setPower(speed);
            right.setPower(speed);
            //
            while (left.isBusy() && right.isBusy()) {}
            right.setPower(0);
            left.setPower(0);
        }else{
            int move1 = (int)(Math.round((inches - 5) * conversion));
            int movel2 = left.getCurrentPosition() + (int)(Math.round(inches * conversion));
            int mover2 = right.getCurrentPosition() + (int)(Math.round(inches * conversion));
            //
            left.setTargetPosition(left.getCurrentPosition() + move1);
            right.setTargetPosition(right.getCurrentPosition() + move1);
            //
            left.setPower(speed);
            right.setPower(speed);
            //
            while (left.isBusy() && right.isBusy()) {}
            //
            left.setTargetPosition(movel2);
            right.setTargetPosition(mover2);
            //
            left.setPower(.1);
            right.setPower(.1);
            //
            while (left.isBusy() && right.isBusy()) {}
            right.setPower(0);
            left.setPower(0);
        }
        return;
    }
    //
    /*
    A tradition within the Thunder Pengwins code, we always start programs with waitForStartify,
    our way of adding personality to our programs.
     */
    public void waitForStartify(){
        waitForStart();
    }
    //
}
