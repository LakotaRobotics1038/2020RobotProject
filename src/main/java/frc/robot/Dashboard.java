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
    SmartDashboard.putNumber("Shooter Angle", 0);
    SmartDashboard.putBoolean("Prox", false);
    SmartDashboard.putNumber("Shooter speed", .55);
  }

  public void update() {
    SmartDashboard.putNumber("Shooter Angle", shooter.getTurretEncoder());
    SmartDashboard.putBoolean("Limelight Can See Target", limelight.canSeeTarget());
    SmartDashboard.putBoolean("Prox", shooter.getHardStop());
    // if (gameData.length() > 0) {
    //     switch (gameData.charAt(0)) {
    //     case 'B':
    //         // Blue case code
    //         SmartDashboard.putString("Blue", null);
    //         break;
    //     case 'G':
    //         // Green case code
    //         SmartDashboard.putString("Green", null);
    //         break;
    //     case 'R':
    //         // Red case code
    //         SmartDashboard.putString("Red", null);
    //         break;
    //     case 'Y':
    //         // Yellow case code
    //         SmartDashboard.putString("Yellow", null);
    //         break;
    //     default:
    //         // This is corrupt data
    //         break;
    //     }
    // } else {
    //     // Code for no data received yet
    // }
  }
    
    public String getPosition() {
        return position;
    }

    public String getAutonChooser() {
		return autonChooser;
    }
}

    
