package org.firstinspires.ftc.teamcode.skystone;

import android.media.MediaPlayer;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.R;

public abstract class HoloGrande extends LinearOpMode {
    abstract public void runOpMode();
    //
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;
    //
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor backRight;
    //
    Double loctarang;
    Double glotarang;
    //
    //<editor-fold desc="hardwares 1">
    Servo grabber;
    DcMotor lifter;
    DcMotor extender;
    DigitalChannel qbert;//cube in
    DigitalChannel george;//gantry down
    DigitalChannel inTouch;
    DigitalChannel outTouch;
    DistanceSensor upity;
    //
    Servo leftHook;
    Servo rightHook;
    DigitalChannel leftTouch;
    DigitalChannel rightTouch;
    ColorSensor leftColor;
    ColorSensor rightColor;
    //
    //DistanceSensor frontLeftD;
    //DistanceSensor frontRightD;
    DistanceSensor leftD;
    DistanceSensor rightD;
    DistanceSensor leftR;
    DistanceSensor rightR;
    DistanceSensor backR;
    //
    Integer cpr = 28; //counts per rotation
    double gearratio = 20;
    Double diameter = 4.125;
    Double cpi = (cpr * gearratio)/(Math.PI * diameter); //counts per inch, 28cpr * gear ratio / (2 * pi * diameter (in inches, in the center))
    Double bias = 0.8;
    //
    Double conversion = cpi * bias;
    //
    final Double grabOpen = 0.7;
    final Double grabClosed = 0.0;
    final Double hookUpLeft = 0.6;
    final Double hookDownLeft = 0.0;
    final Double hookUpRight = 0.4;
    final Double hookDownRight = 1.0;
    //
    //</editor-fold>
    //
    private static MediaPlayer mediaPlayer = null;
    //
    //<editor-fold desc="establishing">
    public void motorHardware(){
        frontRight = hardwareMap.dcMotor.get("frontright");
        frontLeft = hardwareMap.dcMotor.get("frontleft");
        backRight = hardwareMap.dcMotor.get("backright");
        backLeft = hardwareMap.dcMotor.get("backleft");
    }
    //
    public void setMotorReversals(){
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    //
    public void secondaryMotorReversals(){
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    //
    public void motorsWithEncoders(){
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    //
    public void motorsWithoutEncoders(){
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    //
    public void motorsToPosition(){
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //
    public void firstHarwares(){
        grabber = hardwareMap.servo.get("grabber");
        lifter = hardwareMap.dcMotor.get("lifter");
        extender = hardwareMap.dcMotor.get("extender");
        qbert = hardwareMap.digitalChannel.get("qbert");
        george = hardwareMap.digitalChannel.get("george");
        inTouch = hardwareMap.digitalChannel.get("inTouch");
        outTouch = hardwareMap.digitalChannel.get("outTouch");
        //
        upity = hardwareMap.get(DistanceSensor.class, "upity");
        //
        leftHook = hardwareMap.servo.get("lefthook");
        rightHook = hardwareMap.servo.get("righthook");
        leftTouch = hardwareMap.digitalChannel.get("lefttouch");
        rightTouch = hardwareMap.digitalChannel.get("righttouch");
        leftColor = hardwareMap.colorSensor.get("leftcolor");
        rightColor = hardwareMap.colorSensor.get("rightcolor");
        //
        //frontLeftD = hardwareMap.get(DistanceSensor.class, "frontLeftCD");
        //frontRightD = hardwareMap.get(DistanceSensor.class, "frontRightCD");
        leftD = hardwareMap.get(DistanceSensor.class, "leftD");
        rightD = hardwareMap.get(DistanceSensor.class, "rightD");
        leftR = hardwareMap.get(DistanceSensor.class, "leftR");
        rightR = hardwareMap.get(DistanceSensor.class,"rightR");
        backR = hardwareMap.get(DistanceSensor.class, "backR");
        qbert.setMode(DigitalChannel.Mode.INPUT);
        george.setMode(DigitalChannel.Mode.INPUT);
        //
        //extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //
    }
    //
    public void waitForStartify(){
        waitForStart();
    }
    //
    public void initGyro(){
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "GyroCal.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        telemetry.addData("init imu","");
        telemetry.update();
        imu.initialize(parameters);
        telemetry.addData("imu initiated", "");
        telemetry.update();
    }
    //
    public double getAngle(){
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return -angles.firstAngle;
    }
    //</editor-fold>
    //
    //<editor-fold desc="maths operations">
    public double pythagorus(double a, double b){
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
    //
    public double calcHoloAngle(double x, double y, double total){
        double angle;
        if(y > 0){
            angle = Math.acos(x / total) * 180 / Math.PI;
        }else {
            angle = 360 - (Math.acos(x / total) * 180 / Math.PI);
        }
        angle -= 45;
        return angle;
    }
    //
    public double fixAngle(double angle){
        if(angle > 180){
            angle -= 360;
        }else if(angle < -180){
            angle += 360;
        }
        return angle;
    }
    //
    public boolean inBounds(double target, double error){
        //
        double rB = target + error;
        double lB = target - error;
        //
        double a = getAngle();
        //
        if (fixAngle(rB) != rB || fixAngle(lB) != lB){
            if ((lB < a && a < 180) || (-180 < a && a < rB)){
                return true;
            }else {
                return false;
            }
        }else{
            if (lB < getAngle() && getAngle() < rB){
                return true;
            }else {
                return false;
            }
        }
    }
    //</editor-fold>
    //
    public void forward(double left, double right){
        frontRight.setPower(right);
        backLeft.setPower(left);
        frontLeft.setPower(left);
        backRight.setPower(right);//set motor powers
    }
    //
    public void turnWithGyro(double degrees, double speedDirection){
        //<editor-fold desc="Initialize">
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double yaw = -angles.firstAngle;//make this negative
        telemetry.addData("Speed Direction", speedDirection);
        telemetry.addData("Yaw", yaw);
        telemetry.update();
        //
        telemetry.addData("stuff", speedDirection);
        telemetry.update();
        //
        double first;
        double second;
        //</editor-fold>
        //
        if (speedDirection > 0){//set target positions
            //<editor-fold desc="turn right">
            if (degrees > 10){
                first = (degrees - 10) + devertify(yaw);
                second = degrees + devertify(yaw);
            }else{
                first = devertify(yaw);
                second = degrees + devertify(yaw);
            }
            //</editor-fold>
        }else{
            //<editor-fold desc="turn left">
            if (degrees > 10){
                first = devertify(-(degrees - 10) + devertify(yaw));
                second = devertify(-degrees + devertify(yaw));
            }else{
                first = devertify(yaw);
                second = devertify(-degrees + devertify(yaw));
            }
            //
            //</editor-fold>
        }
        //
        //<editor-fold desc="Go to position">
        Double firsta = convertify(first - 5);//175
        Double firstb = convertify(first + 5);//-175
        //
        turnWithEncoder(speedDirection);
        //
        telemetry.addData("turn","stage 1");
        telemetry.update();
        //
        if (Math.abs(firsta - firstb) < 11) {
            while (!(firsta < yaw && yaw < firstb) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
            }
        }else{
            //
            while (!((firsta < yaw && yaw < 180) || (-180 < yaw && yaw < firstb)) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("first before", first);
                telemetry.addData("first after", convertify(first));
                telemetry.update();
            }
        }
        //
        Double seconda = convertify(second - 5);//175
        Double secondb = convertify(second + 5);//-175
        //
        turnWithEncoder(speedDirection / 3);
        //
        telemetry.addData("turn","stage 2");
        telemetry.update();
        //
        if (Math.abs(seconda - secondb) < 11) {
            while (!(seconda < yaw && yaw < secondb) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
            }
            while (!((seconda < yaw && yaw < 180) || (-180 < yaw && yaw < secondb)) && opModeIsActive()) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
        }
        //</editor-fold>
        //
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public double devertify(double degrees){
        if (degrees < 0){
            degrees = degrees + 360;
        }
        return degrees;
    }
    public double convertify(double degrees){
        if (degrees > 179){
            degrees = -(360 - degrees);
        } else if(degrees < -180){
            degrees = 360 + degrees;
        } else if(degrees > 360){
            degrees = degrees - 360;
        }
        return degrees;
    }
    public void turnWithEncoder(double input){
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //
        frontLeft.setPower(input);
        backLeft.setPower(input);
        frontRight.setPower(-input);
        backRight.setPower(-input);
    }
    //
    public void turnToAngle(double angle, double speed){
        //
        if (getAngle() > angle){
            turnWithEncoder(-speed);
        }else{
            turnWithEncoder(speed);
        }
        //
        while (!(angle - 5 < getAngle() && getAngle() < angle + 5)){}
        still();
        //
    }
    //
    public void moveToPosition(double inches, double speed, boolean bridge){
        //
        int move = (int)(Math.round(inches*conversion));
        //
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + move);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + move);
        backRight.setTargetPosition(backRight.getCurrentPosition() + move);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + move);
        //
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);
        //
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()){}
        if (!bridge) {
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
        }
        return;
    }
    //
    public void strafeToPosition(double inches, double speed, boolean bridge){
        //
        int move = (int)(Math.round(inches*conversion));
        //
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - move);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + move);
        backRight.setTargetPosition(backRight.getCurrentPosition() + move);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - move);
        //
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        frontLeft.setPower(speed);
        backLeft.setPower(-speed);
        frontRight.setPower(-speed);
        backRight.setPower(speed);
        //
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()){}
        if (!bridge) {
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
        }
        return;
    }
    //
    public void moveToAndGantry(double inches, double speed, boolean bridge){
        //
        int move = (int)(Math.round(inches*conversion));
        //
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + move);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + move);
        backRight.setTargetPosition(backRight.getCurrentPosition() + move);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + move);
        //
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);
        //
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opModeIsActive()){
            if (upity.getDistance(DistanceUnit.INCH) < 3.3){
                lifter.setPower(0.4);
            }else if (upity.getDistance(DistanceUnit.INCH) > 4){
                lifter.setPower(-0.4);
                extender.setPower(-.3);
            }else {
                lifter.setPower(0);
                extender.setPower(0);
            }
        }
        if (!bridge) {
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
        }
        return;
    }
    //
    //<editor-fold desc="movement teleop">
    public void move(double x, double y, double factor){
        //
        //double total = pythagorus(y, x);//find total power
        double total = 1;
        //
        double angle = calcHoloAngle(x, y, total);//calculate angle of joystick
        //
        double aWheelsPower = Math.cos(angle * Math.PI / 180);
        double bWheelsPower = Math.sin(angle * Math.PI / 180);//find power for a/b motors
        //
        telemetry.addData("a power", aWheelsPower);
        telemetry.addData("b power", bWheelsPower);
        //
        frontRight.setPower(bWheelsPower * total * factor);
        backLeft.setPower(bWheelsPower * total * factor);
        frontLeft.setPower(aWheelsPower * total * factor);
        backRight.setPower(aWheelsPower * total * factor);//set motor powers
        //
        telemetry.addData("front right set",bWheelsPower * total * factor + ", get: " + frontRight);
    }
    //
    public void moveTurn(double x, double y, double t, double factor, double turnFactor, double ayaw) {
        //
        double cyaw = -angles.firstAngle;
        if (t > 0 && cyaw - ayaw < -90) {
            cyaw += 360;
        } else if (t < 0 && cyaw - ayaw > 90) {
            cyaw = -360 - cyaw;
        }
        //
        double total = pythagorus(y, x);//find total power
        //
        double aWheelsPower;
        double bWheelsPower;
        //
        if (total == 0) {
            aWheelsPower = 0;
            bWheelsPower = 0;
        } else {
            double angle = calcHoloAngle(x, y, total);//calculate angle of joystick
            //
            double calc = cyaw - ayaw;
            calc = fixAngle(calc);
            angle += calc;
            //
            aWheelsPower = Math.cos(angle * Math.PI / 180);//front left & back right
            bWheelsPower = Math.sin(angle * Math.PI / 180);//front right & back left
        }
        //
        double a = ((0.5 * aWheelsPower * total) + (0.5 * turnFactor * t)) * factor;//front left
        double b = ((0.5 * bWheelsPower * total) + (-0.5 * turnFactor * t)) * factor;//front right
        double c = ((0.5 * bWheelsPower * total) + (0.5 * turnFactor * t)) * factor;//back left
        double d = ((0.5 * aWheelsPower * total) + (-0.5 * turnFactor * t)) * factor;//back right
        //
        telemetry.addData("turn factor", turnFactor);
        telemetry.addData("t", t);
        telemetry.addData("factor", factor);
        telemetry.addData("a", a);
        //
        frontLeft.setPower(a);
        frontRight.setPower(b);
        backLeft.setPower(c);
        backRight.setPower(d);//set motor powers
        //
    }
    //
    public void globalMoveTurn(double x, double y, double t, double factor, double turnFactor, double origin) {
        //
        double cang = fixAngle(getAngle() - origin);
        //
        double total = pythagorus(y, x);//find total power
        //
        double aWheelsPower;
        double bWheelsPower;
        //
        if (total == 0) {
            aWheelsPower = 0;
            bWheelsPower = 0;
        } else {
            double h = calcHoloAngle(x, y, total);//calculate angle of joystick
            loctarang = h;
            //
            double g = h + cang;
            //g = fixAngle(g);
            glotarang = g;
            //
            aWheelsPower = Math.cos(g * Math.PI / 180);//front left & back right
            bWheelsPower = Math.sin(g * Math.PI / 180);//front right & back left
        }
        //
        double moveP = 0.5;
        if(aWheelsPower == 0 && bWheelsPower == 0){
            moveP = 0;
        }else if(t == 0){
            moveP = 1;
        }
        double turnP = 1 - moveP;
        //
        double a = ((moveP * aWheelsPower * total) + (turnP * turnFactor * t)) * factor;//front left
        double b = ((moveP * bWheelsPower * total) + (-turnP * turnFactor * t)) * factor;//front right
        double c = ((moveP * bWheelsPower * total) + (turnP * turnFactor * t)) * factor;//back left
        double d = ((moveP * aWheelsPower * total) + (-turnP * turnFactor * t)) * factor;//back right
        //
        telemetry.addData("activated", "global move turn");
        //
        frontLeft.setPower(a);
        frontRight.setPower(b);
        backLeft.setPower(c);
        backRight.setPower(d);//set motor powers
        //
    }
    //
    public double conformLeft(double origin){
        //
        return Math.floor((fixAngle(getAngle() - origin)) / 45) * 45;
    }
    //
    public double conformRight(double origin){
        //
        return Math.ceil((fixAngle(getAngle() - origin)) / 45) * 45;
    }
    //
    public void turnRobot(double rx, double factor, double turnFactor){
        frontLeft.setPower(rx * factor * turnFactor);
        frontRight.setPower(-rx * factor * turnFactor);
        backLeft.setPower(rx * factor * turnFactor);
        backRight.setPower(-rx * factor * turnFactor);
    }
    //
    public void still(){
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    //</editor-fold>
    //
    public void gear(){
        if (mediaPlayer == null) mediaPlayer = MediaPlayer.create(this.hardwareMap.appContext, R.raw.gear);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }
    //
    public void dave() {
        if (mediaPlayer == null) mediaPlayer = MediaPlayer.create(this.hardwareMap.appContext, R.raw.dave);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }
}
