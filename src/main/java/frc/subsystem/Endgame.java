package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.CANSpark1038;
import frc.robot.PiReader;

public class Endgame implements Subsystem {
    private final int endgameMotorPort = 58;
    private CANSpark1038 endgameMotor = new CANSpark1038(endgameMotorPort, MotorType.kBrushless);
    private static Endgame endgame;
    private boolean isExtending = false;
    private boolean isAdjusting = false;
    private boolean isRetracting = false;

    public static Endgame getInstance() {
        if (endgame == null) {
            System.out.println("Creating a new Endgame");
            endgame = new Endgame();
        }
        return endgame;
    }

    private Endgame() {

    }

    public void endgamePeriodic() {
        if (isExtending) {
            // TODO Lifts the Robot
        } 
        
        else if (isRetracting) {
            // TODO Retracts the Robot
        }
        
        else if (isAdjusting) {
            // TODO Adjust the Robot Position

            // TODO Change Limit Switch when Drew/Sam get Done with Class
            if ((PiReader.getLeftEndgameSwitchVal()) > 0.5) {
                endgameMotor.set(.5);
            }

            else if (PiReader.getLeftEndgameSwitchVal() < 0.5) {
                endgameMotor.set(-.5);
            }
        }
    }

    public void setIsExtending() {
        isExtending = true;
    }

    public void setIsAdjusting() {
        isAdjusting = true;

    }

    public void setIsRetracting() {
        isRetracting = true;
    }
}