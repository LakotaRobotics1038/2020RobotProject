package frc.auton;


import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.auton.commands.DriveStraightCommand;
import frc.robot.Robot;

public class ForwardAuton extends Auton{
	
	private static double DIST_TO_BASELINE_FROM_DS_WALL = 72;
	public ForwardAuton() {
        super();
    }
    
    public SequentialCommandGroup select() {
        
        //TODO distance tbd
        group.addCommands(new DriveStraightCommand(DIST_TO_BASELINE_FROM_DS_WALL));
        System.out.println(group);
        return group;
    
        
    }
	

}