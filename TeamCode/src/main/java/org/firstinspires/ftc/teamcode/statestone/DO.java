package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "D-O", group = "Sreal")
public class DO extends Myriad{
    //
    boolean planetary = true;
    boolean plana = false;
    //
    float leftx;
    float lefty;
    float rightx;
    float otherlefty;
    float otherrighty;
    //
    double powerFactor = 1.0;
    //
    double direction = 0;
    //
    double ayaw = 0;
    //
    double mT = 0;
    boolean ma = false;
    double mr = 0;
    //
    double origin = 0;
    //
    /*
    movement = still/global move turn
    mouse = extender and lifter
     */
    //
    @Override
    public void runOpMode() {
        //
        motorHardware();
        //
        secondHardwares();
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
                //orchosen = true;
            }
            //
            /*if (planetary) {*/
                leftx = gamepad1.left_stick_x;
                lefty = -gamepad1.left_stick_y;
                rightx = gamepad1.right_stick_x;
                otherlefty = -gamepad2.left_stick_y;
                otherrighty = -gamepad2.right_stick_y;
            /*}else if (!planetary){
                leftx = gamepad2.left_stick_x;
                lefty = -gamepad2.left_stick_y;
                rightx = gamepad2.right_stick_x;
                otherlefty = -gamepad1.left_stick_y;
                otherrighty = -gamepad1.right_stick_y;
            }*/
            //
            direction = fixAngle(getAngle() - origin);
            //
            scooper.setPower(-gamepad2.left_stick_y * 0.25);
            //
            movement();
            //</editor-fold>
        }
    }
    //
    public void movement(){
        //
        if(leftx == 0 && lefty == 0 && rightx == 0){//no motion
            //
            still();
            ayaw = getAngle();
            //setLight("red");
            //
        }else{//moving
            //
            if (planetary) {
                if(ma){
                    globalMoveTurn(leftx, lefty, mr, powerFactor, 0.5, origin);
                }else{
                    globalMoveTurn(leftx, lefty, rightx, powerFactor, 0.5, origin);
                }
            }else{
                if (rightx == 0){
                    ayaw = getAngle();
                }
                //
                moveTurn(leftx, lefty, rightx, powerFactor, 0.5, ayaw);
            }
            //
        }
    }
    //
}