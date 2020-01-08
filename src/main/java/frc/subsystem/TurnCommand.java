package frc.subsystem;

import frc.robot.Robot;
import frc.robot.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnCommand extends PIDCommand {
	
	private double drivePower = 0.0;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final double TOLERANCE = 5.1;
	private final double OUTPUT_RANGE = .6;
	private final static double P = 0.030;
	private final static double I = 0.0001;
	private final static double D = 0.0002;
//	private final static double P = 0.007;
//	private final static double I = 0.000;
//	private final static double D = 0.000;
	//private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController turnPID = getPIDController();
	
	/**
	 * Creates a new Turn Command
	 * @param setpoint angle to turn to
	 */
	public TurnCommand(int setpoint) {
		super(P, I, D);
		setSetpoint(setpoint);
		turnPID.setAbsoluteTolerance(TOLERANCE);
		turnPID.setOutputRange(-OUTPUT_RANGE, OUTPUT_RANGE);
		super.setInputRange(0, 360);
		turnPID.setContinuous(true);
		SmartDashboard.putData("Controls/Turn PID", turnPID);
		requires(Robot.robotDrive);
	}
	
	@Override
	public void initialize() {
	//	gyroSensor.reset();
	}
	
	@Override
	public void execute() {
		turnPID.enable();
		double PIDTurnAdjust = turnPID.get();
		this.usePIDOutput(PIDTurnAdjust);
		if(PIDTurnAdjust > 0) {
			System.out.println("Clockwise");
		} else {
			System.out.println("Counter-Clockwise");
		}
		
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + turnPID.get() + ", Setpoint: " + getSetpoint());
	}
	
	@Override
	public void interrupted() {
		System.out.println("TurnCommand interrupted");
		end();
	}
	
	@Override
	public void end() {
		turnPID.reset();
		//turnPID.disable();
		//turnPID.free();
		double gyroReading = gyroSensor.getAngle();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("Finished at " + gyroReading);
		System.out.println("TurnCommand ended");
	}
	
	@Override
	public boolean isFinished() {
		return turnPID.onTarget();
	}

	@Override
	protected double returnPIDInput() {
		return gyroSensor.getAngle();
	}

	@Override
	protected void usePIDOutput(double turnPower) {
		drive.dualArcadeDrive(drivePower, turnPower);		
	}
}