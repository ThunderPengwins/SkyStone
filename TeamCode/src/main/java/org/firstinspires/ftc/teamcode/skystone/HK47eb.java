package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Disabled
@Autonomous(name = "HK47-easy-blue", group = "Sreal")
public class HK47eb extends HoloLumi {
    //
    public void runOpMode() {
        //
        motorHardware();
        //
        firstHarwares();
        //
        secondaryMotorReversals();
        //
        motorsWithEncoders();
        resetEncoders();
        //
        telemetry.addData("Hardware initialized","");
        //
        initGyro();
        //
        if (light){
            leftColor.enableLed(true);
            rightColor.enableLed(true);
        }else {
            leftColor.enableLed(false);
            rightColor.enableLed(false);
        }
        //
        double origin = getAngle();
        //
        waitForStartify();
        //
        leftHook.setPosition(hookUpLeft);//0 is down, 0.6 is up
        rightHook.setPosition(hookUpRight);//0 is down, 0.6 is up
        //
        grabber.setPosition(grabOpen);
        //
        //forward(.5, .5);
        //
        message = "forward-1";
        moveToPosition(27 + gap1, .3, true);//27
        //moveToPosition(4, .2, false);
        //
        //while (!(backR.getDistance(DistanceUnit.INCH) > 15 + gap1) && opModeIsActive()){ };
        //
        message = "correct-1";
        turnToAngle(0,.05);
        //
        still();
        //
        sleep(500);
        //
        telemetry.addData("Skystone", leftColor.red() == 0);
        telemetry.update();
        //
        message = "right-pos1";
        Integer position = 0;
        moveWithSensor("right",33.5,true,.3,1.0,0.0,false);
        //
        if (leftColor.red() == 0){
            position = 1;
            still();
        }else {
            message = "right-pos2";
            moveWithSensor("right",26.5,true,.3,1.0,0.0,false);
            //
            if (leftColor.red() == 0) {
                position = 2;
                still();
            }else {
                message = "right-pos3";
                position = 3;
                moveWithSensor("right",18.5,true,.3,1.0,0.0,true);
            }
        }
        //
        message = "backup-1";
        moveToPosition(-7, .5, true);
        //
        leftHook.setPosition(hookDownLeft);
        //
        sleep(500);
        //
        message = "toStone-1";
        motorsWithEncoders();
        move(0, 1, .4);//charge (to stone)
        while (!(qbert.getState()) && opModeIsActive() && !(backR.getDistance(DistanceUnit.INCH) > 60)) {
            if (upity.getDistance(DistanceUnit.INCH) < 3.5){
                lifter.setPower(0.4);
            }else if (upity.getDistance(DistanceUnit.INCH) > 4){
                lifter.setPower(-0.4);
                extender.setPower(-.3);
            }else {
                lifter.setPower(0);
                extender.setPower(0);
            }
            telemetry.addData("stage",message);
            telemetry.addData("backR",backR.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
        still();
        boolean fail = false;
        if (backR.getDistance(DistanceUnit.INCH) > 60){//safety
            fail = true;
        }
        //
        message = "gantryDown-1";
        lifter.setPower(-.7);//gantry down to stone
        while (!george.getState() && opModeIsActive() && !fail){
            telemetry.addData("stage",message);
            telemetry.addData("fail",fail);
            telemetry.update();
        }
        lifter.setPower(0);
        //
        if (fail){
            lifter.setPower(-.4);
            while (!(upity.getDistance(DistanceUnit.INCH) < 2.2)){}
            lifter.setPower(0);
        }
        //
        message = "grabStone-1";
        setStage();
        //
        grabber.setPosition(grabClosed);//grab it
        //
        sleep(500);
        //
        message = "gantryUp-1";
        setStage();
        //
        lifter.setPower(0.4);//gantry up
        while (!(upity.getDistance(DistanceUnit.INCH) > 2.2) && opModeIsActive()){}
        lifter.setPower(0);
        //
        leftHook.setPosition(hookUpLeft);
        //back up
        message = "backup-2";
        moveWithSensor("back",28.0,true,0.4,0.0,-1.0,true);//
        //</editor-fold>
        //
        sleep(200);
        //
        message = "turn-toTape";
        turnWithGyro(80, -.3);
        //
        message = "correct-2";
        turnToAngle(-88,.05);//premature correct
        //
        if (position == 1){
            message = "move-tape-pos1";
            moveToPosition(40,.6,true);
        }else if (position == 2){
            message = "move-tape-pos1";
            moveToPosition(47,.6,true);
        }else{
            message = "move-tape-pos1";
            moveToPosition(55,.6,true);
        }
        //
        message = "release-stone";
        setStage();
        grabber.setPosition(grabOpen);
        //
        sleep(500);
        //
        turnToAngle(-90, 0.05);
        //
        moveToPosition(-10,.6,false);
        //
    }
    //
}
