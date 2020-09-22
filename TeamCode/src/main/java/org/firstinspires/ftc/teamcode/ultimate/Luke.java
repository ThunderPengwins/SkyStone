package org.firstinspires.ftc.teamcode.ultimate;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Luke", group = "test")
public class Luke extends jeremy {
    //
    double powerFactor = 1.0;
    //
    float leftx;
    float lefty;
    float rightx;
    float otherlefty;
    float otherrighty;
    //
    double ayaw = 0;
    //
    double mT = 0;
    boolean ma = false;
    double mr = 0;
    //
    double origin = 0;
    //
    public void runOpMode() {
        //
        InitLite();
        //
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        //
        waitForStartify();
        //
        while (opModeIsActive()){
            //
            if (gamepad1.a){
                origin = getAngle();
                //orchosen = true;
            }
            //
            leftx = gamepad1.left_stick_x;
            lefty = -gamepad1.left_stick_y;
            rightx = gamepad1.right_stick_x;
            otherlefty = -gamepad2.left_stick_y;
            otherrighty = -gamepad2.right_stick_y;
            //
            if(leftx == 0 && lefty == 0 && rightx == 0){//no motion
                //
                still();
                ayaw = getAngle();
                //setLight("red");
                //
            }else{//moving
                //
                globalMoveTurn(leftx, lefty, rightx, powerFactor, 0.5, origin);
                //
                /*if (ma) {
                    globalMoveTurn(leftx, lefty, mr, powerFactor, 0.5, origin);
                } else {
                    globalMoveTurn(leftx, lefty, rightx, powerFactor, 0.5, origin);
                }*/
                //
            }
            //
        }
        //
    }
    //
}
