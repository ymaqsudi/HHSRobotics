package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
// http://www.copperliarrobotics.com/

@Autonomous(name="Autonomous", group="Linear OpMode")
//@Disabled
public class BasicOpMode_Auto extends LinearOpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    static final int MOTOR_TICK_COUNTS = 1120;
    private double circumference = 3.141592653589793238462643383 * 4;   // PI * diameter

    @Override
    public void runOpMode() throws InterruptedException
    {
        // Hardware initiation
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");

        // Set motor direction
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);

        // set right motor to run with an encoder.
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // wait for start button.
        waitForStart();

        /**
         * MAIN OBJECTIVES:
         *  - Find the skystones, transport them FROM the loading zone TO the building zone.
         *
         *  **** You DO NOT need the robot or the physical game field to create autonomous.
         *       Instead, call the movement functions defined in this class with those measurements (inches)
         *       from the FTC Skystone Manual Guide #2.
         *
         */

        /**
         * MOVE FORWARD TO BLOCKS
         * distance (in):
         */

        /**
         * SEARCH FOR SKYSTONE
         * distance (in)
         */

        /**
         * PICKUP SKYSTONE
         */

        /**
         * MOVE FROM LOADING AREA TO BUILDING AREA
         * distance (in)
         */

        /**
         * DROP SKYSTONE INTO BUILDING AREA
         */

        /**
         * RETURN TO LOADING AREA
         * distance (in)
         *  ***** Use pictures along the walls to position robot into the corner of loading zone.
         *       Now that the robot is oriented in a predictable spot, we can naviagate using VuForia/sensors
         *
         *  **** This spot is presumably different from the initial starting position. Account for this change,
         *        or reposition the robot at this time to return to the starting position.
         */

        // REPEAT ...

        /*
        strafeLeft(convertInchesToCM(10),.25);
        strafeRight(convertInchesToCM(10),.25);

        forward(convertInchesToCM(25),.25);
        rotateCounterClockwise(convertInchesToCM(25), .25);
        rotateClockwise(convertInchesToCM(25), .25);
        backward(convertInchesToCM(25), .25);
        */

        // Stop motors after the job is done
        backLeft.setPower(0);
        backRight.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);

    }



    // Methods
    public double convertInchesToCM(double in) {
        return in * 2.54;
    }



    /*
    The following 6 methods only take one parameter (speed). We made this in order to call
    these methods later on when we make better methods using encoders.
     */
    public void rotate(double motorSpeed) {
        backLeft.setPower(motorSpeed);
        backRight.setPower(motorSpeed);
        frontLeft.setPower(-motorSpeed);
        frontRight.setPower(-motorSpeed);
    }


    public void driveOrReverse(double motorSpeed) {
        backLeft.setPower(motorSpeed);
        backRight.setPower(motorSpeed);
        frontLeft.setPower(motorSpeed);
        frontRight.setPower(motorSpeed);
    }

    public void strafe(double motorSpeed) {
        backLeft.setPower(motorSpeed);
        backRight.setPower(-motorSpeed);
        frontLeft.setPower(-motorSpeed);
        frontRight.setPower(motorSpeed);
    }

    /**
     * Reset encoders
     */
    public void resetEncoders() {
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Run to Target position
     */
    public void runToTargetPosition() {
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Run to Target position
     */
    public void specifyTarget(int encoderDrivingTarget) {
        backLeft.setTargetPosition(encoderDrivingTarget);
        backRight.setTargetPosition(encoderDrivingTarget);
        frontLeft.setTargetPosition(encoderDrivingTarget);
        frontRight.setTargetPosition(encoderDrivingTarget);
    }

    public void allowMotorsToFinish() {
        while (frontLeft.isBusy() & frontRight.isBusy() & backLeft.isBusy() & backRight.isBusy()) {
            // stop doing stuff when the robot is driving
            telemetry.addData("Path", "Driving 18 inches");
            telemetry.update();
        }
    }
    /*
    These methods are the encoder methods. They calculate the driving target using the desiredDistance
    parameter value, and call upon the methods listed above in order to invoke the speed method
     */
    public void forward(double desiredDistance, double speed) {
        // Calculations for all the variables we use in this method
        double rotationsNeeded = (desiredDistance*0.23778)/circumference;     // You may need to multiply
        int encoderDrivingTarget = (int)(rotationsNeeded*MOTOR_TICK_COUNTS); // rotations needed * tick count


        // Resets encoders before use. You need to do this to clear any values the encoders might
        // have stored.
        resetEncoders();

        // Tells the motors to drive to the specific target, a variable that was defined in the
        // beginning of the program. It is made using the parameter values given.
        specifyTarget(encoderDrivingTarget);

        // calls on one of the primitive methods to move the wheels at the speed of the second parameter
        driveOrReverse(speed);

        // Built in methods that tell the motor to run to the target position
        runToTargetPosition();

        // While the motors are moving, don't do anything except telemetry
        allowMotorsToFinish();

    }

    public void backward(double desiredDistance, double speed) {
        double rotationsNeeded = (desiredDistance*0.23778)/circumference;
        int encoderDrivingTarget = (int)(rotationsNeeded*MOTOR_TICK_COUNTS); // rotations needed * tick count

        resetEncoders();

        specifyTarget(-encoderDrivingTarget);

        driveOrReverse(speed);

        runToTargetPosition();

       allowMotorsToFinish();

    }

    public void strafeRight(double desiredDistance, double speed) {
        double rotationsNeeded = (desiredDistance*0.23778)/circumference;
        int encoderDrivingTarget = (int)(rotationsNeeded*MOTOR_TICK_COUNTS); // rotations needed * tick count

        resetEncoders();

        backLeft.setTargetPosition(-encoderDrivingTarget);
        backRight.setTargetPosition(encoderDrivingTarget);
        frontLeft.setTargetPosition(encoderDrivingTarget);
        frontRight.setTargetPosition(-encoderDrivingTarget);

        strafe(speed);

        runToTargetPosition();

        allowMotorsToFinish();

    }

    public void strafeLeft(double desiredDistance, double speed) {
        double rotationsNeeded = (desiredDistance*0.23778)/circumference;
        int encoderDrivingTarget = (int)(rotationsNeeded*MOTOR_TICK_COUNTS); // rotations needed * tick count

        resetEncoders();

        backLeft.setTargetPosition(encoderDrivingTarget);
        backRight.setTargetPosition(-encoderDrivingTarget);
        frontLeft.setTargetPosition(-encoderDrivingTarget);
        frontRight.setTargetPosition(encoderDrivingTarget);

        strafe(-speed);

        runToTargetPosition();

       allowMotorsToFinish();

    }

    public void rotateClockwise(double desiredDistance, double speed) {
        double rotationsNeeded = (desiredDistance*0.23778)/circumference;
        int encoderDrivingTarget = (int)(rotationsNeeded*MOTOR_TICK_COUNTS); // rotations needed * tick count

        resetEncoders();

        backLeft.setTargetPosition(-encoderDrivingTarget);
        backRight.setTargetPosition(encoderDrivingTarget);
        frontLeft.setTargetPosition(-encoderDrivingTarget);
        frontRight.setTargetPosition(encoderDrivingTarget);

        rotate(speed);

        runToTargetPosition();

       allowMotorsToFinish();

    }

    public void rotateCounterClockwise(double desiredDistance, double speed) {
        double rotationsNeeded = (desiredDistance*0.23778)/circumference;
        int encoderDrivingTarget = (int)(rotationsNeeded*MOTOR_TICK_COUNTS); // rotations needed * tick count

        resetEncoders();

        backLeft.setTargetPosition(encoderDrivingTarget);
        backRight.setTargetPosition(-encoderDrivingTarget);
        frontLeft.setTargetPosition(encoderDrivingTarget);
        frontRight.setTargetPosition(-encoderDrivingTarget);

        rotate(-speed);

        runToTargetPosition();

        allowMotorsToFinish();

    }

}