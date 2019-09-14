package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="teleRamping", group="testing")
public class powerRampT2 extends LinearOpMode {
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
    Double m1 = 10.0;
    Double m2 = 2.0;
    Integer tstart;
    Integer tend;
    Double ISpeed;
    //
    Boolean moving = false;
    Boolean turning = false;
    //
    Double conversion = cpi * bias;
    //
    public void runOpMode(){
        //
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);//If your robot goes backward, switch this from right to left
        //
        //left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //
        waitForStartify();
        //
        float lefty;
        float rightx;
        //hi
        while (opModeIsActive()){
            //
            lefty = -gamepad1.left_stick_y;
            rightx = gamepad1.right_stick_x;
            //
            if(!moving && !turning && lefty > 0){
                moving = true;
                tstart = left.getCurrentPosition();
            }else if(!moving && !turning && rightx > 0){
                turning = true;
                tstart = left.getCurrentPosition();
            }else if(moving && lefty == 0){
                tstart = left.getCurrentPosition();
                tend = tstart + (int)((ISpeed * conversion) / m2);
                moving = false;
            }else if(turning && rightx == 0){
                tstart = left.getCurrentPosition();
                tend = tstart + (int)((ISpeed * conversion) / m2);
                turning = false;
            }
            //
            if(moving){
                rampUpDrive(lefty);
                ISpeed = (double)lefty;
                telemetry.addData("ramping up drive", left.getPower());
            }else if(turning){
                rampUpTurn(rightx);
                ISpeed = (double)rightx;
                telemetry.addData("ramping up turn", left.getPower());
            }else if(!moving && left.getPower() > 0){
                rampDownDrive();
                telemetry.addData("ramping down drive", left.getPower());
                telemetry.addData("ISpeed", ISpeed);
            }else if(!turning && left.getPower() > 0){
                rampDownTurn();
                telemetry.addData("ramping down turn", left.getPower());
            }
            telemetry.update();
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
        if((inches - (speed / m2)) - (Math.sqrt(speed / m1)) > 0){
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
                double curSpeed = getRampOfDistance(leftStart, left.getCurrentPosition(), leftEnd, speed);
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
    public void rampUpDrive(double speed){
        //
        int distance = left.getCurrentPosition() - tstart;
        double curSpeed;
        //
        if(distance < conversion * Math.sqrt(speed / m1)){//sqrt(speed / m1) returns inches, convert to encoder counts
            curSpeed = m1 * Math.pow(distance, 2);//distance is already converted
            telemetry.addData("curSpeed", curSpeed);
            telemetry.addData("distance", distance);
        }else{
            curSpeed = speed;
        }
        //
        if(curSpeed == 0){
            curSpeed += .1;
        }
        //
        telemetry.addData("afterSpeed", curSpeed);
        //
        left.setPower(curSpeed);
        right.setPower(curSpeed);
        //
        telemetry.addData("power", left.getPower());
        //
    }
    //
    public void rampUpTurn(double speed){
        //
        int distance = left.getCurrentPosition() - tstart;
        double curSpeed;
        //
        if(distance < conversion * Math.sqrt(speed / m1)){
            curSpeed = m1 * Math.pow(distance, 2);
        }else{
            curSpeed = speed;
        }
        //
        if(curSpeed == 0){
            curSpeed += .1;
        }
        //
        left.setPower(curSpeed);
        right.setPower(-curSpeed);
        //
    }
    //
    public void rampDownDrive(){
        //
        int distance = Math.abs(left.getCurrentPosition() - tstart);
        double curSpeed;
        //
        if(distance < conversion * (ISpeed / m2) && left.getPower() > 0){//Ispeed/ m2 returns inches, convert to encoder counts
            curSpeed = -m2 * distance + ISpeed;//distance is already converted
        }else{
            curSpeed = 0;
        }
        //
        left.setPower(curSpeed);
        right.setPower(curSpeed);
        //
    }
    //
    public void rampDownTurn(){
        //
        int distance = left.getCurrentPosition() - tstart;
        double curSpeed;
        //
        if(distance < conversion * (ISpeed / m2)){
            curSpeed = -m2 * distance + ISpeed;
        }else{
            curSpeed = 0;
        }
        //
        left.setPower(curSpeed);
        right.setPower(curSpeed);
        //
    }
    //
    public double getRampOfDistance(int start, int location, int destination, double speed){
        //
        int distance = location - start;
        double curSpeed = 0.1;
        //
        if(distance < Math.sqrt(speed / m1)){
            //
            curSpeed = m1 * Math.pow(distance, 2);
            //
        }else if(Math.sqrt(speed / m1) < distance && distance < destination - (speed / m2)){
            //
            curSpeed = speed;
            //
        }else{
            //
            curSpeed = (-m2 * distance) + (destination * m2);
            //
        }
        //
        if(curSpeed == 0){
            curSpeed += 0.05;
        }
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
