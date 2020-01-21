package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "2-1B", group = "Stest")
public class b21 extends Myriad {
    //
    public void runOpMode() {
        //
        motorHardware();
        //
        firstHarwares();
        //
        setMotorReversals();
        //
        motorsWithEncoders();
        //
        initGyro();
        //
        waitForStartify();
        //
        boolean changing = false;
        double flipos = 0.0;
        //
        while (opModeIsActive()){
            //
            extender.setPower(-gamepad2.right_stick_y);
            //
            telemetry.addData("flipos",flipos);
            telemetry.addData("grabber", grabber.getPosition());
            telemetry.addData("upity", upity.getDistance(DistanceUnit.INCH));
            telemetry.addData("left hook", leftHook.getPosition());
            telemetry.addData("right hook", rightHook.getPosition());
            telemetry.addData("qbert", qbert.getState());
            telemetry.addData("george", george.getState());
            telemetry.addData("in", inTouch.getState());
            telemetry.addData("out", outTouch.getState());
            telemetry.addData("up",upTouch.getState());
            telemetry.addData("left touch", leftTouch.getState());
            telemetry.addData("right touch", rightTouch.getState());
            telemetry.addData("Issue", "NextGen");
            telemetry.update();
            //
            if(gamepad1.dpad_up && !changing){
                flipos += .1;
                changing = true;
            }else if (gamepad1.dpad_down && !changing){
                flipos -= .1;
                changing = true;
            }else if (!gamepad1.dpad_up && !gamepad1.dpad_down && changing){
                changing = false;
            }
            //
            flippers.setPosition(flipos);
            //
        }
        //
    }
    //
}
