package frc.auton;

import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.TurnCommand;
import frc.auton.commands.AcquireCommand;
import frc.auton.commands.CheckCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;


public class GalacticCommands extends Auton{
    public GalacticCommands() {
        super();
<<<<<<< HEAD
        switch(teamColor) {
            case "Blue":
            final int FIRST_DRIVE_DIST_BLUE = 156;
            final double SECOND_DRIVE_DIST_BLUE = 99.36;
            final double THIRD_DRIVE_DIST_BLUE = 67.08;
            final int FOURTH_DRIVE_DIST_BLUE = 60;
            final int ACQUIRE_TIME_BLUE = 1;
            final double FIRST_TURN_ANGLE_BLUE = 63.438;
            final double SECOND_TURN_ANGLE_BLUE = -63.367;
            final double ACQUIRE_TIME_BLUE = 1.5;
            final double FIRST_TURN_ANGLE_BLUE = 300;//63.438;
            final double SECOND_TURN_ANGLE_BLUE = 63.367;
            final int THIRD_TURN_ANGLE_BLUE = 0;
                addCommands(
                    //new AcquireCommand(180)
                    new AcquireCommand(0.1),
                    //go forward until you aquire E6, 13 feet
                    new DriveStraightCommand(FIRST_DRIVE_DIST_BLUE),
                    //run aquisition at E6
                    new AcquireCommand(ACQUIRE_TIME_BLUE),
                    //face B7
                    new TurnCommand(FIRST_TURN_ANGLE_BLUE),
                    //Forward to B7, 8.28 feet
                    new DriveStraightCommand(SECOND_DRIVE_DIST_BLUE),
                    //run aquisition at B7
                    new AcquireCommand(ACQUIRE_TIME_BLUE),
                    //Face C9
                    new TurnCommand(SECOND_TURN_ANGLE_BLUE),
                    //Forward to C9, 5.59 feet
                    new DriveStraightCommand(THIRD_DRIVE_DIST_BLUE),
                    //run aquisition at C9
                    new AcquireCommand(ACQUIRE_TIME_BLUE),
                    //Face endzone
                    new TurnCommand(THIRD_TURN_ANGLE_BLUE),
                    //Forward to endzone, 5.5 feet
                    new DriveStraightCommand(FOURTH_DRIVE_DIST_BLUE)
                );
                break;
            case "Red":
            final int FIRST_DRIVE_DIST_RED = 60;
            final double SECOND_DRIVE_DIST_RED = 67.08;
            final double THIRD_DRIVE_DIST_RED = 99.36;
            final double FOURTH_DRIVE_DIST_RED = 156;
            final int ACQUIRE_TIME_RED = 2;
            final double FIRST_TURN_ANGLE_RED = 63.367;
            final double SECOND_TURN_ANGLE_RED = -63.438;
            final int THIRD_TURN_ANGLE_RED = 0;
                addCommands(
                    //go forward until you aquire C3, 5.5 feet;
                    new AcquireCommand(0),
                    new DriveStraightCommand(FIRST_DRIVE_DIST_RED),
                    //run aquisition at C3
                    new AcquireCommand(ACQUIRE_TIME_RED),
                    //face D5
                    new TurnCommand(FIRST_TURN_ANGLE_RED),
                    //Forward to D5, 5.59 feet
                    new DriveStraightCommand(SECOND_DRIVE_DIST_RED),
                    //run aquisition at D5
                    new AcquireCommand(ACQUIRE_TIME_RED),
                    //Face A6
                    new TurnCommand(SECOND_TURN_ANGLE_RED),
                    //Forward to A6, 8.28 feet
                    new DriveStraightCommand(THIRD_DRIVE_DIST_RED),
                    //run aquisition at A6
                    new AcquireCommand(ACQUIRE_TIME_RED),
                    //Face endzone
                    new TurnCommand(THIRD_TURN_ANGLE_RED),
                    //Forward to endzone, 13 feet
                    new DriveStraightCommand(FOURTH_DRIVE_DIST_RED)
                );
                break;
=======
        addCommands(
            new ParallelCommandGroup(
            //go forward until you aquire B3, 5.5 feet
            new DriveStraightCommand(72),
            //run aquisition 
            new AcquireCommand(8)
            ),
            new CheckCommand()
        );
>>>>>>> dc9c1d5b88cafb800129ed2852139980595c59a4
    }
}