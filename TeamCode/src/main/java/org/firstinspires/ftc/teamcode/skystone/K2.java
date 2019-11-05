package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "K2", group = "Sreal")
public class K2 extends HoloLumi{
    //
    boolean planetary = true;
    boolean plana = false;
    //
    float leftx;
    float lefty;
    float rightx;
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
    Boolean orchosen = false;
    //
    Servo grabber;
    DcMotor lifter;
    DcMotor extender;
    DigitalChannel qbert;
    DigitalChannel george;
    //
    Float hold = 0F;
    Boolean holding = false;
    Double pos = 0.0;
    Boolean posing = false;
    Boolean test = false;
    //
    Integer pMode = 0;//
    /*
    0 = default,           allowed: movement, initiate pick up  color: blue
    1 = pick up,           allowed: movement, intake            color: orange
    2 = processing block,  allowed: nothing!                    color: red
    3 = block processed,   allowed: movement, raising           color: green
    4 = raised,            allowed: movement, raising, engaging color: by level
    5 = engaging,          allowed: raising                     color: red
    6 = engaged,           allowed: raising, dropping           color: by level
    7 = released, lowering allowed: movement                    color: red
    */
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
        grabber = hardwareMap.servo.get("grabber");
        lifter = hardwareMap.dcMotor.get("lifter");
        extender = hardwareMap.dcMotor.get("extender");
        qbert = hardwareMap.digitalChannel.get("qbert");
        george = hardwareMap.digitalChannel.get("george");
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
            if (gamepad1.a && !orchosen){
                origin = getAngle();
                orchosen = true;
            }
            //
            if (gamepad1.x){
                dave();
            }
            //
            if (planetary) {
                leftx = gamepad1.left_stick_x;
                lefty = -gamepad1.left_stick_y;
                rightx = gamepad1.right_stick_x;
            }else if (!planetary){
                leftx = gamepad2.left_stick_x;
                lefty = -gamepad2.left_stick_y;
                rightx = gamepad2.right_stick_x;
            }
            //
            direction = fixAngle(getAngle() - origin);
            //
            if(pMode == 0){
                //
                planetary = true;
                //
                movement();
                //
                if (gamepad1.b){
                    pMode = 1;
                }
            }else if(pMode == 1){
                //
                planetary = false;
                lifter.setPower(.1);
                //
                movement();
                //
                if (qbert.getState()){
                    pMode = 2;
                }
            }else if (pMode == 2){
                //
                lifter.setPower(-.1);
                //
                if (george.getState()){
                    pMode = 3;
                    lifter.setPower(0);
                }
            }else if (pMode == 3){
                //
                planetary = true;
                //
                movement();
                //
                mouse();
            }
            //
            mouse();
            //
            //<editor-fold desc="Motor Testing">
            /*if (gamepad1.dpad_up){
                frontLeft.setPower(1);
                test = true;
            }else if(gamepad1.dpad_right){
                frontRight.setPower(1);
                test = true;
            }else if(gamepad1.dpad_down){
                backRight.setPower(1);
                test = true;
            }else if (gamepad1.dpad_left){
                backLeft.setPower(1);
                test = true;
            }else {
                test = false;
            }*/
            //</editor-fold>
            //
            if (gamepad1.y && !plana){
                planetary = !planetary;
                plana = true;
            }else if(!gamepad1.y && plana){
                plana = false;
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
            //
            //<editor-fold desc="telemetry">
            telemetry.update();
            telemetry.addData("mode", pMode);
            telemetry.addData("Planetary?", planetary);
            telemetry.addData("conforming?", ma);
            //telemetry.addData("local target angle", loctarang);
            //telemetry.addData("global target angle", glotarang);
            telemetry.addData("relative yaw", getAngle());
            telemetry.addData("absolute yaw", fixAngle(getAngle() - origin));
            telemetry.addData("origin", origin);
            telemetry.addData("leftx", leftx);
            telemetry.addData("lefty", lefty);
            telemetry.addData("rightx", rightx);
            telemetry.addData("extender", -gamepad2.right_stick_y);
            telemetry.addData("position", pos);
            //</editor-fold>
        }
    }
    //
    public void movement(){
        if(leftx == 0 && lefty == 0 && rightx == 0 && !test){//no motion
            //
            still();
            ayaw = getAngle();
            //setLight("red");
            //
        }else{//moving
            //
            if (planetary) {
                if (ma && !test) {
                    globalMoveTurn(leftx, lefty, mr, powerFactor, 0.5, origin);
                } else if (!test) {
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
    public void mouse(){
        if (gamepad2.dpad_left && !posing){
            pos -= .1;
            posing = true;
        }else if(gamepad2.dpad_right && ! posing){
            pos += .1;
            posing = true;
        }else if (posing && (!gamepad2.dpad_right && !gamepad2.dpad_left)){
            posing = false;
        }
        grabber.setPosition(pos);
        //
        if (gamepad2.left_bumper && holding){
            lifter.setPower(hold);
            telemetry.addData("lifter", hold);
        }else if (gamepad2.left_bumper && !holding){
            hold = -gamepad2.left_stick_y;
            telemetry.addData("lifter", hold);
        }else{
            lifter.setPower(-gamepad2.left_stick_y);
            telemetry.addData("lifter", -gamepad2.left_stick_y);
        }
        extender.setPower(-gamepad2.right_stick_y);
    }
    //
}
