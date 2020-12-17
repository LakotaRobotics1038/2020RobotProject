package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;


public class Joystick1038 extends Joystick {
    // Button Locations
	private final int X_BUTTON = 3;
	private final int A_BUTTON = 1;
	private final int B_BUTTON = 2;
	private final int Y_BUTTON = 4;
	private final int LEFT_BUTTON = 5;
	private final int RIGHT_BUTTON = 6;
	private final int SQUARE_BUTTON = 7;
	private final int LINE_BUTTON = 8;
	private final int LEFT_JOYSTICK_CLICK = 9;
	private final int RIGHT_JOYSTICK_CLICK = 10;

	// Joystick locations
	private final int LEFT_STICK_HORIZONTAL = 0;
	private final int LEFT_STICK_VERTICAL = 1;
	private final int RIGHT_STICK_HORIZONTAL = 4;
	private final int RIGHT_STICK_VERTICAL = 5;
	private final int LEFT_TRIGGER = 2;
	private final int RIGHT_TRIGGER = 3;

    public Joystick1038(int port) {
        super(port);
	}
	
	public int getPOV() {
		return getPOV(0);
	}

    public boolean getXButton() {
        return getRawButton(X_BUTTON);
    }

	public boolean getAButton() {
		return getRawButton(A_BUTTON);
	}

	public boolean getBButton() {
		return getRawButton(B_BUTTON);
	}

	public boolean getYButton() {
		return getRawButton(Y_BUTTON);
	}

	public boolean getLeftButton() {
		return getRawButton(LEFT_BUTTON);
	}

	public boolean getRightButton() {
		return getRawButton(RIGHT_BUTTON);
	}

	public boolean getSquareButton() {
		return getRawButton(SQUARE_BUTTON);
	}

	public boolean getLineButton() {
		return getRawButton(LINE_BUTTON);
	}

	public boolean getLeftJoystickClick() {
		return getRawButton(LEFT_JOYSTICK_CLICK);
	}

	public boolean getRightJoystickClick() {
		return getRawButton(RIGHT_JOYSTICK_CLICK);
	}

	public double deadband(double value) {
		return Math.abs(value) < 0.10 ? 0 : value;
	}

	public double getLeftJoystickVertical() {
		return deadband(-getRawAxis(LEFT_STICK_VERTICAL));
	}

	public double getLeftJoystickHorizontal() {
		return deadband(getRawAxis(LEFT_STICK_HORIZONTAL));
	}

	public double getRightJoystickVertical() {
		return deadband(-getRawAxis(RIGHT_STICK_VERTICAL));
	}

	public double getRightJoystickHorizontal() {
		return deadband(getRawAxis(RIGHT_STICK_HORIZONTAL));
	}

	public double getLeftTrigger() {
		return getRawAxis(LEFT_TRIGGER);
	}

	public double getRightTrigger() {
		return getRawAxis(RIGHT_TRIGGER);
    }

	public double setLeftRumble(double speed) {
		setRumble(GenericHID.RumbleType.kLeftRumble, speed);
		return speed;
	}

	public double setRightRumble(double speed) {
		setRumble(GenericHID.RumbleType.kRightRumble, speed);
		return speed;
	}
}