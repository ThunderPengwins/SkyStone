package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "D-O.S", group = "Sreal")
@Disabled
public class DOS extends Myriad{
    //
    boolean planetary = false;
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
    double scoopPower = 0.5;
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
            leftx = gamepad1.left_stick_x;
            lefty = -gamepad1.left_stick_y;
            rightx = gamepad1.right_stick_x;
            otherlefty = -gamepad2.left_stick_y;
            otherrighty = -gamepad2.right_stick_y;
            //
            if (gamepad1.right_bumper){
                scoopPower = 0.7;
            }else if (gamepad1.left_bumper){
                scoopPower = 0.5;
            }
            //
            direction = fixAngle(getAngle() - origin);
            //
            if (gamepad1.right_trigger > 0){
                scooper.setPower(gamepad1.right_trigger * scoopPower);
            }else{
                scooper.setPower(-gamepad1.left_trigger * scoopPower);
            }
            //scooper.setPower(-gamepad2.left_stick_y * 0.5);
            //
            movement();
            //
            telemetry.addData("scoopDown",scoopDown.getState());
            telemetry.addData("scoopUp",scoopUp.getState());
            telemetry.update();
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
