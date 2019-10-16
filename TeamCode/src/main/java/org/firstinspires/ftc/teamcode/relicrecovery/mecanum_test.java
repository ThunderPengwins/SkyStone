package org.firstinspires.ftc.teamcode.relicrecovery;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.relicrecoveryv2.HardwareSensorMap;

/**
 * Created by thund on 9/30/2017.
 */

@TeleOp(name="mecanum_test",group="BBBot" )
public class mecanum_test extends LinearOpMode {
    HardwareSensorMap robot   = new HardwareSensorMap();   // Use a Pushbot's hardware
    DcMotor leftRear;
    DcMotor rightRear;
    DcMotor leftFront;
    DcMotor rightFront;
    double drive; //turn power
    //
    JeffThePengwin jeffThePengwin;
    //
    double turn; //turn direction
    double leftX; //left x: joystick
    boolean turningRight;
    boolean notTurning;
    boolean movingVertical;
    boolean strafingRight;
    //
    @Override
    public void runOpMode() throws InterruptedException {
        //
        leftRear = hardwareMap.dcMotor.get("backleft");
        rightRear = hardwareMap.dcMotor.get("backright");
        leftFront = hardwareMap.dcMotor.get("frontleft");
        rightFront = hardwareMap.dcMotor.get("frontright");
        //
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        //
        waitForStart();
        //
        while (opModeIsActive()){
            //
            drive = -gamepad1.left_stick_y * 0.5; //left joystick moving up and down
            turn = gamepad1.right_stick_x * 0.5; //right joystick left and right
            leftX = -gamepad1.left_stick_x * 0.5; //left joystick moving right and left
            //
            figureOutMovementOfRobot();
            //
            moveTheRobot(turningRight, notTurning, movingVertical, strafingRight);
            //
        }

    }

    private void figureOutMovementOfRobot() {
        turningRight = turn != 0; //TODO This is not working right, it should be > but it is mixing up and left and right
        notTurning = turn == 0;
        movingVertical = Math.abs(drive) >= Math.abs(leftX);
        strafingRight = leftX < 0;
    }
    //
    private void moveTheRobot(boolean turningRight, boolean notTurning, boolean movingVertical, boolean strafingRight) {
        if (notTurning) {
            //no movement in right joystick
            //start of driving section
            if (movingVertical) { //forward/back or left/right?
                driveForward();
            } else {
                strafeRight();
            }
        } else {
            //turn left
            turnRight();
        }
    }
    //
    public void driveForward() {
        bestowThePowerToAllMotors(drive);
    }
    //
    public void strafeRight(){
        leftRear.setPower(leftX);
        leftFront.setPower(-leftX);
        rightRear.setPower(-leftX);
        rightFront.setPower(leftX);
    }
    //
    public void bestowThePowerToAllMotors(double speed){
        leftRear.setPower(speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        rightFront.setPower(speed);
    }
    //
    public void turnRight(){
        leftRear.setPower(turn);
        leftFront.setPower(turn);
        rightRear.setPower(-turn);
        rightFront.setPower(-turn);
    }
}
