package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Advanced TeleOp with Odometry", group="TeleOp")
public class AdvancedTeleOp extends OpMode {

    // Nested class to manage motors
    public class Motors {
        public DcMotor RightFront;
        public DcMotor LeftFront;
        public DcMotor RightBack;
        public DcMotor LeftBack;
    }

    // Nested class to manage odometry
    public class Odometry {
        public DcMotor x_axis;
        public DcMotor y_axis;
        public DcMotor z_axis;
    }

    private final Motors motors = new Motors();
    private final Odometry odometry = new Odometry();

    @Override
    public void init() {
        // Initialize motors
        motors.LeftFront = hardwareMap.get(DcMotor.class, "lf_drive (x_axis)");
        motors.RightFront = hardwareMap.get(DcMotor.class, "rf_drive");
        motors.LeftBack = hardwareMap.get(DcMotor.class, "lb_drive");
        motors.RightBack = hardwareMap.get(DcMotor.class, "rb_drive");

        // Set motor directions
        motors.RightFront.setDirection(DcMotor.Direction.REVERSE);

        // Initialize odometry motors
        odometry.x_axis = hardwareMap.get(DcMotor.class, "lf_drive (x_axis)"); // Shared with LeftFront
        odometry.y_axis = hardwareMap.get(DcMotor.class, "y_axis");
        odometry.z_axis = hardwareMap.get(DcMotor.class, "z_axis");

        // Reset and set odometry motors to run without encoders
        odometry.x_axis.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odometry.y_axis.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odometry.z_axis.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        odometry.x_axis.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odometry.y_axis.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odometry.z_axis.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        // Nested class to calculate motor powers based on joystick inputs
        class Power {
            double lfPower;
            double rfPower;
            double lbPower;
            double rbPower;

            Power() {
                double xPower = gamepad1.left_stick_y;
                double yPower = gamepad1.left_stick_x;
                double zPower = gamepad1.right_stick_x;

                lfPower = Range.clip(xPower - yPower - zPower, -1.0, 1.0);
                rfPower = Range.clip(xPower + yPower + zPower, -1.0, 1.0);
                lbPower = Range.clip(xPower + yPower - zPower, -1.0, 1.0);
                rbPower = Range.clip(xPower - yPower + zPower, -1.0, 1.0);
            }
        }

        // Nested class to calculate robot's position based on odometry readings
        class Position {
            int xPosition;
            int yPosition;
            int zPosition;

            Position() {
                xPosition = odometry.x_axis.getCurrentPosition();
                yPosition = odometry.y_axis.getCurrentPosition();
                zPosition = odometry.z_axis.getCurrentPosition();
            }
        }

        Power power = new Power();
        Position position = new Position();

        // Modify motor powers based on gamepad1 buttons
        if (gamepad1.cross) {
            power.lfPower *= 0.5;
            power.rfPower *= 0.5;
            power.lbPower *= 0.5;
            power.rbPower *= 0.5;
        }

        if (gamepad1.square) {
            power.lfPower = 0.8 * power.lfPower;
            power.rfPower = 0.8 * power.rfPower;
            power.lbPower = 0.8 * power.lbPower;
            power.rbPower = 0.8 * power.rbPower;
        }

        if (gamepad1.triangle) {
            power.lfPower = 1.0;
            power.rfPower = 1.0;
            power.lbPower = 1.0;
            power.rbPower = 1.0;
        }

        // Apply the calculated motor powers to the respective motors
        motors.LeftFront.setPower(power.lfPower);
        motors.RightFront.setPower(power.rfPower);
        motors.LeftBack.setPower(power.lbPower);
        motors.RightBack.setPower(power.rbPower);

        // Display telemetry data for motor powers and odometry positions
        telemetry.addData("Motor Powers", "LF: %.2f, RF: %.2f, LB: %.2f, RB: %.2f",
                power.lfPower, power.rfPower, power.lbPower, power.rbPower);
        telemetry.addData("Odometry Positions", "X: %d, Y: %d, Z: %d",
                position.xPosition, position.yPosition, position.zPosition);
        telemetry.update();
    }
}
