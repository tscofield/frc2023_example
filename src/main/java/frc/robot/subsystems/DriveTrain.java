// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  //Spark leftFront;
  //Spark rightFront;
  //Spark leftBack;
  //Spark rightBack;
  CANSparkMax leftFront;
  CANSparkMax rightFront;
  CANSparkMax leftBack;
  CANSparkMax rightBack;
  MotorControllerGroup leftMotors;
  MotorControllerGroup rightMotors;
  
  DifferentialDrive drive;

  private final AnalogInput rangeFinder ;

  /** Creates a new DriveTrain. */
  public DriveTrain() {
    /* PWM
    leftFront = new Spark(Constants.LEFT_FRONT);
    leftFront.setInverted(false);
    rightFront = new Spark(Constants.RIGHT_FRONT);
    rightFront.setInverted(false);

    leftBack = new Spark(Constants.LEFT_BACK);
    leftBack.setInverted(false);
    rightBack = new Spark(Constants.RIGHT_BACK);
    rightBack.setInverted(false);
    */

    leftFront = new CANSparkMax(Constants.LEFT_FRONT_ID,Constants.LEFT_FRONT_TYPE);
    leftFront.restoreFactoryDefaults();
    rightFront = new CANSparkMax(Constants.RIGHT_FRONT_ID,Constants.RIGHT_FRONT_TYPE);
    rightFront.restoreFactoryDefaults();
    leftBack = new CANSparkMax(Constants.LEFT_BACK_ID,Constants.LEFT_BACK_TYPE);
    leftBack.restoreFactoryDefaults();
    rightBack = new CANSparkMax(Constants.RIGHT_BACK_ID,Constants.RIGHT_BACK_TYPE);
    rightBack.restoreFactoryDefaults();


    leftMotors = new MotorControllerGroup(leftFront, leftBack);
    rightMotors = new MotorControllerGroup(rightFront, rightBack);
    drive = new DifferentialDrive(leftMotors, rightMotors);

    rangeFinder = new AnalogInput(Constants.RANGE_FINDER);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void driveWithJoysticks(XboxController controller, double speed)
  {
    drive.arcadeDrive(controller.getRawAxis(Constants.XBOX_LEFT_Y_AXIS)*speed, controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)*speed);
  }

  public void driveForward(double speed)
  {
    drive.tankDrive(speed, speed);
  }

  public boolean driveToDistance(double setPointDistance, double speed)
  {
    while(rangeFinder.getAverageVoltage() > setPointDistance)
    {
      driveForward(speed);
    }
    return true;
  }

  public void stop()
  {
    drive.stopMotor();
  }

}
