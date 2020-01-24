/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import frc.subsystem.Spinner;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import frc.subsystem.Spinner;

import com.revrobotics.ColorSensorV3;

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
  private final ColorMatch colorMatcher = new ColorMatch();
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final Color kBlueMinimumTarget = ColorMatch.makeColor(0.1, 0.4, 0.4);
  private final Color kGreenMinimumTarget = ColorMatch.makeColor(0.18, 0.5, 0.2);
  private final Color kRedMinimumTarget = ColorMatch.makeColor(0.5, 0.2, 0.05);
  private final Color kYellowMinimumTarget = ColorMatch.makeColor(0.3, 0.45, 0.05);
  private final Color kBlueMaximumTarget = ColorMatch.makeColor(0.2, 0.5, 0.5);
  private final Color kGreenMaximumTarget = ColorMatch.makeColor(0.28, 0.6, 0.3);
  private final Color kRedMaximumTarget = ColorMatch.makeColor(0.6, 0.3, 0.15);
  private final Color kYellowMaximumTarget = ColorMatch.makeColor(0.4, 0.55, 0.15);
  private final Spinner spinner = Spinner.getInstance();

  // Drive
//   private final DriveTrain driveTrain = DriveTrain.getInstance();
  //public Compressor c = new Compressor();

   // Joystick
//    private final Joystick1038 driverJoystick = new Joystick1038(0);
//    private final Joystick1038 operatorJoystick = new Joystick1038(1);
//    public double multiplyer;

//    // Pi Reader 
//     private final PiReader piReader = PiReader.getInstance();

    //Spinner
 //   private final Spinner spinner = Spinner.getInstance();
    private final ColorSensorV3 colorSensor = new ColorSensor1038(i2cPort);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // piReader.initialize();
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);
    // colorMatcher.addColorMatch(kBlueMinimumTarget);
    // colorMatcher.addColorMatch(kGreenMinimumTarget);
    // colorMatcher.addColorMatch(kRedMinimumTarget);
    // colorMatcher.addColorMatch(kYellowMinimumTarget);
    // colorMatcher.addColorMatch(kBlueMaximumTarget);
    // colorMatcher.addColorMatch(kGreenMaximumTarget);
    // colorMatcher.addColorMatch(kRedMaximumTarget);
    // colorMatcher.addColorMatch(kYellowMaximumTarget);

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
    //piReader.readpi();

    Color detectedColor = colorSensor.getColor();
    System.out.println(spinner.getCurrentColor());
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
    // m_autoSelected = m_chooser.getSelected();
    // // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    // System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
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

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
//   public void driver() {
// 	switch (driveTrain.currentDriveMode) {
//       case tankDrive:
//         driveTrain.tankDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
//             driverJoystick.getRightJoystickVertical() * multiplyer);
//         break;
//       case dualArcadeDrive:
//         driveTrain.dualArcadeDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
//             driverJoystick.getRightJoystickHorizontal() * multiplyer);
//         break;
//       case singleArcadeDrive:
//         driveTrain.singleAracadeDrive(driverJoystick.getLeftJoystickVertical() * multiplyer,
//             driverJoystick.getLeftJoystickHorizontal() * multiplyer);
//         break;
//     }
//   } 
// public void operator() {
//     if(operatorJoystick.getBButton() && !spinner.getColorEnabled()){
//         spinner.setRotationEnabled();
//     }
//     else if(operatorJoystick.getAButton() && !spinner.getRotationEnabled()) {
//         spinner.setcolorEnabled();   
//     }
// }
}

