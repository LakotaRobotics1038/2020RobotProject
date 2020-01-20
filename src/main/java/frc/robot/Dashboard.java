package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//camera imports
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.PiReader;

// Gyro imports
//import org.usfirst.frc.team1038.robot.I2CGyro;
// TODO match time (sam's turn)
// TODO limelight
public class Dashboard {
    private static Dashboard dashboard;
    // TODO make sam fix private ArduinoReader arduinoReader =
    // ArduinoReader.getInstance();
    private DriverStation driverStation = DriverStation.getInstance();
    private String gameData;
    private PiReader piReader = PiReader.getInstance();

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
        // matchtime
        SmartDashboard.putNumber("Match Time", -1);
        // Camera
        UsbCamera visionCam = CameraServer.getInstance().startAutomaticCapture();
        // Gyro
        // putBoolean("Controls/Reset Gyro", false);
        // putBoolean("Controls/Reset Gyro", false);
    }

    public void update() {
        // TODO make sam fix SmartDashboard.putNumber("Left Distance",
        // arduinoReader.getFrontLeftLaserVal());
        // TODO make sam fix SmartDashboard.putNumber("Right Distance",
        // arduinoReader.getFrontRightLaserVal());
        SmartDashboard.putNumber("Match Time", driverStation.getMatchTime());
        // putNumber("Gyro", I2CGyro.getInstance().getAngle());
        // putData("Drivers/Gyro", I2CGyro.getInstance());
        // color displayed
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            switch (gameData.charAt(0)) {
            case 'B':
                // Blue case code
                SmartDashboard.putString("Blue", null);
                break;
            case 'G':
                // Green case code
                SmartDashboard.putString("Green", null);
                break;
            case 'R':
                // Red case code
                SmartDashboard.putString("Red", null);
                break;
            case 'Y':
                // Yellow case code
                SmartDashboard.putString("Yellow", null);
                break;
            default:
                // This is corrupt data
                break;
            }
        } else {
            // Code for no data received yet
        }

        // SmartDashboard.putBoolean("First ball", piReader.getFirstBallSwitchVal());

        // SmartDashboard.putBoolean("Second ball", piReader.getFirstBallSwitchVal());

        // SmartDashboard.putBoolean("Third ball", piReader.getFirstBallSwitchVal());

        // SmartDashboard.putBoolean("Fourth ball", piReader.getFirstBallSwitchVal());

        // SmartDashboard.putBoolean("Fifth ball", piReader.getFirstBallSwitchVal());

    }

}
