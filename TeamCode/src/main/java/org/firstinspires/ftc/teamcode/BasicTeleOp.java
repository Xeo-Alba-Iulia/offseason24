package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic TeleOp", group="TeleOp")
public class BasicTeleOp extends OpMode {
    public static class Motors {
        public DcMotor RightFront;
        public DcMotor LeftFront;
        public DcMotor RightBack;
        public DcMotor LeftBack;
    }
    public static class Odometry {
        public DcMotor xAxisOdometry;
        public DcMotor yAxisOdometry;
        public DcMotor zAxisOdometry;
    }
    private final Motors motors = new Motors();
    private final Odometry odometry = new Odometry();

    public void init(){
        motors.LeftFront = hardwareMap.get(DcMotor.class, "lf_drive (x_axis)");
        motors.RightFront = hardwareMap.get(DcMotor.class, "rf_drive");
        motors.LeftBack = hardwareMap.get(DcMotor.class, "lb_drive");
        motors.RightBack = hardwareMap.get(DcMotor.class, "rb_drive");

        motors.RightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        odometry.xAxisOdometry = hardwareMap.get(DcMotor.class, "y_axis");
        odometry.yAxisOdometry = hardwareMap.get(DcMotor.class, "lf_drive (x_axis)");
        odometry.zAxisOdometry = hardwareMap.get(DcMotor.class, "z_axis");

        odometry.xAxisOdometry.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odometry.yAxisOdometry.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odometry.zAxisOdometry.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        odometry.xAxisOdometry.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odometry.yAxisOdometry.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odometry.zAxisOdometry.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void loop(){
        class Power {
            double leftFront;
            double rightFront;
            double leftBack;
            double rightBack;
            final double yAxis = gamepad1.left_stick_x;
            final double xAxis = gamepad1.left_stick_y;
            final double zAxis = gamepad1.right_stick_x;
        }
        final Power power = new Power();

        class Position {
            final double xPosition = odometry.xAxisOdometry.getCurrentPosition();
            final double yPosition = odometry.yAxisOdometry.getCurrentPosition();
            final double zPosition = odometry.yAxisOdometry.getCurrentPosition() - odometry.zAxisOdometry.getCurrentPosition();
        }
        final Position position = new Position();

        if (gamepad1.cross) {
            power.leftFront = Range.clip(power.xAxis - power.yAxis - Range.clip(position.zPosition / 20000, -0.5, 0.5), -1, 1);
            power.rightFront = Range.clip(power.xAxis + power.yAxis + Range.clip(position.zPosition / 20000, -0.5, 0.5), -1, 1);
            power.leftBack = Range.clip(power.xAxis + power.yAxis - Range.clip(position.zPosition / 20000, -0.5, 0.5), -1, 1);
            power.rightBack = Range.clip(power.xAxis - power.yAxis + Range.clip(position.zPosition / 20000, -0.5, 0.5), -1, 1);
        }
        else if (gamepad1.square) {
            power.leftFront = Range.clip(power.xAxis - Range.clip(position.yPosition / 20000, -0.5, 0.5) - power.zAxis, -1, 1);
            power.rightFront = Range.clip(power.xAxis + Range.clip(position.yPosition / 20000, -0.5, 0.5) + power.zAxis, -1, 1);
            power.leftBack = Range.clip(power.xAxis + Range.clip(position.yPosition / 20000, -0.5, 0.5) - power.zAxis, -1, 1);
            power.rightBack = Range.clip(power.xAxis - Range.clip(position.yPosition / 20000, -0.5, 0.5) + power.zAxis, -1, 1);
        }
        else if (gamepad1.triangle) {
            power.leftFront = Range.clip(Range.clip(position.xPosition / 20000, -0.5, 0.5) - power.yAxis - power.zAxis, -1, 1);
            power.rightFront = Range.clip(Range.clip(position.xPosition / 20000, -0.5, 0.5) + power.yAxis + power.zAxis, -1, 1);
            power.leftBack = Range.clip(Range.clip(position.xPosition / 20000, -0.5, 0.5) + power.yAxis - power.zAxis, -1, 1);
            power.rightBack = Range.clip(Range.clip(position.xPosition / 20000, -0.5, 0.5) - power.yAxis + power.zAxis, -1, 1);
        }
        else {
            power.leftFront = Range.clip(power.xAxis - power.yAxis - power.zAxis, -1, 1);
            power.rightFront = Range.clip(power.xAxis + power.yAxis + power.zAxis, -1, 1);
            power.leftBack = Range.clip(power.xAxis + power.yAxis - power.zAxis, -1, 1);
            power.rightBack = Range.clip(power.xAxis - power.yAxis + power.zAxis, -1, 1);
        }

        telemetry.addData("LeftFront: ", power.leftFront);
        telemetry.addData("RightFront: ", power.rightFront);
        telemetry.addData("LeftBack: ", power.leftBack);
        telemetry.addData("RightBack: ", power.rightBack);

        telemetry.addData("xPosition", position.xPosition);
        telemetry.addData("yPosition", position.yPosition);
        telemetry.addData("zPosition", position.zPosition);

        motors.LeftFront.setPower(power.leftFront);
        motors.RightFront.setPower(power.rightFront);
        motors.LeftBack.setPower(power.leftBack);
        motors.RightBack.setPower(power.rightBack);
    }
}
