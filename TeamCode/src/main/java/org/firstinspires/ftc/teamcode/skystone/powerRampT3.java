package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="TeleRamping2", group="testing")
public class powerRampT3 extends LinearOpMode {
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
    Integer start;
    Integer end;
    Integer momode = 0;
    Float leftj;
    Float rightj;
    Double speedt = 0.0;
    Boolean pos = true;
    //
    public void runOpMode(){
        //
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);//If your robot goes backward, switch this from right to left
        //
        waitForStartify();
        //
        while (opModeIsActive()){
            //
            leftj = -gamepad1.left_stick_y;
            rightj = -gamepad1.right_stick_y;
            //
            speedt = (double)leftj;
            //
            if(momode == 0 && (leftj != 0 || rightj != 0)){
                //start ramp up (drive or turn)
                //
                start = left.getCurrentPosition();
                //
                if(leftj < 0){//left wheel moving backward
                    end = start - (int)(conversion * (leftj / m1));
                    pos = false;
                }else{//left wheel moving forward
                    end = start + (int)(conversion * (leftj / m1));
                    pos = true;
                }
                //
                if (leftj / rightj < 0){//turning
                    momode = 1;
                }else{//driving
                    momode = 2;
                }
                //
            }else if(momode == 1){//driving
                rampUp();
            }else if(momode == 2){//turning
                //set active power
            }else if (momode > 2){
                //get ramp down power
            }
            //
            if((momode == 1 || momode == 2) && (leftj == 0 && rightj == 0)){
                //set ramp down
                momode += 2;
            }
            //
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
            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //
            while (left.isBusy() && right.isBusy()) {
                //
                double curSpeed = getRampOfDistance(leftStart, left.getCurrentPosition(), leftEnd, speed, ms);
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
    public double rampUp(){
        //
        double speed = 0;
        //
        if(pos){
            if(left.getCurrentPosition() > end){
                if(momode == 1) {//drive
                    left.setPower(speedt);
                    right.setPower(speedt);
                }else{
                    left.setPower(speedt);
                    right.setPower(-speedt);
                }
            }else{
                double curSpeed = getRampSpeed();
                if(momode == 1) {//drive
                    left.setPower(curSpeed);
                    right.setPower(curSpeed);
                }else{
                    left.setPower(curSpeed);
                    right.setPower(curSpeed);
                }
            }
        }
        //
        return speed;
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
            curSpeed = m1 * (currentD / conversion);
            //
        }else if(currentD < (totalD - (ms * conversion))){
            //
            curSpeed = speed;
            telemetry.addData("active", "");
            //
        }else{
            //
            curSpeed = -m1 * (((currentD - totalD) / conversion) + ms) + speed;
            telemetry.addData("stopping", "");
            //
        }
        //
        curSpeed += floor;
        //
        return curSpeed;
    }
    //
    public double getRampSpeed(){
        //
        Double speed = 0.0;
        //
        if(momode < 3){//starting
            //
            //
        }else{//ending
            //
        }
        //
        return speed;
        //
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
