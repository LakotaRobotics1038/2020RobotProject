/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.auton.AutonSelector;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import javax.lang.model.util.ElementScanner6;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.Compressor;
import frc.subsystem.PowerCell;
import frc.subsystem.Acquisition;
import frc.robot.Limelight;
import frc.subsystem.Shooter;
import frc.subsystem.DriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //Auton Variables
  public static SendableChooser<String> autoChooser = new SendableChooser<>();
  public static SendableChooser<String> startPosition = new SendableChooser<>();
  private SequentialCommandGroup autonPath;
  private AutonSelector autonSelector = AutonSelector.getInstance();
  private CommandScheduler schedule = CommandScheduler.getInstance();
  // Drive
   private final DriveTrain driveTrain = DriveTrain.getInstance();
   public Compressor c = new Compressor();

   // Joystick
   private final Joystick1038 driverJoystick = new Joystick1038(0);
   private final Joystick1038 operatorJoystick = new Joystick1038(1);
   public double multiplyer;

  // Pi Reader 

  // Powercell
  private final PowerCell powerCell = PowerCell.getInstance();

  //Aquisition
  private final Acquisition acquisition = Acquisition.getInstance();

  //limelight
  private final Limelight limelight = Limelight.getInstance();

  //shooter
  private final Shooter shooter = Shooter.getInstance();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    limelight.initialize();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
   shooter.periodic();
    powerCell.ballsPeriodic();
    limelight.read();

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    autonPath = autonSelector.chooseAuton();
		schedule.schedule(autonPath);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if(schedule != null) {
			schedule.run();
		}
  }

  @Override
  public void teleopInit() {
    // TODO Auto-generated method stub
    super.teleopInit();
    shooter.positionSpeedPIDAdjustment();
    shooter.initialize();
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    shooter.executeAimPID();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
   public void driver() {
 	switch (driveTrain.currentDriveMode) {
       case tankDrive:
         driveTrain.tankDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
             driverJoystick.getRightJoystickVertical() * multiplyer);
         break;
       case dualArcadeDrive:
         driveTrain.dualArcadeDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
             driverJoystick.getRightJoystickHorizontal() * multiplyer);
         break;
       case singleArcadeDrive:
         driveTrain.singleAracadeDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
             driverJoystick.getLeftJoystickHorizontal() * multiplyer);
         break;
     }
   } 
   public void operator() {
     if(operatorJoystick.getRightButton()) {
       acquisition.runBeaterBarFwd();
     }
    //  else if(operatorJoystick.getRightTrigger() > .5) {
    //    acquisition.runBeaterBarRev();
    //  }
     else {
       acquisition.stopBeaterBar();
     }
     if(operatorJoystick.getYButton()) {
       acquisition.toggleAcquisitionPosition();
     }
     if(operatorJoystick.getLeftButton()) {
       shooter.executeSpeedPID();
     }
     else {
       shooter.disablePID();
     }
     if(operatorJoystick.getLeftTrigger() > .5) {
       powerCell.feedShooter(.5);
     }
     else {
       powerCell.feedShooter(0);
     }
   }
 }

