package frc.robotLibraries

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInput1038 extends DigitalInput {
	public DigitalInput1038 (int port) {
		super(port);
	}
	
	@Override
	public boolean get() {
		return super.get();
	}
	@Override
	public boolean getInverted() {
		return !super.get();
	}
}
