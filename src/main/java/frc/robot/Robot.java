/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.auton.AutonSelector;
import frc.auton.ForwardAuton;
import frc.auton.ShootingAuton;
import frc.auton.commands.DriveStraightCommand;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import frc.subsystem.PowerCell;
import frc.subsystem.Acquisition;
import frc.robot.Limelight;
import frc.subsystem.Shooter;
import frc.subsystem.DriveTrain;
import frc.subsystem.PowerCell.ManualStorageModes;
import frc.auton.Auton;
// import frc.subsystem.Spinner;
// import edu.wpi.first.wpilibj.util.Color;
// import edu.wpi.first.wpilibj.I2C;
// import com.revrobotics.ColorMatchResult;
// import com.revrobotics.ColorMatch;
// import frc.subsystem.Spinner;
// import com.revrobotics.ColorSensorV3;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private CommandScheduler schedule = CommandScheduler.getInstance();
  private AutonSelector autonSelector = AutonSelector.getInstance();
  public static SequentialCommandGroup autonPath;
  private Auton auton = new Auton();

  // Driver Camera
  UsbCamera visionCam = CameraServer.getInstance().startAutomaticCapture();

  // // Drive
  private final DriveTrain driveTrain = DriveTrain.getInstance();
  public Compressor c = new Compressor();
  private double prevStickValue = 0;
  private double currentStickValue = 0;
  private double normalIncrement = .1;
  private double brakeIncrement = .1;
  private double drivePower = 0;
  private int isAccelerating = 0;

  // // Joystick
  private final Joystick1038 driverJoystick = new Joystick1038(0);
  private final Joystick1038 operatorJoystick = new Joystick1038(1);
  public double multiplyer = .8;
  // private final ColorMatch colorMatcher = new ColorMatch();
  // private final I2C.Port i2cPort = I2C.Port.kOnboard;
  // private final Color kBlueMinimumTarget = ColorMatch.makeColor(0.1, 0.4, 0.4);
  // private final Color kGreenMinimumTarget = ColorMatch.makeColor(0.18, 0.5,
  // 0.2);
  // private final Color kRedMinimumTarget = ColorMatch.makeColor(0.5, 0.2, 0.05);
  // private final Color kYellowMinimumTarget = ColorMatch.makeColor(0.3, 0.45,
  // 0.05);
  // private final Color kBlueMaximumTarget = ColorMatch.makeColor(0.2, 0.5, 0.5);
  // private final Color kGreenMaximumTarget = ColorMatch.makeColor(0.28, 0.6,
  // 0.3);
  // private final Color kRedMaximumTarget = ColorMatch.makeColor(0.6, 0.3, 0.15);
  // private final Color kYellowMaximumTarget = ColorMatch.makeColor(0.4, 0.55,
  // 0.15);
  // private final Spinner spinner = Spinner.getInstance();

  // Powercell
  private final PowerCell powerCell = PowerCell.getInstance();

  // Aquisition
  private final Acquisition acquisition = Acquisition.getInstance();
  private boolean prevOperatorYState = false;
  private boolean prevOperatorAState = false;
  private boolean prevDUpState = false;
  private boolean prevDDownState = false;

  // //limelight
  private final Limelight limelight = Limelight.getInstance();

  // shooter
  private final Shooter shooter = Shooter.getInstance();
  private double shooterSpeed = -.55;

  // spinner
  // private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kMXP);
  private final Dashboard dashboard = Dashboard.getInstance();

  // /**
  // * This function is run when the robot is first started up and should be
  // * used for any initialization code.
  // */
  @Override
  public void robotInit() {
    
    shooter.resetTurretEncoder();
    DriveStraightCommand.gyroSensor.reset();
    System.out.print(DriveStraightCommand.gyroSensor.getAngle());
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Drive Straight With Shooting", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    visionCam.setExposureManual(50);
    // colorMatcher.addColorMatch(kBlueMinimumTarget);
    // colorMatcher.addColorMatch(kGreenMinimumTarget);
    // colorMatcher.addColorMatch(kRedMinimumTarget);
    // colorMatcher.addColorMatch(kYellowMinimumTarget);
    // colorMatcher.addColorMatch(kBlueMaximumTarget);
    // colorMatcher.addColorMatch(kGreenMaximumTarget);
    // colorMatcher.addColorMatch(kRedMaximumTarget);
    // colorMatcher.addColorMatch(kYellowMaximumTarget);
  }

  // /**
  // * This function is called every robot packet, no matter the mode. Use
  // * this for items like diagnostics that you want ran during disabled,
  // * autonomous, teleoperated and test.
  // *
  // * <p>This runs after the mode specific periodic functions, but before
  // * LiveWindow and SmartDashboard integrated updating.
  // */
  @Override
  public void robotPeriodic() {
    limelight.read();
    dashboard.update();
    //System.out.println("working");
    System.out.println(shooter.getTurretEncoder());
  }

  // /**
  // * This autonomous (along with the chooser code above) shows how to select
  // * between different autonomous modes using the dashboard. The sendable
  // * chooser code works with the Java SmartDashboard. If you prefer the
  // * LabVIEW Dashboard, remove all of the chooser code and uncomment the
  // * getString line to get the auto name from the text box below the Gyro
  // *
  // * <p>You can add additional auto modes by adding additional comparisons to
  // * the switch structure below with additional strings. If using the
  // * SendableChooser make sure to add them to the chooser code above as well.
  // */

  @Override
  public void autonomousInit() {
    System.out.println("Auton started");
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    switch (m_autoSelected) {
      case kCustomAuto:
        autonPath = new ShootingAuton();
        // acquisition.toggleAcquisitionPosition();
        System.out.println("Selected shoot auton");
        break;
      case kDefaultAuto:
      default:
      
        autonPath = new ShootingAuton();
        // autonPath = new ForwardAuton().select();
        // acquisition.toggleAcquisitionPosition();
        System.out.println("Selected drive auton");
        break;
    }

    schedule.schedule(autonPath);
    System.out.println("Scheduled tasks");
  }

  // /**
  // * This function is called periodically during autonomous.
  // */
  @Override
  public void autonomousPeriodic() {
    if (schedule != null) {
      schedule.run();
    }
  }

  @Override
  public void teleopInit() {
    // TODO Auto-generated method stub
    // shooter.positionSpeedPIDAdjustment();
    // shooter.positionSpeedPIDAdjustment();
    // shooter.initialize();
    c.setClosedLoopControl(true);
  }

  // /**
  // * This function is called periodically during operator control.
  // */
  @Override
  public void teleopPeriodic() {
    operator();
    driver();
    powerCell.periodic();
    // System.out.println(shooter.getShooterSpeed());
    SmartDashboard.putNumber("Shooter speed", -shooterSpeed);
    System.out.println(shooter.isFinished());
  }

  public void driver() {
    normalIncrement = SmartDashboard.getNumber("normal Increment", .1);
    brakeIncrement = SmartDashboard.getNumber("brake increment", .1);
    switch (driveTrain.currentDriveMode) {
    case tankDrive:
      driveTrain.tankDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
          driverJoystick.getRightJoystickVertical() * multiplyer);
      break;
    case dualArcadeDrive:
      /*
       * prevStickValue = currentStickValue; currentStickValue =
       * driverJoystick.getLeftJoystickVertical(); if
       * (driverJoystick.deadband(currentStickValue) == 0) { if (drivePower > 0) {
       * drivePower -= brakeIncrement; } else if (drivePower < 0) { drivePower +=
       * brakeIncrement; } } else if (currentStickValue > prevStickValue && drivePower
       * < currentStickValue) { drivePower += normalIncrement; isAccelerating = 1; }
       * else if (currentStickValue < prevStickValue && drivePower >
       * currentStickValue) { drivePower -= normalIncrement; isAccelerating = -1; }
       * else if (Math.abs(drivePower - currentStickValue) < .05) { isAccelerating =
       * 0; } else if(isAccelerating == 1) { drivePower += normalIncrement; } else if
       * (isAccelerating == -1) { drivePower -= normalIncrement; . }
       */
      if (driverJoystick.deadband(driverJoystick.getLeftJoystickVertical()) > 0) {
        drivePower = (driverJoystick.getLeftJoystickVertical() - .1) * (1 / .9);
      } else if (driverJoystick.deadband(driverJoystick.getLeftJoystickVertical()) < 0) {
        drivePower = (driverJoystick.getLeftJoystickVertical() + .1) * (1 / .9);
      } else {
        drivePower = 0;
      }
      driveTrain.dualArcadeDrive(drivePower * multiplyer, driverJoystick.getRightJoystickHorizontal() * multiplyer);
      break;
    case singleArcadeDrive:
      driveTrain.singleAracadeDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
          driverJoystick.getLeftJoystickHorizontal() * multiplyer);
      break;
    }

    if (driverJoystick.getRightButton() && driverJoystick.getRightTrigger() > .5) {
      multiplyer = 1;
      driveTrain.highGear();
    } else if (driverJoystick.getRightButton()) {
      multiplyer = 1;
      driveTrain.lowGear();
    } else if (driverJoystick.getRightTrigger() > .5) {
      multiplyer = .8;
      driveTrain.highGear();
    } else {
      multiplyer = .8;
      driveTrain.lowGear();
    }
  }

  public void operator() {
    if (operatorJoystick.getRightButton()) {
      acquisition.runBeaterBarFwd();
    } else if (operatorJoystick.getRightTrigger() > .5) {
      acquisition.runBeaterBarRev();
    } else {
      acquisition.stopBeaterBar();
    }

    if (operatorJoystick.getYButton() && !prevOperatorYState) {
      acquisition.toggleAcquisitionPosition();
      prevOperatorYState = true;
    } else if (!operatorJoystick.getYButton()) {
      prevOperatorYState = false;
    }

    // if(operatorJoystick.getBButton() && !spinner.getColorEnabled()){
    // spinner.setRotationEnabled();
    // }
    // else if(operatorJoystick.getAButton() && !spinner.getRotationEnabled()) {
    // spinner.setcolorEnabled();
    // }
    if (operatorJoystick.getBButton() && !prevDUpState) {
      shooterSpeed -= .05;
      prevDUpState = true;
    } else if (operatorJoystick.getXButton() && !prevDDownState) {
      shooterSpeed += .05;







      prevDDownState = true;
    } else if (!operatorJoystick.getXButton() && !operatorJoystick.getBButton()) {
      prevDUpState = false;
      prevDDownState = false;
    }
    if (operatorJoystick.getLeftButton()) {
      // shooter.executeSpeedPID();
      // TODO: invert shooter motors
      shooter.shootManually(shooterSpeed);
    } else {
      shooter.shootManually(0);
    }
    // if(shooter.speedOnTarget()){
    // operatorJoystick.setLeftRumble(1);
    // operatorJoystick.setRightRumble(1);
    // }
    // else {
    // operatorJoystick.setRightRumble(0);
    // operatorJoystick.setLeftRumble(0);
    // }

    if (operatorJoystick.getLeftTrigger() > .5) {
      powerCell.enableManualStorage(ManualStorageModes.Forward);
    } else if (operatorJoystick.getLeftJoystickVertical() > .5) {
      powerCell.enableManualStorage(ManualStorageModes.Forward);
    } else if (operatorJoystick.getLeftJoystickVertical() < -.5) {
      powerCell.enableManualStorage(ManualStorageModes.Reverse);
    } else {
      powerCell.disableManualStorage();
    }

    if (operatorJoystick.getAButton()) {
      if (!prevOperatorAState) {
        // TODO: make an enum
        shooter.setLeftMost(true);
        prevOperatorAState = true;
      }
      limelight.turnLEDsOn();
      shooter.move();
      // shooter.getTurretEncoder();
    } else {
      shooter.goToCrashPosition();
      limelight.turnLEDsOff();
      prevOperatorAState = false;
    }
  }
}
