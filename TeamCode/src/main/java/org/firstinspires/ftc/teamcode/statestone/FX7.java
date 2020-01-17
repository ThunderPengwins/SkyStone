package org.firstinspires.ftc.teamcode.statestone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "FX-7", group = "Sreal")
public class FX7 extends Myriad {
    //
    public void runOpMode() {
        //
        fullInit();
        //
        waitForStartify();
        //
        int axa = frontRight.getCurrentPosition();
        int axb = backLeft.getCurrentPosition();
        int ax = (axa + axb) / 2;
        double bx = ax / conversion;
        int aya =  frontLeft.getCurrentPosition();
        int ayb = backRight.getCurrentPosition();
        int ay = (aya + ayb) / 2;
        double by = ay / conversion;
        //
        double ang;
        double h;
        //
        double x;
        double y;
        //
        while (opModeIsActive()){
            //
            globalMoveTurn(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0.5, 0.5, 0);
            //
            axa = frontRight.getCurrentPosition();
            axb = backLeft.getCurrentPosition();
            ax = (axa + axb) / 2;
            bx = ax / conversion;
            aya =  frontLeft.getCurrentPosition();
            ayb = backRight.getCurrentPosition();
            ay = (aya + ayb) / 2;
            by = ay / conversion;
            //
            h = pythagorus(bx, by);
            //
            if (ay > 0){
                ang = Math.toDegrees(Math.cos(bx / h));
            }else {
                ang = Math.toDegrees(Math.cos(bx / h));
            }
            ang = fixUnitAngle(ang - 45);
            //
            y = h * Math.cos(Math.toRadians(ang));
            x = h * Math.sin(Math.toRadians(ang));
            //
            telemetry.addData("x",x);
            telemetry.addData("y",y);
            telemetry.addData("ax",ax);
            telemetry.addData("ay",ay);
            telemetry.addData("bx",bx);
            telemetry.addData("by",by);
            telemetry.addData("h",h);
            telemetry.addData("ang",ang);
            telemetry.update();
            //
        }
        //
    }
    //
}
