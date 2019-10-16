package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "motor test", group = "testing")
public class motorTest extends LinearOpMode{
    //
    DcMotor up1;
    DcMotor up2;
    DcMotor out;
    //
    public void runOpMode(){
        //
        up1 = hardwareMap.dcMotor.get("up1");
        up2 = hardwareMap.dcMotor.get("up2");
        out = hardwareMap.dcMotor.get("out");
        //
        waitForStart();
        //
        while (opModeIsActive()){
            //
            up1.setPower(-gamepad1.left_stick_y);
            up2.setPower(-gamepad1.left_stick_y);
            out.setPower(-gamepad1.right_stick_y * 0.2);
            //telemetry.addData("power", test.getPower());
            telemetry.update();
            //
        }
        //
        up1.setPower(0);
        up2.setPower(0);
        out.setPower(0);
        //
    }
    //
}
