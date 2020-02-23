/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import javax.lang.model.util.ElementScanner6;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.subsystem.PowerCell;
import frc.subsystem.Acquisition;
import frc.robot.Limelight;
import frc.subsystem.Shooter;

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

  //private CANSpark1038 test = new CANSpark1038(57, MotorType.kBrushed);

  // // Drive
  private final DriveTrain driveTrain = DriveTrain.getInstance();
  public Compressor c = new Compressor();

  //  // Joystick
  private final Joystick1038 driverJoystick = new Joystick1038(0);
  private final Joystick1038 operatorJoystick = new Joystick1038(1);
  public double multiplyer = .8;

  // // Pi Reader 
  // private final PiReader piReader = PiReader.getInstance();

  // Powercell
  private final PowerCell powerCell = PowerCell.getInstance();

  //Aquisition
  private final Acquisition acquisition = Acquisition.getInstance();
  private boolean prevButtonState = false;

  // //limelight
  // private final Limelight limelight = Limelight.getInstance();

  // shooter
  private final Shooter shooter = Shooter.getInstance();

  private final Dashboard dashboard = Dashboard.getInstance();

  // /**
  //  * This function is run when the robot is first started up and should be
  //  * used for any initialization code.
  //  */
   @Override
   public void robotInit() {
  //   piReader.initialize();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
  //   SmartDashboard.putData("Auto choices", m_chooser);
  //   limelight.initialize();
   }

  // /**
  //  * This function is called every robot packet, no matter the mode. Use
  //  * this for items like diagnostics that you want ran during disabled,
  //  * autonomous, teleoperated and test.
  //  *
  //  * <p>This runs after the mode specific periodic functions, but before
  //  * LiveWindow and SmartDashboard integrated updating.
  //  */
   @Override
   public void robotPeriodic() {
  //   piReader.readpi();
  //   powerCell.ballsPeriodic();
  //   limelight.read();
       

   }

  // /**
  //  * This autonomous (along with the chooser code above) shows how to select
  //  * between different autonomous modes using the dashboard. The sendable
  //  * chooser code works with the Java SmartDashboard. If you prefer the
  //  * LabVIEW Dashboard, remove all of the chooser code and uncomment the
  //  * getString line to get the auto name from the text box below the Gyro
  //  *
  //  * <p>You can add additional auto modes by adding additional comparisons to
  //  * the switch structure below with additional strings. If using the
  //  * SendableChooser make sure to add them to the chooser code above as well.
  //  */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  // /**
  //  * This function is called periodically during autonomous.
  //  */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {
    // TODO Auto-generated method stub
    super.teleopInit();
    //shooter.positionSpeedPIDAdjustment();
    // shooter.positionSpeedPIDAdjustment();
    // shooter.initialize();
    c.setClosedLoopControl(true);
  }
  // /**
  //  * This function is called periodically during operator control.
  //  */
   @Override
   public void teleopPeriodic() {
     operator();
     driver();
     //shooter.move();
    // powerCell.ballsPeriodic();
  }

  // /**
  //  * This function is called periodically during test mode.
  //  */
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
     else if(operatorJoystick.getRightTrigger() > .5) {
       acquisition.runBeaterBarRev();
     }
     else {
       acquisition.stopBeaterBar();
     }
     if(operatorJoystick.getYButton() && !prevButtonState) {
       acquisition.toggleAcquisitionPosition();
       prevButtonState = true;
     }
     if(!operatorJoystick.getYButton()) {
       prevButtonState = false;
     }
     if(operatorJoystick.getLeftButton()) {
      //  shooter.executeSpeedPID();
      shooter.shootManually(-SmartDashboard.getNumber("Shooter Speed", 0.5));
     }
     else {
      shooter.shootManually(0);
     }
    //  if(shooter.speedOnTarget()){
    //    operatorJoystick.setLeftRumble(1);
    //    operatorJoystick.setRightRumble(1);
    //  }
    //  else {
    //    operatorJoystick.setRightRumble(0);
    //    operatorJoystick.setLeftRumble(0);
    //  }
     if(operatorJoystick.getLeftTrigger() > .5) {
       powerCell.feedShooter(-1);
     }
     else if(operatorJoystick.getBButton()) {
      powerCell.feedShooter(-1);
    }
     else if(operatorJoystick.getXButton()) {
      powerCell.feedShooter(1);
    }
     else {
      powerCell.feedShooter(0);
    }
   }
 }

