package frc.subsystem;

import frc.robot.Robot;
import frc.robot.DriveTrain;
import frc.robot.PiReader;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
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
	private static PiReader gyroSensor = PiReader.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController turnPID = getController();
	
	/**
	 * Creates a new Turn Command
	 * @param setpoint angle to turn to
	 */
	public TurnCommand(double setpoint) {
		//needs gyro class to finsih constuctor
		super(new PIDController(P, I, D), gyroSensor::getGyroVal, setpoint,
			d -> DriveTrain.getInstance().dualArcadeDrive(0,d));
		turnPID.setSetpoint(setpoint);
		turnPID.setTolerance(TOLERANCE);
		turnPID.enableContinuousInput(0, 360);
		SmartDashboard.putData("Controls/Turn PID", turnPID);
		addRequirements(drive);
	}

	@Override
	public void initialize() {
	//	gyroSensor.reset();
	}
	
	@Override
	public void execute() {
		// turnPID.enable();
		//needs gyro class for parameters
		double PIDTurnAdjust = turnPID.calculate(gyroSensor.getGyroVal());
		//this.usePIDOutput(PIDTurnAdjust); THIS MIGHT STILL BE NECCESSARY. Limits the power output of the motors.
		if(PIDTurnAdjust > 0) {
			System.out.println("Clockwise");
		} else {
			System.out.println("Counter-Clockwise");
		}
		
		// .calculate needs gyro class to get attributes.
		System.out.println("Current Angle: " + gyroSensor.getGyroVal() + ", PIDTurnAdjust: " + turnPID.calculate(gyroSensor.getGyroVal()) + ", Setpoint: " + turnPID.getSetpoint());

	}
	
	@Override
	public void end(boolean interrupted) {
		if(interrupted) {
			System.out.println("TurnCommand interrupted");
		}
		turnPID.reset();
		//turnPID.disable();
		//turnPID.free();
		double gyroReading = gyroSensor.getGyroVal();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("Finished at " + gyroReading);
		System.out.println("TurnCommand ended");
	}

	@Override
	public boolean isFinished() {
		return turnPID.atSetpoint();
	}


// 	@Override
// 	protected void usePIDOutput(double turnPower) {
// 		drive.dualArcadeDrive(drivePower, turnPower);		
// 	}
 }