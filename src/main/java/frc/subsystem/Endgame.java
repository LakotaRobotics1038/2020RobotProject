package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.CANSpark1038;

public class Endgame implements Subsystem {

    // Variables
    private static Endgame endgame;
    private boolean isExtending = false;
    private boolean isLeftAdjusting = false;
    private boolean isRightAdjusting = false;
    private boolean isRetracting = false;

    // Deploying/Retracting Motor
    private final double ENDGAME_LIFTING_SPEED = 0.5;
    private final double ENDGAME_RETRACTING_SPEED = -0.5;
    private final int ENDGAME_LIFTING_MOTOR_PORT = 52;
    private CANSpark1038 liftingMotor = new CANSpark1038(ENDGAME_LIFTING_MOTOR_PORT, MotorType.kBrushless);

    // Adjusting Motor
    private final double ENDGAME_LEFT_MOTOR_SPEED = 0.5;
    private final double ENDGAME_RIGHT_MOTOR_SPEED = -0.5;
    private final int ENDGAME_ADJUSTING_MOTOR_PORT = 53;
    private CANSpark1038 adjustingMotor = new CANSpark1038(ENDGAME_ADJUSTING_MOTOR_PORT, MotorType.kBrushed);

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
     * If isExtending is true, Extend the arms;
     * 
     * If isRetracting is true, Retract the arms;
     * 
     * If isLeftAdjusting is true, Move the robot to the Left on the bar
     * 
     * If isRightAdjusting is true, Move the robot to the Right on the bar
     */

    public void endgamePeriodic() {
        if (isExtending) {
            // TODO Lifts the Arms when the Driver X Button is Pressed
            liftingMotor.set(ENDGAME_LIFTING_SPEED);
        }

        else if (isRetracting) {
            // TODO Retracts the Arms when the Driver A Button is Pressed
            liftingMotor.set(ENDGAME_RETRACTING_SPEED);
        }

        else if (isLeftAdjusting) {
            // TODO Adjust the Robot Position
            adjustingMotor.set(ENDGAME_LEFT_MOTOR_SPEED);
        }

        else if (isRightAdjusting) {
            adjustingMotor.set(ENDGAME_RIGHT_MOTOR_SPEED);
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

    public void setIsLeftAdjusting() {
        isLeftAdjusting = true;

    }

    public void setIsRightAdjusting() {
        isRightAdjusting = true;

    }
}