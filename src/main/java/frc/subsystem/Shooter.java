package frc.subsystem;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Limelight;
import frc.robot.TalonSRX1038;
import frc.subsystem.PowerCell;

public class Shooter implements Subsystem {
    // motor port numbers
    private final int SHOOTER_MOTOR_1_PORT = 60;
    private final int SHOOTER_MOTOR_2_PORT = 61;
    private final int TURRET_TURNING_PORT = 57;
    private final int hardStopPort = 0;

    // motors and encoders and sensors
    private TalonSRX1038 shooterMotor1 = new TalonSRX1038(SHOOTER_MOTOR_1_PORT);
    private TalonSRX1038 shooterMotor2 = new TalonSRX1038(SHOOTER_MOTOR_2_PORT);
    private TalonSRX1038 turretTurningMotor = new TalonSRX1038(TURRET_TURNING_PORT);
    private DigitalInput hardStop = new DigitalInput(hardStopPort);

    // Shooter
    private static Shooter shooter;

    // swerves
    private boolean leftMost = false;
    private static int rightStop = 0;
    private static double swivelSpeed = 0.5;

    // Limelight instance
    private Limelight limelight = Limelight.getInstance();

    // PowerCell instance
    private PowerCell powerCell = PowerCell.getInstance();

    // position PID for turret
    private PIDController positionPID;
    private final double positionSetpoint = 0.0;
    private final double positionTolerance = .05;
    private final static double positionP = 0.15;
    private final static double positionI = 0.0;
    private final static double positionD = 0.0;

    // speed PID for shooter
    private PIDController speedPID;
    private final double speedSetpoint = 0.0;
    private final double speedTolerance = 0.0;
    private final static double speedP = 0.0;
    private final static double speedI = 0.0;
    private final static double speedD = 0.0;

    // motor speed for shooter feeder
    private final static double feedSpeed = 0.5;

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

    /**
     * sets initial PID values
     */
    public void positionSpeedPIDAdjustment() {
        positionPID = new PIDController(positionP, positionI, positionD);
        speedPID = new PIDController(speedP, speedI, speedD);

        positionPID.setPID(positionP, positionI, positionD);
        positionPID.setSetpoint(positionSetpoint);
        positionPID.setTolerance(positionTolerance);
        positionPID.disableContinuousInput();

        speedPID.setPID(speedP, speedI, speedD);
        speedPID.setSetpoint(speedSetpoint);
        speedPID.setTolerance(speedTolerance);
        speedPID.disableContinuousInput();
    }

    /**
     * Feeds ball into shooter
     */
    public void feedBall() {
        powerCell.feedShooter(feedSpeed);
    }

    /**
     * stops feeding balls into shooter
     */
    public void noFeedBall() {
        powerCell.feedShooter(0);
    }

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
        System.out.println("PID");
        turretTurningMotor.set(-1 * positionPID.calculate(limelight.getXOffset()));
    }

    /**
     * sets the speed of the shooter
     */
    public void executeSpeedPID() {
        shooterMotor1.set(speedPID.calculate(shooterMotor1.getSelectedSensorVelocity()));
        shooterMotor2.set(speedPID.calculate(shooterMotor1.getSelectedSensorVelocity()));
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

    public void move() {
        System.out.println(limelight.isTarget());
        System.out.println(limelight.getXOffset());
        if (hardStop.get()) {
            leftMost = true;
            // turretEncoder.setPosition(0);
            swivelEy();
        }
        // else if(turretEncoder.getPosition() >= rightStop)
        // {
        //     leftMost = false;
        //     swivelEy();
        // }
        if (!limelight.isTarget()) {
            executeAimPID();
            System.out.println("lemon");
        } 
        else {
            swivelEy();
        }
    }  
}