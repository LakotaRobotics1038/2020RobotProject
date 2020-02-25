package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//camera imports
// import edu.wpi.cscore.UsbCamera;
// import edu.wpi.first.cameraserver.CameraServer;
// TODO gyro 
// TODO match time (sam's turn)

import frc.subsystem.Shooter;

// TODO gyro 
// TODO match time (sam's turn)
public class Dashboard {
  private static Dashboard dashboard;
  private String position;
  private String autonChooser;
  private DriverStation driverStation = DriverStation.getInstance();
  private String gameData;
  private Shooter shooter = Shooter.getInstance();
  private Limelight limelight = Limelight.getInstance();

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
    SmartDashboard.putNumber("Match Time", -1);

    SmartDashboard.putNumber("Shooter Speed", 0.6);
    SmartDashboard.putNumber("Reset Turret Encoder", 0);
    SmartDashboard.putNumber("normal Increment", .1);
    SmartDashboard.putNumber("brake increment", .1);
    SmartDashboard.putNumber("Shooter Angle", 0);
  }

  public void update() {
    SmartDashboard.putNumber("Shooter Angle", shooter.getTurretEncoder());
    SmartDashboard.putNumber("Turret Encoder", shooter.getTurretEncoder());
    SmartDashboard.putBoolean("Shooter Hard Stop", shooter.getHardStop());
    SmartDashboard.putBoolean("Limelight Can See Target", limelight.canSeeTarget());
    SmartDashboard.putBoolean("Left Most?", shooter.getLeftMost());
    if (SmartDashboard.getNumber("Reset Turret Encoder", 0) == 1) {
      shooter.resetTurretEncoder();
      SmartDashboard.putNumber("Reset Turret Encoder", 0);
    }
  }
    
    public String getPosition() {
        return position;
    }

    public String getAutonChooser() {
		return autonChooser;
	}

  
}
