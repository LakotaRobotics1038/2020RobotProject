package frc.subsystem;

import com.ctre.phoenix.motorcontrol.InvertType;

import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.TalonSRX1038;
import frc.subsystem.Storage.ManualStorageModes;

public class Shooter implements Subsystem {
    // Motor port numbers
    private final int SHOOTER_MOTOR_1_PORT = 60;
    private final int SHOOTER_MOTOR_2_PORT = 61;
    private final int TURRET_TURNING_PORT = 59;

    // Motors and encoders and sensors
    private TalonSRX1038 shooterMotor1 = new TalonSRX1038(SHOOTER_MOTOR_1_PORT);
    private TalonSRX1038 shooterMotor2 = new TalonSRX1038(SHOOTER_MOTOR_2_PORT);
    private TalonSRX1038 turretTurningMotor = new TalonSRX1038(TURRET_TURNING_PORT);
    // private Prox hardStop = new Prox(HARD_STOP_PORT);

    // Shooter
    private static Shooter shooter;

    // Turret
    private TurretDirections currentTurretDirection = TurretDirections.Left;

    public enum TurretDirections {
        Left, Right
    }

    private final static int LEFT_STOP = 220; // 114500
    private final static int RIGHT_STOP = -31;

    // Storage instance
    private Storage storage = Storage.getInstance();

    // acquisition instance
    private Acquisition acquisition = Acquisition.getInstance();

    /**
     * Returns the Shooter instance created when the robot starts
     *
     * @return Shooter instance
     */
    public static Shooter getInstance() {
        if (shooter == null) {
            System.out.println("creating a new shooter");
            shooter = new Shooter();
        }
        return shooter;
    }

    private Shooter() {
        turretTurningMotor.setSelectedSensorPosition(0);
        shooterMotor2.follow(shooterMotor1);
        shooterMotor1.setInverted(true);
        shooterMotor2.setInverted(InvertType.OpposeMaster);
    }

    /**
     * Feeds ball into shooter
     */
    public void feedBall() {
        storage.enableManualStorage(ManualStorageModes.Forward);
        acquisition.runBeaterBarFwd();
    }

    /**
     * stops feeding balls into shooter
     */
    public void noFeedBall() {
        storage.disableManualStorage();
    }

    // sets the shooter to manual speed, disabling the PID
    public void shootManually(double speed) {
        shooterMotor1.set(speed);
    }

    // pass thru the turret direction you want, then this will set the turret to
    // that
    public void setTurretDirection(TurretDirections value) {
        currentTurretDirection = value;
    }

    /**
     * Moves the turret
     *
     * @param turnSpeed
     */
    public void turnTurret(double turnSpeed) {
        turretTurningMotor.set(turnSpeed);
    }

    // get's the turret current encoder value
    public double getTurretEncoder() {
        return turretTurningMotor.getSelectedSensorPosition() * 180.00 / 82000.00; // converts radians to degrees
    }

    // gets the current shooter speed
    public double getShooterSpeed() {
        return shooterMotor1.getSelectedSensorVelocity() / 410.00;
    }

    // this sets the turret encoder position to 0
    public void resetTurretEncoder() {
        turretTurningMotor.setSelectedSensorPosition(0);
    }

    // stops the turret from moving
    private void stopTurret() {
        turretTurningMotor.set(0);
    }

    // returns the turret directions
    public TurretDirections getTurretDirection() {
        return currentTurretDirection;
    }

    public void moveTurret(double power) {
        if (power < 0 && getTurretEncoder() > RIGHT_STOP) {
            turretTurningMotor.set(power / 2);
        } else if (power > 0 && getTurretEncoder() < LEFT_STOP) {
            turretTurningMotor.set(power / 2);
        } else {
            this.stopTurret();
        }
    }
}