package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.subsystem.Shooter;

// TODO gyro
// TODO match time (sam's turn)
public class Dashboard {
    private static Dashboard dashboard;

    private Shooter shooter = Shooter.getInstance();
    private Limelight limelight = Limelight.getInstance();

    private String position;
    private String autonChooser;

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
        // visionCam.setExposureManual(CAMERA_EXPOSURE);
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
