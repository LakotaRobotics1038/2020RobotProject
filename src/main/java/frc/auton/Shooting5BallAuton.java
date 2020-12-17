

package frc.auton;

import frc.auton.commands.AimCommand;
import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.auton.commands.AcquireCommand;


public class Shooting5BallAuton extends Auton {
    public Shooting5BallAuton() {
        super();
        
        addCommands(
            new ParallelCommandGroup(
                new AcquireCommand(8),
                new DriveStraightCommand(200)
            ),
            new AimCommand(4),
            new ParallelCommandGroup(
                new AimCommand(0),
                new AcquireCommand(0),
                new ShootCommand(0)
            )
        );
    }

}
