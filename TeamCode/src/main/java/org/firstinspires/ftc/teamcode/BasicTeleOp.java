package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic TeleOp", group="TeleOp")
public class BasicTeleOp extends OpMode {

    static public class Motors {
        public DcMotor RightFront;
        public DcMotor LeftFront;
        public DcMotor RightBack;
        public DcMotor LeftBack;
    }
    private Motors motors;

    public void init(){
        motors.LeftFront = hardwareMap.get(DcMotor.class, "LeftFront_drive");
        motors.RightFront = hardwareMap.get(DcMotor.class, "RightFront_drive");
        motors.LeftBack = hardwareMap.get(DcMotor.class, "LeftBack_drive");
        motors.RightBack = hardwareMap.get(DcMotor.class, "RightBack_drive");

        motors.RightFront.setDirection(DcMotor.Direction.REVERSE);
        motors.LeftBack.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop(){
        double lfPower;
        double rfPower;
        double lbPower;
        double rbPower;

        double xPower = gamepad1.left_stick_y;
        double yPower = gamepad1.left_stick_x;
        double zPower = gamepad1.right_stick_x;

        lfPower = Range.clip(xPower + yPower + zPower, -1, 1);
        rfPower = Range.clip(xPower - yPower - zPower, -1, 1);
        lbPower = Range.clip(xPower - yPower + zPower, -1, 1);
        rbPower = Range.clip(xPower + yPower - zPower, -1, 1);

        motors.LeftFront.setPower(lfPower);
        motors.RightFront.setPower(rfPower);
        motors.LeftBack.setPower(lbPower);
        motors.RightBack.setPower(rbPower);
    }
}
