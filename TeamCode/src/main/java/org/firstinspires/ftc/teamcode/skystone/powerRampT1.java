package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="Ramping", group="testing")
public class powerRampT1 extends LinearOpMode {
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
    Double m1 = 0.1;
    Double floor = 0.05;
    //
    Double conversion = cpi * bias;
    //
    Boolean error = false;
    Double errordata = 0.0;
    Integer errormode = 0;
    Integer mode = 0;
    //
    public void runOpMode(){
        //
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);//If your robot goes backward, switch this from right to left
        //
        waitForStartify();
        //
        rampToPosition(30, .4);
        //
        if(error){
            telemetry.addData("error", errordata);
            telemetry.update();
            sleep(10000);
        }
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
    public void rampToPosition(double inches, double speed){
        //
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        double ms = speed / m1;
        //
        if(inches > 2 * ms){
            //
            int move = (int) (Math.round(inches * conversion));
            //
            int leftStart = left.getCurrentPosition();
            int rightStart = right.getCurrentPosition();
            int leftEnd = leftStart + move;
            int rightEnd = rightStart + move;
            //
            left.setTargetPosition(leftEnd);
            right.setTargetPosition(rightEnd);
            //
            while (left.isBusy() && right.isBusy()) {
                //
                double curSpeed = getRampOfDistance(leftStart, left.getCurrentPosition(), leftEnd, speed, ms);
                //
                if(curSpeed < -1 || curSpeed > 1){
                    error = true;
                    if(Math.abs(curSpeed) > Math.abs(errordata)) {
                        errordata = curSpeed;
                        errormode = mode;
                    }
                }
                //
                telemetry.addData("curSpeed", curSpeed);
                telemetry.update();
                //
                left.setPower(curSpeed);
                right.setPower(curSpeed);
            }
            //
            right.setPower(0);
            left.setPower(0);
        }
        //
    }
    //
    public double getRampOfDistance(int start, int location, int destination, double speed, double ms){
        //
        speed -= floor;
        int currentD =  location - start;
        int totalD = destination - start;
        double curSpeed;
        //
        if(currentD < (ms * conversion)){
            //
            telemetry.addData("starting","");
            curSpeed = m1 * currentD;
            mode = 1;
            //
        }else if(currentD < (totalD - (ms * conversion))){
            //
            curSpeed = speed;
            telemetry.addData("active", "");
            mode = 2;
            //
        }else{
            //
            curSpeed = -m1 * (currentD - totalD + ms) + speed;
            telemetry.addData("stopping", "");
            mode = 3;
            //
        }
        //
        curSpeed += floor;
        //
        return curSpeed;
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
