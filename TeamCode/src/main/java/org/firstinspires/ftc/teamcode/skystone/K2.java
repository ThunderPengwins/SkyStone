package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "K2", group = "Sreal")
public class K2 extends HoloLumi{
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
    Boolean orchosen = false;
    //
    Float hold = 0F;
    Boolean holding = false;
    Double pos = 0.0;
    Boolean posing = false;
    Boolean test = false;
    //
    Integer pMode = 0;//
    Integer task = 0;
    Long taskTimer = 0L;
    Long currentTime = 0L;
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
        //
        firstHarwares();
        //
        secondaryMotorReversals();
        //
        motorsWithEncoders();
        //
        initGyro();
        //
        waitForStartify();
        //
        leftHook.setPosition(hookUpLeft);
        rightHook.setPosition(hookUpRight);
        //
        while (opModeIsActive()){
            //
            if (gamepad1.a){
                origin = getAngle();
                //orchosen = true;
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
                otherlefty = -gamepad2.left_stick_y;
                otherrighty = -gamepad2.right_stick_y;
            }else if (!planetary){
                leftx = gamepad2.left_stick_x;
                lefty = -gamepad2.left_stick_y;
                rightx = gamepad2.right_stick_x;
                otherlefty = -gamepad1.left_stick_y;
                otherrighty = -gamepad1.right_stick_y;
            }
            //
            direction = fixAngle(getAngle() - origin);
            //
            if (gamepad1.left_bumper && gamepad1.right_bumper){
                pMode = 0;
                lifter.setPower(0);
                extender.setPower(0);
                still();
                planetary = true;
                leftHook.setPosition(hookUpLeft);
                rightHook.setPosition(hookUpRight);
            }
            //
            currentTime = java.lang.System.currentTimeMillis();
            if(pMode == 0){//default
                //
                movement();
                mouse();
                //
                if (gamepad1.b){
                    pMode = 1;
                    planetary = false;
                    grabber.setPosition(grabOpen);
                }
                //
            }else if(pMode == 1){//pick up
                //
                if (upity.getDistance(DistanceUnit.INCH) < 3.3){
                    lifter.setPower(0.6);
                }else if (upity.getDistance(DistanceUnit.INCH) > 4){
                    lifter.setPower(-0.6);
                    extender.setPower(-.3);
                }else {
                    lifter.setPower(0);
                    extender.setPower(0);
                }
                //
                movement();
                mouse();
                //
                leftHook.setPosition(hookUpLeft);
                rightHook.setPosition(hookUpRight);
                //
                if (qbert.getState()){
                    pMode = 2;
                    task = 1;
                    still();
                    lifter.setPower(-.3);
                }
            }else if (pMode == 2) {//process block
                //
                leftHook.setPosition(hookUpLeft);
                rightHook.setPosition(hookUpRight);
                //
                if (task == 0) {//lifter buffer, disabled
                    //
                    if (george.getState()) {
                        task++;
                        taskTimer = currentTime;
                        lifter.setPower(-.3);
                    }
                    //
                }else if (task == 1){//lifter down
                    //
                    if (george.getState()){//(currentTime > taskTimer + 1000){
                        lifter.setPower(0);
                        task++;
                        taskTimer = currentTime;
                    }
                    //
                }else if (task == 2){//grab block
                    //
                    grabber.setPosition(grabClosed);//down
                    //
                    if (currentTime > taskTimer + 500){
                        task++;
                        lifter.setPower(0.6);
                    }
                }else if (task == 3){//lifter up
                    //
                    if (upity.getDistance(DistanceUnit.INCH) > 2.2){
                        lifter.setPower(0);
                        planetary = true;
                        pMode = 3;
                    }
                }
                //
            }else if (pMode == 3){//transport mode
                //
                planetary = true;
                movement();
                mouse();
                //
                if (lifter.getPower() != 0){
                    pMode = 4;
                }
                //
            }else if (pMode == 4){//lifting mode
                //
                planetary = true;
                movement();
                mouse();
                //
                if (!leftTouch.getState() || !rightTouch.getState()){//they're backwards
                    pMode = 5;
                }
                //
            }else if (pMode == 5){//engage to foundation
                //
                if (!leftTouch.getState()){
                    frontRight.setPower(0.2);
                    backRight.setPower(0.2);
                }else{
                    frontLeft.setPower(0.2);
                    backLeft.setPower(0.2);
                }
                //
                if (!leftTouch.getState() && !rightTouch.getState()){
                    still();
                    pMode = 6;
                }
                //
            }else if (pMode == 6){//stacking blocks ->release, default
                //
                mouse();
                //
                if (gamepad2.left_bumper){
                    move(1, 0, .3);
                }else if (gamepad2.right_bumper){
                    move(-1, 0, .3);
                }else {
                    if (!leftTouch.getState() && !rightTouch.getState()) {
                        still();
                    }else{
                        move(0,1,0.2);
                    }
                }
                //
                if (gamepad1.b){
                    still();
                    grabber.setPosition(grabOpen);
                    pMode = 7;
                }
                //
            }else if (pMode == 7){
                //
                movement();
                //
                if (upity.getDistance(DistanceUnit.INCH) > 2.5){
                    lifter.setPower(-.3);
                    extender.setPower(-.3);
                }else {
                    lifter.setPower(0);
                    extender.setPower(0);
                    pMode = 0;
                }
                //
            }
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
                mr = -1;
            }else if (gamepad1.right_trigger > 0){
                ma = true;
                mT = conformRight(origin);
                mr = 1;
            }
            //
            //<editor-fold desc="telemetry">
            telemetry.update();
            telemetry.addData("mode", pMode);
            telemetry.addData("task", task);
            telemetry.addData("Planetary?", planetary);
            telemetry.addData("conforming?", ma);
            //telemetry.addData("extender encoder", extender.getCurrentPosition());
            telemetry.addData("hieght of gantry", upity.getDistance(DistanceUnit.INCH));
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
        if (gamepad2.dpad_up){
            grabber.setPosition(grabOpen);
        }else if(gamepad2.dpad_down){
            grabber.setPosition(grabClosed);
        }
        if (gamepad2.dpad_right){
            leftHook.setPosition(0);
            rightHook.setPosition(1);
        }else if (gamepad2.dpad_left){
            leftHook.setPosition(0.6);//0.3 is middle
            rightHook.setPosition(0.4);
        }
        //rightHook.setPosition(pos);//disabled
        //
        if (gamepad2.left_bumper && holding){
            lifter.setPower(hold);
            telemetry.addData("lifter", hold);
        }else if (gamepad2.left_bumper && !holding){
            hold = otherlefty;
            telemetry.addData("lifter", hold);
        }else{
            if (pMode != 1) {
                lifter.setPower(otherlefty);
            }
            telemetry.addData("lifter", otherlefty);
        }
        extender.setPower(otherrighty);
    }
    //
}
