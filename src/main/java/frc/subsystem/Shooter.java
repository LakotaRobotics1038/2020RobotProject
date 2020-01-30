package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.CANSpark1038;
import frc.robot.Limelight;

public class Shooter implements Subsystem {
    // TODO change port numbers
    private final int SHOOTER_MOTOR_1_PORT = 0; 
    private final int SHOOTER_MOTOR_2_PORT = 1; 
    private final int SHOOTER_MOTOR_3_PORT = 3; 
    private final int TURRET_TURNING = 57;
    // private CANSpark1038 shooterMotor1 = new CANSpark1038(SHOOTER_MOTOR_1_PORT, MotorType.kBrushless);
    // private CANSpark1038 shooterMotor2 = new CANSpark1038(SHOOTER_MOTOR_2_PORT, MotorType.kBrushless);
    // private CANSpark1038 shooterMotor3 = new CANSpark1038(SHOOTER_MOTOR_3_PORT, MotorType.kBrushless);
    // private CANEncoder shooterEncoder1 = shooterMotor1.getEncoder();
    // private CANEncoder shooterEncoder2 = shooterMotor2.getEncoder();
    private CANSpark1038 turretTurningMotor = new CANSpark1038(TURRET_TURNING, MotorType.kBrushed);
    private static Shooter shooter;
    private PIDController positionPID;
    private PIDController speedPID;
    private Limelight limelight = Limelight.getInstance();
    // pid values
    private final double positionTolerance = 3.0;
    private final double speedTolerance = 0.0;
    private final double positionSetpoint = 0.0;
    private final double speedSetpoint = 0.0;
    private final static double positionP = 0.01;
    private final static double positionI = 0.0;
    private final static double positionD = 0.0;
    private final static double speedP = 0.0;
    private final static double speedI = 0.0;
    private final static double speedD = 0.0;
    // Servos for testing
    //private Servo angleServo = new Servo(5);


    public static Shooter getInstance() {
        if(shooter == null) {
            System.out.println("creating a new shooter");
            shooter = new Shooter();
        }
        return shooter;
    }

    public void positionSpeedPIDAdjustment() {
        positionPID  = new PIDController(positionP, positionI, positionD);
        speedPID  = new PIDController(speedP, speedI, speedD);

        positionPID.setPID(positionP, positionI, positionD);
        positionPID.setSetpoint(positionSetpoint);
        positionPID.setTolerance(positionTolerance);
        positionPID.disableContinuousInput();
        
        speedPID.setPID(speedP, speedI, speedD);
        speedPID.setSetpoint(speedSetpoint);
        speedPID.setTolerance(speedTolerance);
        speedPID.disableContinuousInput();
    }

    public void feedBall() {
        // shooterMotor3.set(.5);
    }

    public void noFeedBall() {
        // shooterMotor3.set(0);
    }

    public void disablePID() {
        //speedPID.calculate(0.0);
        // shooterMotor1.set(0);
        // shooterMotor2.set(0);
    }

    
	public void initialize() {
		positionPID.setSetpoint(positionSetpoint);
		
	}

	
	public void executeAimPID() {
        turretTurningMotor.set(positionPID.calculate(limelight.getXOffset()));
        //angleServo.set(angleServo.get() + limelight.getXOffset());	
    }

    public void executeSpeedPID() {
        // shooterMotor1.set(speedPID.calculate(shooterEncoder1.getVelocity()));
        // shooterMotor2.set(speedPID.calculate(shooterEncoder1.getVelocity()));
    }

	public void end(boolean interrupted) {
		if(interrupted) {
			System.out.println("position interrupted");
		}
		positionPID.reset();
        speedPID.reset();
	}

	
	public boolean isFinished() {
		return positionPID.atSetpoint() && speedPID.atSetpoint();
	}


   
   
    // @Override
    // protected void initDefaultCommand() {
    //     // TODO Auto-generated method stub

    // }
}