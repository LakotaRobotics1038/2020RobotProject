package frc.auton;

import frc.auton.commands.DriveStraightCommand;

public class ForwardAuton extends Auton{
	
	private final double DIST_TO_BASELINE_FROM_DS_WALL = 11;
	
	public ForwardAuton() {
        super();
        //TODO distance tbd
		addCommands(new DriveStraightCommand(DIST_TO_BASELINE_FROM_DS_WALL));
	}
	

}