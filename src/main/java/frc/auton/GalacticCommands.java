package frc.auton;

import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
import frc.auton.commands.TurnCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.auton.commands.AcquireCommand;
import frc.auton.commands.AimCommand;


public class GalacticCommands extends Auton{
     
    
    public String teamColor = "Blue";
    public GalacticCommands() {
        super();
        switch(teamColor) {
            case "Blue":
            final int FIRST_TURN_ANGLE_BLUE = 90;
            final int FIRST_DRIVE_DIST_BLUE = 180;
            final int SECOND_DRIVE_DIST_BLUE = -200;
            final int ACQUIRE_TIME_BLUE = 5;
            final int SECOND_TURN_ANGLE_BLUE = 180;
                addCommands(
                    //go forward until you aquire B7;
                    new DriveStraightCommand(FIRST_DRIVE_DIST_BLUE),
                    
                    // //run aquisition;
                    new AcquireCommand(ACQUIRE_TIME_BLUE),
                    
                    //go at an angle until you get to C9;
                    new TurnCommand(FIRST_TURN_ANGLE_BLUE),
                    
                    //run aquisition;
                    new AcquireCommand(ACQUIRE_TIME_BLUE),
                    //then go at an angle until you aquire E6;
                    new TurnCommand(FIRST_TURN_ANGLE_BLUE),
                    
                    //run aquisition;
                    new AcquireCommand(ACQUIRE_TIME_BLUE),
                    
                    //then go backwards until you reach the end zone;
                    new DriveStraightCommand(SECOND_DRIVE_DIST_BLUE)
                );
                break;
            case "Red":
            final int FIRST_TURN_ANGLE_RED = 90;
            final int FIRST_DRIVE_DIST_RED = 180;
            final int SECOND_DRIVE_DIST_RED = -200;
            final int ACQUIRE_TIME_RED = 5;
            final int SECOND_TURN_ANGLE_RED = 180;
                addCommands(
                    //then go to D1 and turn around;
                    new TurnCommand(SECOND_TURN_ANGLE_RED),
                    
                    //then go forward until you aquire D5;
                    new DriveStraightCommand(FIRST_DRIVE_DIST_RED),
                    
                    //run aquisition;
                    new AcquireCommand(ACQUIRE_TIME_RED),
                    
                    //then go at an angle until you aquire A6;
                    new TurnCommand(FIRST_TURN_ANGLE_RED),
                    
                    //run aquisition;
                    new AcquireCommand(ACQUIRE_TIME_RED),
                    
                    //then turn around 
                    new TurnCommand(SECOND_TURN_ANGLE_RED),

                    //go at an angle until you aquire C3;
                    new TurnCommand(FIRST_TURN_ANGLE_RED),

                    //run aquisition;
                    new AcquireCommand(ACQUIRE_TIME_RED),

                    //go at an angle until you reach the end zone;
                    new TurnCommand(FIRST_TURN_ANGLE_RED)

                    //drop balls;
                    //then celebrate;
                );
                break;
    }
}
}