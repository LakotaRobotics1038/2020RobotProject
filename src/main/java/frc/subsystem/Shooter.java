package frc.subsystem;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.Limelight;
import frc.robot.Prox;
import frc.robot.TalonSRX1038;
import frc.subsystem.PowerCell;

public class Shooter implements Subsystem {
    // motor port numbers
    private static boolean isEnabled = false;
    private final int SHOOTER_MOTOR_1_PORT = 60;
    private final int SHOOTER_MOTOR_2_PORT = 61;
    private final int TURRET_TURNING_PORT = 59;
    private final int HARD_STOP_PORT = 0;

    // motors and encoders and sensors
    private TalonSRX1038 shooterMotor1 = new TalonSRX1038(SHOOTER_MOTOR_1_PORT);
    private TalonSRX1038 shooterMotor2 = new TalonSRX1038(SHOOTER_MOTOR_2_PORT);
    private TalonSRX1038 turretTurningMotor = new TalonSRX1038(TURRET_TURNING_PORT);
    private Prox hardStop = new Prox(HARD_STOP_PORT);

    // Shooter
    private static Shooter shooter;

    // swerves
    private boolean leftMost = false;
    private final static int RIGHT_STOP = 111000; // 114500
    private final static int LEFT_STOP = -14200;
    private static double swivelSpeed = 0.2;

    // Limelight instance
    private Limelight limelight = Limelight.getInstance();

    // PowerCell instance
    private PowerCell powerCell = PowerCell.getInstance();

    // position PID for turret
    private final double positionSetpoint = 0.0;
    private final double positionTolerance = .05;
    private final static double positionP = 0.055; // .15
    private final static double positionI = 0.0;
    private final static double positionD = 0.0;
    private PIDController positionPID = new PIDController(positionP, positionI, positionD);

    // speed PID for shooter
    private final double speedSetpoint = 0.0;
    private final double speedTolerance = 0.0;
    private final static double speedP = 0.0;
    private final static double speedI = 0.0;
    private final static double speedD = 0.0;
    private PIDController speedPID = new PIDController(speedP, speedI, speedD);

    // motor speed for shooter feeder
    private final static double feedSpeed = 1;

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
        // turretTurningMotor.setSelectedSensorPosition(0);
        // positionPID.setPID(positionP, positionI, positionD);
        positionPID.setSetpoint(positionSetpoint);
        positionPID.setTolerance(positionTolerance);
        positionPID.disableContinuousInput();

        // speedPID.setPID(speedP, speedI, speedD);
        speedPID.setSetpoint(speedSetpoint);
        speedPID.setTolerance(speedTolerance);
        speedPID.disableContinuousInput();
    }

    /**
     * Feeds ball into shooter
     */
    public void feedBall() {
        // if (speedPID.atSetpoint()) {
        powerCell.feedShooter(feedSpeed);
        // }
    }

    /**
     * stops feeding balls into shooter
     */
    // public void noFeedBall() {
    // powerCell.feedShooter(0);
    // }

    /**
     * disables speed motors and pid
     */
    public void disablePID() {
        speedPID.calculate(0.0);// come back to that
        shooterMotor1.set(0);
        shooterMotor2.set(0);
    }

    /**
     * sets the position setpoint
     */
    public void initialize() {
        positionPID.setSetpoint(positionSetpoint);

    }

    /**
     * aims turret towards target
     */
    public void executeAimPID() {
        // System.out.println("PID");
        double power = positionPID.calculate(limelight.getXOffset());
        turretTurningMotor.set(power * 0.5);
    }

    /**
     * sets the speed of the shooter
     */
    public void executeSpeedPID() {
        shooterMotor1.set(speedPID.calculate(shooterMotor1.getSelectedSensorVelocity()));
        shooterMotor2.set(speedPID.calculate(shooterMotor1.getSelectedSensorVelocity()));
    }

    public boolean speedOnTarget() {
        return speedPID.atSetpoint();
    }

    public void shootManually(double speed) {
        shooterMotor1.set(speed);
        shooterMotor2.set(-speed);
    }

    public void setLeftMost(boolean value) {
        leftMost = value;
    }

    public void enable() {
        isEnabled = true;
    }

    public void disable() {
        isEnabled = false;
    }

    public void periodic() {
        if(isEnabled) {
            executeAimPID();
            executeSpeedPID();
        }
    }
    /**
     * stops and resets PID values if interrupted (potentially unnecessary)
     * 
     * @param interrupted if the robot is interrupted
     */
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("position interrupted");
        }
        positionPID.reset();
        speedPID.reset();
    }

    /**
     * decides whether the robot is ready to shoot
     * 
     * @return returns if robot is ready to shoot
     */
    public boolean isFinished() {
        return positionPID.atSetpoint() && speedPID.atSetpoint();
    }

    /**
     * limits shooter turn radius
     */
    public void swivelEy() {
        if (leftMost) {
            turretTurningMotor.set(swivelSpeed);
        } else {
            turretTurningMotor.set(-swivelSpeed);
        }
    }

    public int getTurretEncoder() {
        return turretTurningMotor.getSelectedSensorPosition();
    }

    public int getShooterSpeed() {
        return shooterMotor1.getSelectedSensorPosition
        ();
    }

    public boolean getHardStop() {
        return hardStop.get();
    }

    public void resetTurretEncoder() {
        turretTurningMotor.setSelectedSensorPosition(0);
    }

    private void stopTurret() {
        turretTurningMotor.set(0);
    }

    public boolean getLeftMost() {
        return leftMost;
    }

    public void goToCrashPosition() {
        if (Math.abs(turretTurningMotor.getSelectedSensorPosition()) < 1000) {
            stopTurret();
        } else if (turretTurningMotor.getSelectedSensorPosition() > 0) {
            leftMost = false;
            swivelEy();
        } else if (turretTurningMotor.getSelectedSensorPosition() < 0) {
            leftMost = true;
            swivelEy();
        }
    }

    public void move() {
        if (hardStop.get()) {
        turretTurningMotor.setSelectedSensorPosition(0);
        System.out.println("i see prox");
        }
        if (turretTurningMotor.getSelectedSensorPosition() <= LEFT_STOP) {
            leftMost = true;
            swivelEy();
        } else if (turretTurningMotor.getSelectedSensorPosition() >= RIGHT_STOP) {
            leftMost = false;
            swivelEy();
        } else if (limelight.canSeeTarget()) {
            executeAimPID();
            // System.out.println("lemon");
        } else {
            swivelEy();
        }
    }
}