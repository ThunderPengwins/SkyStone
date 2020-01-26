package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "K2", group = "Sreal")
public class K2 extends Myriad{
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
    boolean powerChange = false;
    //
    double direction = 0;
    //
    double ayaw = 0;
    //
    double mT = 0;
    boolean ma = false;
    double mr = 0;
    //
    boolean ignore = false;
    //
    double origin = 0;
    Boolean orchosen = false;
    //
    Boolean posing = false;
    Boolean test = false;
    //
    boolean flipin = true;
    boolean flipinpress = false;
    //
    int extension = 1320;
    Boolean extending = false;
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
        setMotorReversals();
        //
        motorsWithEncoders();
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //
        initGyro();
        //
        waitForStartify();
        //
        leftHook.setPosition(hookUpLeft);
        rightHook.setPosition(hookUpRight);
        //
        flippers.setPosition(0.0);
        //
        while (opModeIsActive()){
            //
            if (gamepad1.a){
                origin = getAngle();
                //orchosen = true;
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
                lifter.setPower(0.2);
                extender.setPower(0);
                still();
                planetary = true;
                leftHook.setPosition(hookUpLeft);
                rightHook.setPosition(hookUpRight);
            }
            //
            currentTime = System.currentTimeMillis();
            if(pMode == 0){//default
                //
                movement();
                mouse();
                //
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                //
                if (gamepad1.b){
                    pMode = 1;
                    planetary = false;
                    grabber.setPosition(grabOpen);
                }
                //
            }else if(pMode == 1){//pick up
                //
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
                //
                if (upity.getDistance(DistanceUnit.INCH) < 3.3){
                    lifter.setPower(0.8);
                }else if (upity.getDistance(DistanceUnit.INCH) > 4){
                    lifter.setPower(-0.4);
                    //extender.setPower(-.3);
                }else {
                    lifter.setPower(0.2);
                    //extender.setPower(0);
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
                    grabber.setPosition(grabOpen);
                    lifter.setPower(-.1);
                }
            }else if (pMode == 2) {//process block
                //
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
                //
                leftHook.setPosition(hookUpLeft);
                rightHook.setPosition(hookUpRight);
                //
                if (task == 0) {//lifter buffer, disabled
                    //
                    if (george.getState()) {
                        task++;
                        taskTimer = currentTime;
                        lifter.setPower(-.4);
                    }
                    //
                }else if (task == 1){//lifter down
                    //
                    if (george.getState()){//(currentTime > taskTimer + 1000){
                        lifter.setPower(0.2);
                        task = 2;
                        taskTimer = currentTime;
                    }
                    //
                }else if (task == 2){//grab block
                    //
                    grabber.setPosition(grabClosed);//down
                    //
                    if (currentTime > taskTimer + 500){
                        task = 3;
                        lifter.setPower(0.8);
                    }
                }else if (task == 3){//lifter up
                    //
                    if (upity.getDistance(DistanceUnit.INCH) > 2){
                        lifter.setPower(0.2);
                        planetary = true;
                        pMode = 3;
                        task = 1;
                    }
                }
                //
            }else if (pMode == 3){//transport mode
                //
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                //
                planetary = true;
                movement();
                mouse();
                //
                if (lifter.getPower() != 0.2){
                    pMode = 4;
                }
                //
            }else if (pMode == 4){//lifting mode
                //
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
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
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
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
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.AQUA);
                //
                mouse();
                //
                if (gamepad2.left_bumper){
                    moveTurn(1, 0, 0,0.3,0,0);
                }else if (gamepad2.right_bumper){
                    moveTurn(-1, 0, 0,0.3,0,0);
                }else {
                    if (!leftTouch.getState() && !rightTouch.getState()) {
                        still();
                    }else{
                        moveTurn(0,1,0,0.2,0,0);
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
                //coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
                //
                movement();
                //
                if (upity.getDistance(DistanceUnit.INCH) > 2.5){
                    if (extender.getCurrentPosition() > 500){
                        extender.setPower(-1);
                        lifter.setPower(0.2);
                    }else {
                        lifter.setPower(-.5);
                        extender.setPower(-.3);
                    }
                }else {
                    lifter.setPower(0.2);
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
            if ((gamepad1.right_trigger > 0) && !powerChange){
                if (powerFactor == 1.0){
                    powerFactor = 0.5;
                }else{
                    powerFactor = 1.0;
                }
                powerChange = true;
            }else if (!(gamepad1.right_trigger > 0) && powerChange){
                powerChange = false;
            }
            //
            if (gamepad2.left_bumper){
                ignore = true;
            }
            if (gamepad2.right_bumper){
                ignore = false;
            }
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
            if (!inTouch.getState()){
                extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            //
            //<editor-fold desc="telemetry">
            telemetry.update();
            telemetry.addData("mode", pMode);
            telemetry.addData("task", task);
            telemetry.addData("extenderPos",extender.getCurrentPosition());
            telemetry.addData("Planetary?", planetary);
            telemetry.addData("power",powerFactor);
            //telemetry.addData("conforming?", ma);
            telemetry.addData("hieght of gantry", upity.getDistance(DistanceUnit.INCH));
            telemetry.addData("relative yaw", getAngle());
            telemetry.addData("absolute yaw", fixAngle(getAngle() - origin));
            telemetry.addData("origin", origin);
            //</editor-fold>
        }
    }
    //
    public void movement(){
        //
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
        //
        if ((gamepad2.right_trigger > 0 || gamepad2.left_trigger > 0) && flipin && !flipinpress){
            flippers.setPosition(0.9);//out
            flipin = false;
            flipinpress = true;
        }else if ((gamepad2.right_trigger > 0 || gamepad2.left_trigger > 0) && !flipin && !flipinpress){
            flippers.setPosition(0.2);
            flipin = true;
            flipinpress = true;
        }else if ((gamepad2.right_trigger == 0 && gamepad2.left_trigger == 0) && flipinpress){
            flipinpress = false;
        }
        //
        //rightHook.setPosition(pos);//disabled
        //otherlefty
        if ((!upTouch.getState() || otherlefty > 0 || ignore) && pMode != 1){
            lifter.setPower(otherlefty + .2);
            telemetry.addData("lifting",otherlefty);
        }else if (pMode != 1){
            lifter.setPower(0.2);
        }
        if (!extending && !gamepad2.y) {
            if ((inTouch.getState() && otherrighty <= 0) || (outTouch.getState() && otherrighty >= 0)) {
                extender.setPower(-otherrighty * 0.6);
                telemetry.addData("extending", "normal");
            }else{
                extender.setPower(0);
            }
        }else if (!extending && gamepad2.y){
            extending = true;
            extender.setTargetPosition(extension);
            extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (extender.getCurrentPosition() < extension){
                extender.setPower(1.0);
            }else{
                extender.setPower(-1.0);
            }
            telemetry.addData("extending", "set move");
        }else if ((extending && !extender.isBusy()) || (otherrighty != 0)){
            extending = false;
            extender.setPower(0);
            extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("extending","stop move");
        }else{
            extender.setPower(0);
        }
        telemetry.addData("forward",extender.getCurrentPosition() < extension);
        if (gamepad2.x){
            extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
    //
}
