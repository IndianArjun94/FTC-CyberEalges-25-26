package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOpALPHA")
public class TeleOpALPHA extends OpMode {

    private static DcMotor leftFrontMotor;
    private static DcMotor rightFrontMotor;
    private static DcMotor leftBackMotor;
    private static DcMotor rightBackMotor;
    private static DcMotor armMotor;

    private static double leftFrontSpeed;
    private static double leftBackSpeed;
    private static double rightFrontSpeed;
    private static double rightBackSpeed;

    private final static double DRIVETRAIN_MULTIPLIER = 0.5f;
    private static int armPos = armMotor.getCurrentPosition();
    private static int ARM_MAX = 100000;
    private static int ARM_MIN = -100000;
    private static boolean IS_ARM_LOCKED = false;
    private static boolean IS_ARM_UP = false;

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }


    //  Update Function that runs after every loop  //
    public static void updateDrivetrainPower() {
        leftFrontMotor.setPower(leftFrontSpeed * DRIVETRAIN_MULTIPLIER);
        leftBackMotor.setPower(leftBackSpeed * DRIVETRAIN_MULTIPLIER);
        rightBackMotor.setPower(rightBackSpeed * DRIVETRAIN_MULTIPLIER);
        rightFrontMotor.setPower(rightFrontSpeed * DRIVETRAIN_MULTIPLIER);
    }

    @Override
    public void loop() {
        double gamepad1_y = gamepad1.left_stick_y;
        double gamepad1_x = gamepad1.left_stick_x;
        double gamepad1_x2 = gamepad1.right_stick_x;

        leftFrontSpeed = 0;
        rightFrontSpeed = 0;
        leftBackSpeed = 0;
        rightBackSpeed = 0;

//        ALL CODE GOES HERE!!!!

//       Rotate
        leftBackSpeed -= gamepad1_x2;
        leftFrontSpeed -= gamepad1_x2;
        rightFrontSpeed += gamepad1_x2;
        rightBackSpeed += gamepad1_x2;

//       Forward/Backward
        leftFrontSpeed += gamepad1_y;
        leftBackSpeed += gamepad1_y;
        rightFrontSpeed += gamepad1_y;
        rightBackSpeed += gamepad1_y;

//       Lateral
        leftFrontSpeed -= gamepad1_x;
        leftBackSpeed += gamepad1_x;
        rightFrontSpeed += gamepad1_x;
        rightBackSpeed -= gamepad1_x;

//      Arm code        //
        if(gamepad1.y){
            if(!IS_ARM_UP){
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setTargetPosition(ARM_MAX);
                armMotor.setPower(.5f);
            }
            IS_ARM_UP = true;
            IS_ARM_LOCKED = false;
        } else if (gamepad1.a) {
            if(IS_ARM_UP || IS_ARM_LOCKED){
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setTargetPosition(ARM_MIN);
                armMotor.setPower(.5f);
            }
            IS_ARM_LOCKED = false;
            IS_ARM_UP = false;
        } else {
            if (IS_ARM_UP || !IS_ARM_LOCKED) {
                armPos = armMotor.getCurrentPosition();
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setTargetPosition(armPos);
                armMotor.setPower(.5f);
            }

            IS_ARM_LOCKED = true;
            IS_ARM_UP = false;
        }

        telemetry.addData("Arm Pos: ", armPos);
        telemetry.update();
//        Update Drivetrain Power
        updateDrivetrainPower();
    }
}
