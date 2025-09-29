package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Motor Encoder Test")
public class MotorEncoderTest extends OpMode {
    private static DcMotor leftFrontMotor;
    private static DcMotor rightFrontMotor;
    private static DcMotor leftBackMotor;
    private static DcMotor rightBackMotor;

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBack");

        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        if (gamepad1.x) {
            leftBackMotor.setPower(gamepad1.left_trigger);
        } if (gamepad1.b) {
            rightBackMotor.setPower(gamepad1.left_trigger);
        } if (gamepad1.y) {
            leftFrontMotor.setPower(gamepad1.left_trigger);
        } if (gamepad1.a) {
            rightFrontMotor.setPower(gamepad1.left_trigger);
        }

        telemetry.addData("Front Left: ", leftFrontMotor.getCurrentPosition());
        telemetry.addData("Front Right: ", rightFrontMotor.getCurrentPosition());
        telemetry.addData("Back Left: ", leftBackMotor.getCurrentPosition());
        telemetry.addData("Back Right: ", rightBackMotor.getCurrentPosition());
    }
}
