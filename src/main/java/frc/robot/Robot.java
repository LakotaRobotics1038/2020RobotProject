/*--------------------------------------------------------*/
/* Welcome to the Controls 2021 Training Scavenger Hunt!  */
/* Presented by Courtney Lyden and Drew Friend.           */
/* You will be given clues that will lead you to specific */
/* places within the code from last year. When you get    */
/* to the correct spot, you will find the next clue.      */
/* The first clue is:                                     */
/* This button allows you to start grabbing balls.        */
/* Good luck, and may the odds be ever in your favor!     */
/*--------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;

import frc.auton.Auton;
import frc.auton.DriveAuton;
import frc.auton.Shooting3BallAuton;
import frc.auton.Shooting5BallAuton;
import frc.robot.Limelight.LEDStates;
import frc.subsystem.Storage;
import frc.subsystem.Acquisition;
import frc.subsystem.Shooter;
import frc.subsystem.DriveTrain;
import frc.subsystem.Storage.ManualStorageModes;
import frc.subsystem.Shooter.TurretDirections;

public class Robot extends TimedRobot {
  private static final String kDriveAuto = "Drive Auto";
  private static final String k3BallAuto = "3 Ball Auto";
  private static final String k5BallAuto = "5 Ball Auto";
  private static final String k8BallAuto = "8 Ball Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private CommandScheduler schedule = CommandScheduler.getInstance();
  public static SequentialCommandGroup autonPath;
  private Auton auton = new Auton();

  // Compressor
  public Compressor c = new Compressor();

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

  // limelight
  private final Limelight limelight = Limelight.getInstance();

  // shooter
  private final Shooter shooter = Shooter.getInstance();
  private double shooterSpeed = -.55;

  // dashboard
  private final Dashboard dashboard = Dashboard.getInstance();

  @Override
  public void robotInit() {
    shooter.resetTurretEncoder();
    m_chooser.setDefaultOption("Drive Auto", kDriveAuto);
    m_chooser.addOption("3 Ball Auto", k3BallAuto);
    m_chooser.addOption("5 Ball Auto", k5BallAuto);
    m_chooser.addOption("8 Ball Auto", k8BallAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  @Override
  public void robotPeriodic() {
    limelight.read();
    dashboard.update();
    System.out.println(shooter.getTurretEncoder());
  }

  @Override
  public void autonomousInit() {
    System.out.println("Auton started");
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
    switch (m_autoSelected) {
      case kDriveAuto:
        autonPath = new DriveAuton();
        System.out.println("Selected drive auton");
        break;
      case k3BallAuto:
      /*--------------------------------------------------*/
      /* Clue #5:                                         */
      /* Find the command group for "Shooting3BallAuton." */
      /*--------------------------------------------------*/
        autonPath = new Shooting3BallAuton();
        System.out.println("Selected shoot auton");
        break;
      case k5BallAuto:
        autonPath = new Shooting5BallAuton();
        System.out.println("Selected acquisition auton");
        break;
      case k8BallAuto:
        autonPath = new DriveAuton();
        System.out.println("Selected drive auton");
        break;
      default:
        System.out.println("how? just how? also why?");
        break;
    }

    schedule.schedule(autonPath);
    System.out.println("Scheduled tasks");
  }

  @Override
  public void autonomousPeriodic() {
    if (schedule != null) {
      schedule.run();
    }
  }

  @Override
  public void teleopInit() { }

  @Override
  public void teleopPeriodic() {
    operator();
    driver();
    storage.periodic();
    SmartDashboard.putNumber("Shooter speed", -shooterSpeed);
    System.out.println(shooter.isFinished());
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
    /*--------------------------------------------------------*/
    /* Clue #2:                                               */
    /* Find the definition of the "runBeaterBarFwd()" method. */
    /*--------------------------------------------------------*/
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
    } else {
      shooter.goToCrashPosition();
      limelight.changeLEDStatus(LEDStates.Off);
      prevOperatorAState = false;
    }
  }
}
