package frc.subsystem;

import java.util.function.ToDoubleFunction;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.Robot;
import frc.robot.Limelight;
import frc.robot.Prox;
import frc.robot.TalonSRX1038;
import frc.subsystem.Storage;
import frc.subsystem.Storage.ManualStorageModes;

public class Shooter implements Subsystem {
    // Motor port numbers
    private static boolean isEnabled = false;
    private final int SHOOTER_MOTOR_1_PORT = 60;
    private final int SHOOTER_MOTOR_2_PORT = 61;
    private final int TURRET_TURNING_PORT = 59;
    private final int HARD_STOP_PORT = 0;

    // Motors and encoders and sensors
    private TalonSRX1038 shooterMotor1 = new TalonSRX1038(SHOOTER_MOTOR_1_PORT);
    private TalonSRX1038 shooterMotor2 = new TalonSRX1038(SHOOTER_MOTOR_2_PORT);
    private TalonSRX1038 turretTurningMotor = new TalonSRX1038(TURRET_TURNING_PORT);
    //private Prox hardStop = new Prox(HARD_STOP_PORT);

    // Shooter
    private static Shooter shooter;

    // Turret
    private TurretDirections currentTurretDirection = TurretDirections.Left;

    public enum TurretDirections {
        Left, Right
    }

    private final static int LEFT_STOP = 220; // 114500
    private final static int RIGHT_STOP = -31;
    private static double swivelSpeed = 0.3;

    // Limelight instance
    public Limelight limelight = Limelight.getInstance();

    // Storage instance
    private Storage storage = Storage.getInstance();

    // Position PID for turret
    private final double positionSetpoint = 0.0;
    private final double positionTolerance = 1;
    private final static double positionP = 0.036; // .15
    private final static double positionI = 0.0;
    private final static double positionD = 0.0;
    private PIDController positionPID = new PIDController(positionP, positionI, positionD);

    // Speed PID for shooter
    private final double speedSetpoint = limelight.getShooterSetpoint();
    private final double speedTolerance = 9001;
    private final static double speedP = 0.00003;
    private final static double speedI = 0.00001;
    private final static double speedD = 0.00001;
    private PIDController speedPID = new PIDController(speedP, speedI, speedD);
    private boolean isRunning = false;

    // Motor speed for shooter feeder
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
        positionPID.setSetpoint(positionSetpoint);
        positionPID.setTolerance(positionTolerance);
        positionPID.disableContinuousInput();
        turretTurningMotor.setSelectedSensorPosition(0);

        //speedPID.setSetpoint(speedSetpoint);
        speedPID.setTolerance(speedTolerance);
        speedPID.disableContinuousInput();
    }

    /**
     * Feeds ball into shooter
     */
    public void feedBall() {
        if (isFinished()) {
            storage.enableManualStorage(ManualStorageModes.Forward);
        }
    }

    /**
     * stops feeding balls into shooter
     */
    public void noFeedBall() {
        storage.disableManualStorage();
    }

    /**
     * disables speed motors and pid
     */
    public void disablePID() {
        // TODO: Fix this
        speedPID.calculate(0.0);
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
        double power = 0.0;
        if(limelight.getYOffset() < -13){
            power = positionPID.calculate(limelight.getXOffset() + 1);
            System.out.println("X += 1");
        }
        else{
            power = positionPID.calculate(limelight.getXOffset());
        }
         System.out.println("x " + limelight.getXOffset());
         System.out.println("Power" + power);
        turretTurningMotor.set(power * 0.5);
    }

    /**
     * sets the speed of the shooter
     */
    public void executeSpeedPID() {
        //shooterMotor1.set(-.55);
        //shooterMotor2.set(.55);
        isRunning = true;
        speedPID.setSetpoint(limelight.getShooterSetpoint());
        double power = speedPID.calculate(getShooterSpeed()) + limelight.getMotorPower();
        System.out.println("speed" + getShooterSpeed());
        System.out.println("setpoint: " + speedPID.getSetpoint());
        System.out.println("power" + power);
        shooterMotor1.set(-power);
        shooterMotor2.set(power);
    }

    public void disableSpeedPID() {
        isRunning = false;
    }

    public boolean speedOnTarget() {
        return speedPID.atSetpoint();
    }

    public void shootManually(double speed) {
        shooterMotor1.set(speed);
        shooterMotor2.set(-speed);
    }

    public void setTurretDirection(TurretDirections value) {
        currentTurretDirection = value;
    }

    public void enable() {
        isEnabled = true;
    }

    public void disable() {
        isEnabled = false;
    }

    public void periodic() {
        if (isEnabled) {
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
        return positionPID.atSetpoint() && speedPID.atSetpoint() && isRunning;
    }

    public boolean turretOnTarget() {
        //return false;
        return positionPID.atSetpoint() && limelight.canSeeTarget();
    }

    /**
     * limits shooter turn radius
     */
    public void turnTurret(double turnSpeed) {
        turretTurningMotor.set(turnSpeed);
    }

    public void swivelEy() {
        switch (currentTurretDirection) {
            case Left:
                turretTurningMotor.set(swivelSpeed);
                break;
            case Right:
                turretTurningMotor.set(-swivelSpeed);
                break;
        }
    }

    public double getTurretEncoder() {
        return turretTurningMotor.getSelectedSensorPosition()*180.00/82000.00;
    }

    public double getShooterSpeed() {
        return shooterMotor1.getSelectedSensorVelocity()/4100.00;
    }

    // public boolean getHardStop() {
    //     return hardStop.get();
    // }

    public void resetTurretEncoder() {
        turretTurningMotor.setSelectedSensorPosition(0);
    }

    private void stopTurret() {
        turretTurningMotor.set(0);
    }

    public TurretDirections getTurretDirection() {
        return currentTurretDirection;
    }

    

    public void goToCrashPosition() {
        if (Math.abs(turretTurningMotor.getSelectedSensorPosition()) < 2000) {
            stopTurret();
        } else if (turretTurningMotor.getSelectedSensorPosition() > 0) {
            currentTurretDirection = TurretDirections.Right;
            swivelEy();
        } else if (turretTurningMotor.getSelectedSensorPosition() < 0) {
            currentTurretDirection = TurretDirections.Left;
            swivelEy();
        }
    }

    public void holdPosition() {
        turretTurningMotor.set(0);
    }

    // public void ninety() {
    //     if(getTurretEncoder() < 90) {
    //         turretTurningMotor.set(swivelSpeed);
    //     }
    //     else {
    //         turretTurningMotor.set(0);
    //     }
    // }

    public void move() {
        // if (hardStop.get()) {
        //     turretTurningMotor.setSelectedSensorPosition(0);
        // }
        if (getTurretEncoder() <= RIGHT_STOP) {
            currentTurretDirection = TurretDirections.Left;
            swivelEy();
        } else if (getTurretEncoder() >= LEFT_STOP) {
            currentTurretDirection = TurretDirections.Right;
            swivelEy();
         } else if (limelight.canSeeTarget()) {
             executeAimPID();
        } else {
            swivelEy();
        }
    }
    // public void manual(double turnDir) {
    //     System.out.println(turnDir + "," + getTurretEncoder());
    //     if (turnDir < 0 && getTurretEncoder() > RIGHT_STOP) {
    //         turretTurningMotor.set(turnDir/2);
    //     }
    //     else if (turnDir > 0 && getTurretEncoder() <LEFT_STOP) {
    //         turretTurningMotor.set(turnDir/2);
    //     }
    //     else{
    //         turretTurningMotor.set(0);
    //     }
    // }
}