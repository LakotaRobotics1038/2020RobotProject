package frc.auton;

import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.auton.commands.AimCommand;


public class Shooting3BallAuton extends Auton{
    public Shooting3BallAuton() {
        super();
        /*------------------------------------------------------*/
        /* Clue #6:                                             */
        /* Find the place that the turret is aimed for auton.   */
        /*------------------------------------------------------*/
        addCommands(
            new AimCommand(11),
            new ParallelCommandGroup(
                new ShootCommand(6),
                new AimCommand(6)
            ),
            new DriveStraightCommand(200)
        );
    }
}
