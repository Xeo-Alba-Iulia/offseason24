package org.firstinspires.ftc.teamcode.NewAIModel;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    public DcMotor rightFront;
    public DcMotor leftFront;
    public DcMotor rightBack;
    public DcMotor leftBack;

    public void init(HardwareMap hardwareMap) {
        // Initialize motors with specific names from the hardware map
        rightFront = hardwareMap.get(DcMotor.class, "rf_drive");
        leftFront = hardwareMap.get(DcMotor.class, "lf_drive (x_axis)");
        rightBack = hardwareMap.get(DcMotor.class, "rb_drive");
        leftBack = hardwareMap.get(DcMotor.class, "lb_drive");

        // Set motor directions
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        // Set motors to run without encoders
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
