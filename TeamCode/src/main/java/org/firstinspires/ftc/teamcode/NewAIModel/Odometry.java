package org.firstinspires.ftc.teamcode.NewAIModel;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odometry {
    public DcMotor xAxis;
    public DcMotor yAxis;
    public DcMotor zAxis;

    public void init(HardwareMap hardwareMap) {
        // Initialize odometry motors with specific names from the hardware map
        xAxis = hardwareMap.get(DcMotor.class, "lf_drive (x_axis)");
        yAxis = hardwareMap.get(DcMotor.class, "y_axis");
        zAxis = hardwareMap.get(DcMotor.class, "z_axis");

        // Set odometry motors to run with encoders
        xAxis.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yAxis.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        zAxis.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int[] getPositions() {
        // Return an array of odometry positions
        return new int[]{xAxis.getCurrentPosition(), yAxis.getCurrentPosition(), zAxis.getCurrentPosition()};
    }
}
