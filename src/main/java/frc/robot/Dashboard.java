package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.subsystem.Shooter;

public class Dashboard {
  private static Dashboard dashboard;

  private DriverStation driverStation = DriverStation.getInstance();
  private Shooter shooter = Shooter.getInstance();
  private Limelight limelight = Limelight.getInstance();

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

    /*-----------------------------------------------------*/
    /* Clue #11                                            */
    /* Go to the place where storage is automatically run. */
    /*-----------------------------------------------------*/
  private Dashboard() {
    SmartDashboard.putNumber("Match Time", -1);
    SmartDashboard.putNumber("Shooter Angle", 0);
    SmartDashboard.putBoolean("Prox", false);
    SmartDashboard.putNumber("Shooter speed", .55);
  }

  public void update() {
    SmartDashboard.putNumber("Shooter Angle", shooter.getTurretEncoder());
    SmartDashboard.putBoolean("Limelight Can See Target", limelight.canSeeTarget());
    SmartDashboard.putBoolean("Prox", shooter.getHardStop());
  }

  public String getPosition() {
    return position;
  }

  public String getAutonChooser() {
    return autonChooser;
  }
}
