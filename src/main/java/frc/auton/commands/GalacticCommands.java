package frc.auton;

import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.auton.commands.AimCommand;


public class Shooting3BallAuton extends Auton{
    public static int t = 90;
    public static int d = 200;
    public static int b = -200;
    public static int a = 20;
    public static int ta = 180; 
    public Shooting3BallAuton() {
        super();

        addCommands(
            //go forward until you aquire B7;
            new DriveStraightCommand(d),
            
            //run aquisition;
            new AcquireCommand(a),
            
            //go at an angle until you get to C9;
            new TurnCommand(t),
            
            //run aquisition;
            new AcquireCommand(a),
            //then go at an angle until you aquire E6;
            new TurnCommand(t),
            
            //run aquisition;
            new AcquireCommand(a),
            
            //then go backwards until you reach the end zone;
            new DriveStraightCommand(b),
            
            //drop balls;
            //then go to D1 and turn around;
            new TurnCommand(ta),
            
            //then go forward until you aquire D5;
            new DriveStraightCommand(d),
            
            //run aquisition;
            new AcquireCommand(a),
            
            //then go at an angle until you aquire A6;
            new TurnCommand(t),
            
            //run aquisition;
            new AcquireCommand(a),
            
            //then turn around 
            new TurnCommand(ta),

            //go at an angle until you aquire C3;
            new TurnCommand(t),

            //run aquisition;
            new AcquireCommand(a),

            //go at an angle until you reach the end zone;
            new TurnCommand(t),

            //drop balls;
            //then celebrate;



        );


            
            /*new ParallelCommandGroup(
                new ShootCommand(6),
                new AimCommand(6)
            ),
            new DriveStraightCommand(200)
        );*/
    }
}
