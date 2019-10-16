package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "inputs", group = "test")
public class inputs extends LinearOpMode {
    //
    public void runOpMode(){
        //
        waitForStart();
        //
        while (opModeIsActive()){
            telemetry.addData("leftx", gamepad1.left_stick_x);
            telemetry.addData("lefty", -gamepad1.left_stick_y);
            telemetry.addData("righty", -gamepad1.right_stick_y);
            telemetry.update();
        }
        //
    }
    //
}
