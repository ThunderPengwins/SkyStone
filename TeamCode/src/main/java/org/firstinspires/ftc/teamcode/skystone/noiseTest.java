package org.firstinspires.ftc.teamcode.skystone;

import android.content.Context;
import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.R;

@TeleOp(name = "noiseTest", group = "test")
public class noiseTest extends OpMode {
    private static MediaPlayer mediaPlayer = null;
    private boolean pressedLast;
    public void init() {
        this.pressedLast = false;
    }
    public void loop() {
        if (this.gamepad1.a && !this.pressedLast) {
            telemetry.addData("Running", "");
            telemetry.update();
            start(this.hardwareMap.appContext);
            this.pressedLast = true;
        }
        else if (!this.gamepad1.a && this.pressedLast) {
            //CenaPlayer.stop();
            this.pressedLast = false;
        }
    }
    public void stop() {
        //CenaPlayer.stop();
    }
    public static void start(Context context) {
        if (mediaPlayer == null) mediaPlayer = MediaPlayer.create(context, R.raw.gear);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }
    /*public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try { mediaPlayer.prepare(); }
            catch (IOException e) {}
        }
    }*/
}
