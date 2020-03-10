package frc.auton;

import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
import frc.auton.commands.AimCommand;


public class Shooting3BallAuton extends Auton{
    public Shooting3BallAuton() {
        super();
        
        addCommands(
            new AimCommand(),
            new ShootCommand(),
            new DriveStraightCommand(0) //65
        );
    }
}
