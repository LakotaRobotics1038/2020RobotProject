package frc.auton.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.subsystem.DriveTrain;
import frc.robot.Gyro1038;

public class TurnCommand extends CommandBase {
	private static Gyro1038 gyroSensor = Gyro1038.getInstance();
    private final DriveTrain drive = DriveTrain.getInstance();
    //private PIDController turnPID = getPIDController();

	private double drivePower = 0.0;
	private final double TOLERANCE = 5.1;
	private final static double P = 0.0231;
	private final static double I = 0.0001;
	private final static double D = 0.0002;
	private static  double tSetpoint;
	 
	private PIDController turnPID;

	/**
	 * Makes a new turn Command
	 * 
	 * @param setpoint in degrees
	 */
	public TurnCommand(double setpoint) {
		//super(P, I, D);
		gyroSensor.reset();
		turnPID = new PIDController(P, I, D);

		turnPID.setPID(P, I, D);
		
		tSetpoint = setpoint;
		turnPID.setSetpoint(setpoint);
		System.out.println("setpoint" + turnPID.getSetpoint());
		//turnPID.disableContinuousInput();
		SmartDashboard.putData("Controls/turn", turnPID);

		// Angle
		turnPID.setTolerance(TOLERANCE);
        turnPID.enableContinuousInput(0, 360);
        addRequirements(drive);
		//requires(drive);
	}

	@Override
	public void initialize() {
		// gyroSensor.reset();
		// drivePID.setSetpoint(dSetpoint);
		// System.out.println("setpoint" + drivePID.getSetpoint());
		// turnPID.setInputRange(0, 359);
	}

    @Override
	public void execute() {
		//turnPID.enable();
		gyroSensor.readGyro();
		double PIDTurnAdjust = turnPID.calculate(gyroSensor.getAngle());
		//this.usePIDOutput(PIDTurnAdjust);
		System.out.println("Gyro: " + gyroSensor.getAngle());
		if(PIDTurnAdjust > 0) {
			System.out.println("Clockwise: " + PIDTurnAdjust);
		} else {
			System.out.println("Counter-Clockwise: " + PIDTurnAdjust);
		}
		
		//System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + turnPID.get() + ", Setpoint: " + getSetpoint());
        drive.dualArcadeDrive(drivePower, PIDTurnAdjust);
    }

	//@Override
	/*public void end(boolean interrupted) {
		drivePID.reset();
		turnPID.reset();
		drive.tankDrive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("DriveStraight ended");
    }*/
    
	@Override
	public boolean isFinished() {
		return turnPID.atSetpoint();
    }

    // @Override
	// protected double returnPIDInput() {
	// 	return gyroSensor.getAngle();
	// }

	// @Override
	// protected void usePIDOutput(double turnPower) {
	// 	drive.dualArcadeDrive(drivePower, turnPower);		
	// }
}
