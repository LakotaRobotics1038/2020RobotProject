
//not working yet. Meeting ended before i finished.

package frc.auton.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystem.DriveTrain;
//import org.usfirst.frc.team1038.robot.I2CGyro;
import frc.robot.Robot;

public class DriveStraightCommand extends PIDCommand {
	private double encoderZero;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final double TOLERANCE = 1.9;
	private final double MAX_OUTPUT = .8;
	private final static double dP = 0.150; // .04 proto
	private final static double dI = 0.000;
	private final static double dD = 0.002;
	private final static double tP = 0.200; // .23 proto
	private final static double tI = 0.001;
	private final static double tD = 0.000;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController drivePID = getController();
	private PIDController turnPID = new PIDController(tP, tI, tD, gyroSensor, Robot.emptySpark);

	/**
	 * Makes a new Drive Straight Command
	 * 
	 * @param setpoint in feet
	 */
	public DriveStraightCommand(double setpoint) {
		// Drive
		super(new PIDController(dP, dI, dD), DriveTrain.getInstance()::getLeftDriveEncoderDistance, setpoint,
				d -> DriveTrain.getInstance().tankDrive(d, d));
		drivePID.setPID(dP, dI, dD);
		//*12 Converts inches to feet
		drivePID.setSetpoint(setpoint * 12 + (DriveTrain.getInstance().getLeftDriveEncoderDistance()));
		drivePID.setTolerance(TOLERANCE);
		// drivePID.setOutputRange(-MAX_OUTPUT, MAX_OUTPUT);
		drivePID.disableContinuousInput();
		SmartDashboard.putData("Controls/Drive Straight", drivePID);

		// Angle
		turnPID.setTolerance(TOLERANCE);
		turnPID.enableContinuousInput(0, 360);
		SmartDashboard.putData("Controls/Drive Straight Angle", turnPID);
		addRequirements(Robot.driveTrain);
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
		double distancePID = drivePID.calculate();
		double anglePID = turnPID.get();
		System.out.println("dist out: " + distancePID + " ang out: " + anglePID + " ang sp: " + turnPID.getSetpoint()
				+ "ang: " + gyroSensor.getAngle());
		usePIDOutput(distancePID, anglePID);
	}

	@Override
	public boolean isFinished() {
		return drivePID.atSetpoint() && turnPID.atSetpoint();
	}

}