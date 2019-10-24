package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "C3PO", group = "Stest")
public class C3PO extends HoloLumi{
    //
    float leftx;
    float lefty;
    float rightx;
    //
    double powerFactor = 1.0;
    //
    double direction = 0;
    //
    double mT = 0;
    boolean ma = false;
    double mr = 0;
    //
    double origin = 0;
    //
    @Override
    public void runOpMode() {
        //
        motorHardware();
        //
        secondaryMotorReversals();
        //
        motorsWithEncoders();
        //
        initGyro();
        //
        waitForStartify();
        //
        while (opModeIsActive()){
            //
            if (gamepad1.a){
                origin = getAngle();
            }
            //
            if (gamepad1.y){
                dave();
            }
            //
            leftx = gamepad1.left_stick_x;
            lefty = -gamepad1.left_stick_y;
            rightx = gamepad1.right_stick_x;
            //
            direction = fixAngle(getAngle() - origin);
            //
            if(leftx == 0 && lefty == 0 && rightx == 0){//no motion
                //
                still();
                //setLight("red");
                //
            }else{//moving
                //
                if (ma){
                    globalMoveTurn(leftx, lefty, mr, powerFactor, 0.5, origin);
                }else {
                    globalMoveTurn(leftx, lefty, rightx, powerFactor, 0.5, origin);
                }
                //
            }
            //
            if ((rightx != 0 && ma) || inBounds(mT, 5)){
                ma = false;
            }
            if (gamepad1.left_trigger > 0){
                ma = true;
                mT = conformLeft(origin);
                mr = -0.5;
            }else if (gamepad1.right_trigger > 0){
                ma = true;
                mT = conformRight(origin);
                mr = 0.5;
            }
            if (gamepad1.dpad_left){
                frontLeft.setPower(.5);
            }else if(gamepad1.dpad_right){
                frontRight.setPower(.5);
            }else if (gamepad1.dpad_down){
                frontLeft.setPower(0);
                frontRight.setPower(0);
            }
            //
            telemetry.update();
            telemetry.addData("local target angle", loctarang);
            telemetry.addData("global target angle", glotarang);
            telemetry.addData("relative yaw", getAngle());
            telemetry.addData("absolute yaw", fixAngle(getAngle() - origin));
            telemetry.addData("origin", origin);
            telemetry.addData("conforming?", ma);
            telemetry.addData("leftx", leftx);
            telemetry.addData("lefty", lefty);
            telemetry.addData("rightx", rightx);
        }
    }
    //
}
