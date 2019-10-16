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
    double ayaw = 0;
    //
    double origin = 0;
    //
    @Override
    public void runOpMode() {
        //
        motorHardware();
        //
        setMotorReversals();
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
            leftx = -gamepad1.left_stick_x;
            lefty = gamepad1.left_stick_y;
            rightx = -gamepad1.right_stick_x;
            //
            if(leftx == 0 && lefty == 0 && rightx == 0){//no motion
                //l'V
                still();
                ayaw = fixAngle(getAngle());
                //setLight("red");
                //
            }else /*if(rightx == 0)*/{//moving
                //
                if (rightx == 0){
                    ayaw = getAngle();
                }
                //move(leftx, lefty, powerFactor);
                globalMoveTurn(leftx, lefty, rightx, powerFactor, 0.5, ayaw);
                //setLight("green");
                //
            }/*else{//turning
                //
                turnRobot(rightx, powerFactor, 0.5);
                //setLight("aqua");
                //
            }*/
            //
            telemetry.addData("relative yaw", getAngle());
            telemetry.addData("absolute yaw", fixAngle(getAngle()));
            telemetry.update();
        }
    }
    //
}
