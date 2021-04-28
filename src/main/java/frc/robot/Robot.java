/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;

import frc.auton.Auton;
import frc.auton.DriveAuton;
import frc.auton.GalacticCommands;
import frc.auton.GalacticCommands2;
import frc.robot.Gyro1038;
import frc.auton.Shooting3BallAuton;
import frc.auton.Shooting5BallAuton;
import frc.robot.Limelight.LEDStates;
import frc.subsystem.Storage;
import frc.subsystem.Acquisition;
import frc.subsystem.Shooter;
import frc.subsystem.DriveTrain;
import frc.subsystem.Endgame;
import frc.subsystem.Storage.ManualStorageModes;
import frc.subsystem.Shooter.TurretDirections;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDriveAuto = "Drive Auto";
  private static final String k3BallAuto = "3 Ball Auto";
  private static final String k5BallAuto = "5 Ball Auto";
  private static final String k8BallAuto = "8 Ball Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static CommandScheduler schedule = CommandScheduler.getInstance();
  public static SequentialCommandGroup autonPath;
  private final Gyro1038 gyro = Gyro1038.getInstance();
  private Auton auton = new Auton();

  // Compressor
  public Compressor c = new Compressor();

  //Endgame
  //public Endgame endgame = new Endgame();
  private final Endgame endgame = Endgame.getInstance();
  //private boolean prevXState = false;

  // Drive
  private final DriveTrain driveTrain = DriveTrain.getInstance();
  private double drivePower = 0;

  // Joystick
  private final Joystick1038 driverJoystick = new Joystick1038(0);
  private final Joystick1038 operatorJoystick = new Joystick1038(1);
  public double multiplyer = .8;

  // Storage
  private final Storage storage = Storage.getInstance();

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

  /**
  * This function is run when the robot is first started up and should be
  * used for any initialization code.
  */
  @Override
  public void robotInit() {
    shooter.resetTurretEncoder();
    gyro.calibrate();
    m_chooser.setDefaultOption("Drive Auto", kDriveAuto);
    m_chooser.addOption("3 Ball Auto", k3BallAuto);
    m_chooser.addOption("5 Ball Auto", k5BallAuto);
    m_chooser.addOption("8 Ball Auto", k8BallAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
  * This function is called every robot packet, no matter the mode. Use
  * this for items like diagnostics that you want ran during disabled,
  * autonomous, teleoperated and test.
  */
  @Override
  public void robotPeriodic() {
    limelight.read();
    dashboard.update();
    // System.out.println(limelight.getYOffset());
    //System.out.println(shooter.getShooterSpeed());
  }

  /**
  * This autonomous (along with the chooser code above) shows how to select
  * between different autonomous modes using the dashboard. The sendable
  * chooser code works with the Java SmartDashboard. If you prefer the
  * LabVIEW Dashboard, remove all of the chooser code and uncomment the
  * getString line to get the auto name from the text box below the Gyro
  */
  @Override
  public void autonomousInit() {
    //autonPath = new GalacticCommands2();
    autonPath = new GalacticCommands();
    // System.out.println("Auton started");
    // m_autoSelected = m_chooser.getSelected();
    // System.out.println("Auto selected: " + m_autoSelected);
    // switch (m_autoSelected) {
    //   case kDriveAuto:
    //     autonPath = new DriveAuton();
    //     System.out.println("Selected drive auton");
    //     break;
    //   case k3BallAuto:
    //     autonPath = new Shooting3BallAuton();
    //     System.out.println("Selected shoot auton");
    //     break;
    //   case k5BallAuto:
    //     autonPath = new Shooting5BallAuton();
    //     System.out.println("Selected acquisition auton");
    //     break;
    //   case k8BallAuto:
    //     autonPath = new DriveAuton();
    //     System.out.println("Selected drive auton");
    //     break;
    //   default:
    //     System.out.println("how? just how? also why?");
    //     break;
    //}

    schedule.schedule(autonPath);
    System.out.println("Scheduled tasks");
  }

  /**
  * This function is called periodically during autonomous.
  */
  @Override
  public void autonomousPeriodic() {
    if (schedule != null) {
      schedule.run();
    }
  }

  @Override
  public void teleopInit() { }

  /**
  * This function is called periodically during operator control.
  */
  @Override
  public void teleopPeriodic() {
    operator();
    driver();
    endgame.periodic();
    storage.periodic();
    SmartDashboard.putNumber("Shooter speed", -shooterSpeed);
    //System.out.println(shooter.isFinished());
  }

  public void driver() {
    switch (driveTrain.currentDriveMode) {
      case tankDrive:
        driveTrain.tankDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
            driverJoystick.getRightJoystickVertical() * multiplyer);
        break;
      case dualArcadeDrive:
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

    if (operatorJoystick.getLeftButton()) {
      shooter.executeSpeedPID();
    } else {
      shooter.disableSpeedPID();
      shooter.shootManually(0);
    }
    if(shooter.isFinished() && operatorJoystick.getLeftButton()){
      operatorJoystick.setLeftRumble(1);
      operatorJoystick.setRightRumble(1);
    }
    else {
      operatorJoystick.setRightRumble(0);
      operatorJoystick.setLeftRumble(0);
    }

    if (operatorJoystick.getLeftTrigger() > .5) {
      shooter.feedBall();
    } else if (operatorJoystick.getLeftJoystickVertical() > .5) {
      storage.enableManualStorage(ManualStorageModes.Forward);
    } else if (operatorJoystick.getLeftJoystickVertical() < -.5) {
      storage.enableManualStorage(ManualStorageModes.Reverse);
    } else {
      storage.disableManualStorage();
    }

    if(operatorJoystick.getBButton() || driverJoystick.getBButton()) {
      shooter.holdPosition();
    }
    else if (operatorJoystick.getAButton()) {
      if (!prevOperatorAState) {
        shooter.setTurretDirection(TurretDirections.Left);
        prevOperatorAState = true;
      }
      limelight.changeLEDStatus(LEDStates.On);
      shooter.move();
    } 
    else {
      shooter.goToCrashPosition();
      limelight.changeLEDStatus(LEDStates.Off);
      prevOperatorAState = false;
    }
    
    if (operatorJoystick.getXButton()) {
      endgame.onButton();
    }
  }
}
