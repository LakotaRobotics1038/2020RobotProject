package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//camera imports
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
// TODO gyro 
// TODO match time (sam's turn)
public class Dashboard {
    private static Dashboard dashboard;
    // TODO make sam fix private ArduinoReader arduinoReader = ArduinoReader.getInstance();
    private DriverStation driverStation = DriverStation.getInstance(); 
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
            SmartDashboard.putNumber("Match Time", -1);
            
            UsbCamera visionCam = CameraServer.getInstance().startAutomaticCapture();
            SmartDashboard.putNumber("Shooter Speed", 0.5);
            SmartDashboard.getNumber("Shooter Speed", 0.5);
    }

    public void periodic() {
    //     // TODO make sam fix SmartDashboard.putNumber("Left Distance", arduinoReader.getFrontLeftLaserVal());
    //     // TODO make sam fix SmartDashboard.putNumber("Right Distance", arduinoReader.getFrontRightLaserVal());
    //     SmartDashboard.putNumber("Match Time", driverStation.getMatchTime());
    //     // color displayed   
    //     gameData = DriverStation.getInstance().getGameSpecificMessage();
    //     if(gameData.length() > 0) {
    //         switch (gameData.charAt(0)) {
    //             case 'B' :
    //             //Blue case code
    //             SmartDashboard.putString("Blue", null);
    //             break;
    //             case 'G' :
    //             //Green case code
    //             SmartDashboard.putString("Green", null);
    //             break;
    //             case 'R' :
    //             //Red case code
    //             SmartDashboard.putString("Red", null);
    //             break;
    //             case 'Y' :
    //             //Yellow case code
    //             SmartDashboard.putString("Yellow", null);
    //             break;
    //             default :
    //             //This is corrupt data
    //             break;
    //         }
    //     } 
    //     else {
    //         //Code for no data received yet
    //     }
    }

  
}




