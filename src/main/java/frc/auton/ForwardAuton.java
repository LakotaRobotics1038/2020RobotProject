package frc.auton;

import frc.auton.commands.DriveStraightCommand;

public class ForwardAuton extends Auton {
    // FIXME: this name isn't right, what should it be?
    private static double DIST_TO_BASELINE_FROM_DS_WALL = 72;
    
	public ForwardAuton() {
        super();
        addCommands(new DriveStraightCommand(DIST_TO_BASELINE_FROM_DS_WALL));
    }
}