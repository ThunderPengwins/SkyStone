package org.firstinspires.ftc.teamcode.skystone;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

public abstract class HoloLumi extends HoloGrande{
    abstract public void runOpMode();
    //
    RevBlinkinLedDriver coolLights;
    //
    public void lightHardware(){
        coolLights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
    }
    //
    public void setLight(String color){
        if (color == "red"){
            coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
        }else if (color == "green") {
            coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        }else if (color == "aqua"){
            coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.AQUA);
        }else if (color == "orange"){
            coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
        }else{
            coolLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(Integer.parseInt(color)));
        }
    }
}
