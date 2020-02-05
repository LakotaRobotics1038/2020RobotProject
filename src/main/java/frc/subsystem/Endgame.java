package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.CANSpark1038;
import frc.robot.PiReader;

public class Endgame implements Subsystem {

    //Variables
    private static Endgame endgame;
    private boolean isExtending = false;
    private boolean isAdjusting = false;
    private boolean isRetracting = false;

    //Motor
    private final double ENDGAME_LEFT_MOTOR_SPEED = 0.5;
    private final double ENDGAME_RIGHT_MOTOR_SPEED = -0.5;
    private final int ENDGAME_MOTOR_PORT = 58;
    private CANSpark1038 endgameMotor = new CANSpark1038(ENDGAME_MOTOR_PORT, MotorType.kBrushless);

    /**
     * Returns the endgame instance created when the robot starrs
     * 
     * @return Endgame instance
     */

    public static Endgame getInstance() {
        if (endgame == null) {
            System.out.println("Creating a new Endgame");
            endgame = new Endgame();
        }
        return endgame;
    }

    /**
     * Instantiates endgame object
     */

    private Endgame() {

    }

    /**
     * If isExtending is true, Extend the arms
     * 
     * If isRetracting is true, Retract the arms
     * 
     * If isAdjusting is true, Move the robot to balance out the bar
     */

    public void endgamePeriodic() {
        if (isExtending) {
            // TODO Lifts the Robot
        } 
        
        else if (isRetracting) {
            // TODO Retracts the Robot
        }
        
        else if (isAdjusting) {
            // TODO Adjust the Robot Position

            if (PiReader.getLeftEndgameSwitchVal() == 1) {
                endgameMotor.set(ENDGAME_LEFT_MOTOR_SPEED);
            }

            else if (PiReader.getRightEndgameSwitchVal() == 1) {
                endgameMotor.set(ENDGAME_RIGHT_MOTOR_SPEED);
            }
        }
    }

    /**
     * Set the robot to extend the arms
     */

    public void setIsExtending() {
            isExtending = true;
        
    }

    /**
     * Set the robot to retract the arms
     */
    
    public void setIsRetracting() {
        isRetracting = true;
    }

    /**
     * Set the robot to adjust it's position on the bar
     */

    public void setIsAdjusting() {
        isAdjusting = true;

    }


}