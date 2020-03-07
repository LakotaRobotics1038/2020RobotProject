package frc.auton;

import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
import frc.auton.commands.AimCommand;


public class ShootingAuton extends Auton{
    public ShootingAuton() {
        super();
        
        addCommands(
            new AimCommand(),
            new ShootCommand(),
            new DriveStraightCommand(65)
        );
    }
}
