package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.CANSpark1038;
import frc.robot.Limelight;
import frc.subsystem.PowerCell;

public class Shooter implements Subsystem {
    // motor port numbers
    private static boolean isEnabled = false;
    private final int SHOOTER_MOTOR_1_PORT = 60;
    private final int SHOOTER_MOTOR_2_PORT = 61;
    private final int TURRET_TURNING_PORT = 59;

    // motors and encoders
    private CANSpark1038 shooterMotor1 = new CANSpark1038(SHOOTER_MOTOR_1_PORT, MotorType.kBrushed);
    private CANSpark1038 shooterMotor2 = new CANSpark1038(SHOOTER_MOTOR_2_PORT, MotorType.kBrushed);
    private CANSpark1038 turretTurningMotor = new CANSpark1038(TURRET_TURNING_PORT, MotorType.kBrushed);
    private CANEncoder shooterEncoder1 = shooterMotor1.getAlternateEncoder();
    //private CANEncoder turretEncoder = turretTurningMotor.getAlternateEncoder();

    // Shooter 
    private static Shooter shooter;
    
    // Limelight instance
    private Limelight limelight = Limelight.getInstance();
    
    // PowerCell instance
    private PowerCell powerCell = PowerCell.getInstance();
    
    // position PID for turret
    private PIDController positionPID;
    private final double positionSetpoint = 0.0;
    private final double positionTolerance = 1.0;
    private final static double positionP = 0.005;
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
        speedPID.calculate(0.0);//come back to that
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
    public Boolean executeAimPID() {
        turretTurningMotor.set(-1 * positionPID.calculate(limelight.getXOffset()));
        return true; //return a boolean that tells us that the turret is positioned
    }

    /**
     * sets the speed of the shooter
     */
    public void executeSpeedPID() {
         shooterMotor1.set(speedPID.calculate(shooterEncoder1.getVelocity()));
         shooterMotor2.set(speedPID.calculate(shooterEncoder1.getVelocity()));
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

    public void test() {
        shooterMotor1.set(.5);
        System.out.println("position " + shooterEncoder1.getPosition());
        
    }

}