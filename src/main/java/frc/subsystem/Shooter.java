package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Joystick1038;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.CANSpark1038;

public class Shooter implements Subsystem {
    // TODO change port numbers
    private final int SHOOTER_MOTOR_1_PORT = 0; 
    private final int SHOOTER_MOTOR_2_PORT = 1; 
    private CANSpark1038 shooterMotor1 = new CANSpark1038(SHOOTER_MOTOR_1_PORT, MotorType.kBrushless);
    private CANSpark1038 shooterMotor2 = new CANSpark1038(SHOOTER_MOTOR_2_PORT, MotorType.kBrushless);
    private static Shooter shooter;
    private PIDController positionPID;
    private PIDController speedPID;
    // pid values
    private final double positionTolerance = 0.0;
    private final double speedTolerance = 0.0;
    private final double positionSetpoint = 0.0;
    private final double speedSetpoint = 0.0;
    private final static double positionP = 0.0;
    private final static double positionI = 0.0;
    private final static double positionD = 0.0;
    private final static double speedP = 0.0;
    private final static double speedI = 0.0;
    private final static double speedD = 0.0;


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

    

    
	public void initialize() {
		// gyroSensor.reset();
		positionPID.setSetpoint(positionSetpoint);
		//speedPID.resetEncoders();
		// turnPID.setInputRange(0, 359);
	}

	
	public void execute() {

		// assuming that in the new version of PIDControl they made .enable automatic
		// positionPID.enable();
		//speedPID.enable();
		//double distancePID = drivePID.calculate(drive.getLeftDriveEncoderDistance());
		//double anglePID = turnPID.calculate(gyroSensor.getAngle());.
		
		
	}

	public void usePIDOutput() {
		
		
	}


	
	public void end(boolean interrupted) {
		if(interrupted) {
			System.out.println("position interrupted");
		}
		positionPID.reset();
        speedPID.reset();
        //positionPID.disable();
        //positionPID.disable();
	}

	
	public boolean isFinished() {
		return positionPID.atSetpoint() && speedPID.atSetpoint();
	}


   
   
    // @Override
    // protected void initDefaultCommand() {
    //     // TODO Auto-generated method stub

    // }
}