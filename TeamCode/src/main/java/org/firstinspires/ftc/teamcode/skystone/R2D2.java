package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "R2-D2", group = "Stest")
@Disabled
public class R2D2 extends HoloLumi{
    //
    float leftx;
    float lefty;
    float rightx;
    //
    double powerFactor = 1.0;
    //
    double ayaw = 0;
    //
    Integer pMode = 0;//
    /*
    0 = default,           allowed: movement, initiate pick up  color: blue
    1 = pick up,           allowed: movement, intake            color: orange
    2 = processing block,  allowed: movement                    color: red
    3 = block processed,   allowed: movement, raising           color: green
    4 = raised,            allowed: movement, raising, engaging color: by level
    5 = engaging,          allowed: raising                     color: red
    6 = engaged,           allowed: raising, dropping           color: by level
    7 = released, lowering allowed: movement                    color: red
    */
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
            leftx = -gamepad1.left_stick_x;
            lefty = gamepad1.left_stick_y;
            rightx = -gamepad1.right_stick_x;
            //
            if(leftx == 0 && lefty == 0 && rightx == 0){//no motion
                //l'V
                still();
                ayaw = getAngle();
                //setLight("red");
                //
            }else /*if(rightx == 0)*/{//moving
                //
                if (rightx == 0){
                    ayaw = getAngle();
                }
                //move(leftx, lefty, powerFactor);
                moveTurn(leftx, lefty, rightx, powerFactor, 0.5, ayaw);
                //setLight("green");
                //
            }/*else{//turning
                //
                turnRobot(rightx, powerFactor, 0.5);
                //setLight("aqua");
                //
            }*/
            //
            if(gamepad1.a){
                gear();
            }
            if (gamepad1.b){
                dave();
            }
            //
            telemetry.addData("yaw: ", getAngle());
            telemetry.update();
        }
    }
    //
}
