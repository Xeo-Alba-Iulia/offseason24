package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic TeleOp", group="TeleOp")
public class BasicTeleOp extends OpMode {
    private DcMotor lfMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor lbMotor = null;
    private DcMotor rbMotor = null;

    public void init(){
        lfMotor  = hardwareMap.get(DcMotor.class, "lf_drive");
        rfMotor = hardwareMap.get(DcMotor.class, "rf_drive");
        lbMotor = hardwareMap.get(DcMotor.class, "lb_drive");
        rbMotor = hardwareMap.get(DcMotor.class, "rb_drive");

        rfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop(){
        double lfPower;
        double rfPower;
        double lbPower;
        double rbPower;

        double xPower = gamepad1.left_stick_y;
        double yPower = gamepad1.left_stick_x;
        double zPower = gamepad1.right_stick_x;

        lfPower = Range.clip(xPower + yPower - zPower, -1, 1);
        rfPower = Range.clip(xPower - yPower + zPower, -1, 1);
        lbPower = Range.clip(xPower - yPower - zPower, -1, 1);
        rbPower = Range.clip(xPower + yPower + zPower, -1, 1);

        telemetry.addData("LeftFront: ", lfPower);
        telemetry.addData("RightFront: ", rfPower);
        telemetry.addData("LeftBack: ", lbPower);
        telemetry.addData("RightBack: ", rbPower);

        lfMotor.setPower(lfPower);
        rfMotor.setPower(rfPower);
        lbMotor.setPower(lbPower);
        rbMotor.setPower(rbPower);
    }
}
