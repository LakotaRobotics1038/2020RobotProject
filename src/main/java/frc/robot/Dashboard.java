package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Limelight;

import frc.subsystem.Shooter;

// TODO gyro 
// TODO match time (sam's turn)
public class Dashboard {
  private static Dashboard dashboard;

  private DriverStation driverStation = DriverStation.getInstance();
  private Shooter shooter = Shooter.getInstance();
  //private Limelight limelight = Limelight.getInstance();
  //private UsbCamera visionCam = CameraServer.getInstance().startAutomaticCapture();

  private final int CAMERA_EXPOSURE =  50;

  private String position;
  private String autonChooser;
  private String gameData;

  public static Dashboard getInstance() {
    if (dashboard == null) {
      System.out.println("Creating a new Dashboard");
      dashboard = new Dashboard();
    }
    return dashboard;
  }

  /**
   * Instantiates dashboard object
   */
  private Dashboard() {
    //visionCam.setExposureManual(CAMERA_EXPOSURE);
    SmartDashboard.putNumber("Match Time", -1);
    SmartDashboard.putNumber("Shooter Angle", 0);
    SmartDashboard.putBoolean("Prox", false);
    SmartDashboard.putNumber("Shooter speed", .55);
    SmartDashboard.putNumber("Shooter Multiplier", -.005);
  }

  public void update() {
    SmartDashboard.putNumber("Shooter Angle", shooter.getTurretEncoder());
    //SmartDashboard.putBoolean("Limelight Can See Target", limelight.canSeeTarget());
    //SmartDashboard.putBoolean("Prox", shooter.getHardStop());
  }

  public String getPosition() {
    return position;
  }

  public String getAutonChooser() {
    return autonChooser;
  }
  public Double getLimeBase() {
    return SmartDashboard.getNumber("Shooter speed", .55);
  }

  public Double getLimeMultiplier() {
    return SmartDashboard.getNumber("Shooter Multipier", -.005);
  }

}
