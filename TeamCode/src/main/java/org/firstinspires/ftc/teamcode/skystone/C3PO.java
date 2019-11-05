package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "C3PO", group = "Stest")
public class C3PO extends HoloLumi{
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
    //
    Servo grabber;
    DcMotor lifter;
    DcMotor extender;
    //
    Float hold = 0F;
    Boolean holding = false;
    Double pos = 0.0;
    Boolean posing = false;
    Boolean test = false;
    //
    @Override
    public void runOpMode() {
        //
        motorHardware();
        grabber = hardwareMap.servo.get("grabber");
        lifter = hardwareMap.dcMotor.get("lifter");
        extender = hardwareMap.dcMotor.get("extender");
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
            if (gamepad1.x){
                dave();
            }
            //
            leftx = gamepad1.left_stick_x;
            lefty = -gamepad1.left_stick_y;
            rightx = gamepad1.right_stick_x;
            //
            direction = fixAngle(getAngle() - origin);
            //
            //<editor-fold desc="Movement">
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
            //</editor-fold>
            //
            //<editor-fold desc="mouse">
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
            //</editor-fold>
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
            telemetry.addData("Planetary?", planetary);
            telemetry.addData("conforming?", ma);
            telemetry.addData("height", lifter.getCurrentPosition());
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
}
