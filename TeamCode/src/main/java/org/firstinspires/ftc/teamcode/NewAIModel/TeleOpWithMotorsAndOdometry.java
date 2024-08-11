package org.firstinspires.ftc.teamcode.NewAIModel;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp with Motors and Odometry", group="TeleOp")
public class TeleOpWithMotorsAndOdometry extends OpMode {

    private final Motors motors = new Motors();
    private final Odometry odometry = new Odometry();

    @Override
    public void init() {
        // Initialize motors and odometry
        motors.init(hardwareMap);
        odometry.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Get joystick values
        double drive = -gamepad1.left_stick_y; // Forward/backward
        double strafe = gamepad1.left_stick_x; // Left/right
        double rotate = gamepad1.right_stick_x; // Rotation

        // Calculate motor powers for mecanum drive
        double leftFrontPower = drive + strafe + rotate;
        double rightFrontPower = drive - strafe - rotate;
        double leftBackPower = drive - strafe + rotate;
        double rightBackPower = drive + strafe - rotate;

        // Normalize motor powers to ensure they are within -1.0 to 1.0
        double maxPower = Math.max(Math.abs(leftFrontPower), Math.max(Math.abs(rightFrontPower), Math.max(Math.abs(leftBackPower), Math.abs(rightBackPower))));
        if (maxPower > 1.0) {
            leftFrontPower /= maxPower;
            rightFrontPower /= maxPower;
            leftBackPower /= maxPower;
            rightBackPower /= maxPower;
        }

        // Apply conditional logic based on button presses
        if (gamepad1.cross) {
            leftFrontPower *= 0.5;
            rightFrontPower *= 0.5;
            leftBackPower *= 0.5;
            rightBackPower *= 0.5;
        } else if (gamepad1.square) {
            leftFrontPower *= 1.5;
            rightFrontPower *= 1.5;
            leftBackPower *= 1.5;
            rightBackPower *= 1.5;
        } else if (gamepad1.triangle) {
            leftFrontPower = 0;
            rightFrontPower = 0;
            leftBackPower = 0;
            rightBackPower = 0;
        }

        // Apply calculated power to motors
        motors.leftFront.setPower(leftFrontPower);
        motors.rightFront.setPower(rightFrontPower);
        motors.leftBack.setPower(leftBackPower);
        motors.rightBack.setPower(rightBackPower);

        // Get odometry positions
        int[] odometryPositions = odometry.getPositions();

        // Update telemetry
        telemetry.addData("Motor Powers", "LF: %.2f, RF: %.2f, LB: %.2f, RB: %.2f", leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
        telemetry.addData("Odometry", "X: %d, Y: %d, Z: %d", odometryPositions[0], odometryPositions[1], odometryPositions[2]);
        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop all motors
        motors.leftFront.setPower(0);
        motors.rightFront.setPower(0);
        motors.leftBack.setPower(0);
        motors.rightBack.setPower(0);
    }
}
