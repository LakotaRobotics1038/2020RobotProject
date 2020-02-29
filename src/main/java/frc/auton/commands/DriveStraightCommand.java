
//not working yet. Meeting ended before i finished.

package frc.auton.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystem.DriveTrain;
import frc.robot.Gyro1038;

//import org.usfirst.frc.team1038.robot.I2CGyro;
public class DriveStraightCommand extends CommandBase {
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final double TOLERANCE = 1.9;
	private final static double dP = 0.150; // .04 proto
	private final static double dI = 0.000;
	private final static double dD = 0.002;
	private final static double tP = 0.200; // .23 proto
	private final static double tI = 0.001;
	private final static double tD = 0.000;
	public static Gyro1038 gyroSensor = Gyro1038.getInstance();
	private final DriveTrain drive = DriveTrain.getInstance();
	private PIDController drivePID; 
	private PIDController turnPID;

	/**
	 * Makes a new Drive Straight Command
	 * 
	 * @param setpoint in feet
	 */
	public DriveStraightCommand(double setpoint) {
		// Drive
		
		gyroSensor.reset();
		drivePID = new PIDController(dP,dI,dD);
		turnPID = new PIDController(tP, tI, tD);

		drivePID.setPID(dP, dI, dD);


		// *12 Converts inches to feet
		drivePID.setSetpoint(setpoint * 12 + (drive.getLeftDriveEncoderDistance()));
		drivePID.setTolerance(TOLERANCE);
		// drivePID.setOutputRange(-MAX_OUTPUT, MAX_OUTPUT);
		drivePID.disableContinuousInput();
		SmartDashboard.putData("Controls/Drive Straight", drivePID);

		// Angle
		turnPID.setTolerance(TOLERANCE);
		turnPID.enableContinuousInput(0, 360);
		SmartDashboard.putData("Controls/Drive Straight Angle", turnPID);
		addRequirements(drive);
	}

	@Override
	public void initialize() {
		// gyroSensor.reset();
		turnPID.setSetpoint(gyroSensor.getAngle());
		drive.resetEncoders();
		// turnPID.setInputRange(0, 359);
	}

	@Override
	public void execute() {

		// assuming that in the new version of PIDControl they made .enable automatic
		// drivePID.enable();
		// turnPID.enable();
		double distancePID = drivePID.calculate(drive.getLeftDriveEncoderDistance());
		// double anglePID = turnPID.calculate(gyroSensor.getAngle());
		// TODO incorperate the turnPID calculate.
		drive.tankDrive(distancePID, distancePID);
	
		System.out.println("dist out: " + distancePID + " ang out: " + " ang sp: " + turnPID.getSetpoint()
				+ "ang: " + gyroSensor.getAngle());
		
	}

	public void usePIDOutput() {
		//TODO tune pid values.
		}


	@Override
	public void end(boolean interrupted) {
		if(interrupted) {
			System.out.println("Straight interrupted");
		}
		drivePID.reset();
		turnPID.reset();
		drive.tankDrive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("DriveStraight ended");
	}

	@Override
	public boolean isFinished() {
		return drivePID.atSetpoint() && turnPID.atSetpoint();
	}
}