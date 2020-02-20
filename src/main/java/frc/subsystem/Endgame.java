package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.CANSpark1038;

public class Endgame implements Subsystem {

    // Variables
    private static Endgame endgame;
    private boolean isExtending = false;
    private boolean isLeftAdjusting = false;
    private boolean isRightAdjusting = false;
    private boolean isRetracting = false;
    private boolean endgameLockOut = false;

    // Deploying/Retracting Motor
    private final double ENDGAME_LIFTING_SPEED = 0.5;
    private final double ENDGAME_RETRACTING_SPEED = -0.5;
    private final int ENDGAME_LIFTING_MOTOR_PORT = 52;
    private final int ENDGAME_RETRACTING_PORT = 53;
    private CANSpark1038 liftingMotor = new CANSpark1038(ENDGAME_LIFTING_MOTOR_PORT, MotorType.kBrushless);
    private CANSpark1038 retractingMotor = new CANSpark1038(ENDGAME_RETRACTING_PORT, MotorType.kBrushless);

    // Adjusting Motor
    private final double ENDGAME_LEFT_MOTOR_SPEED = 0.5;
    private final double ENDGAME_RIGHT_MOTOR_SPEED = -0.5;
    private final int ENDGAME_ADJUSTING_MOTOR_PORT = 53;
    private CANSpark1038 adjustingMotor = new CANSpark1038(ENDGAME_ADJUSTING_MOTOR_PORT, MotorType.kBrushed);

    //Solenoid Channels
    private final int ENDGAME_SOLENOID_FORWARD_CHANNEL = 6;
    private final int ENDGAME_SOLENOID_REVERSE_CHANNEL = 7;

    //Solenoids
    private DoubleSolenoid endgameLock = new DoubleSolenoid(ENDGAME_SOLENOID_FORWARD_CHANNEL, ENDGAME_SOLENOID_REVERSE_CHANNEL);
    

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
            if (endgameLockOut){
                endgameLock.set(Value.kReverse);
                endgameLockOut = false;
            }
            liftingMotor.set(ENDGAME_LIFTING_SPEED);
        }

        else if (isRetracting) {
            // TODO Retracts the Arms when the Driver A Button is Pressed
            if (!endgameLockOut){
                endgameLock.set(Value.kForward);
                endgameLockOut = true;
            }
            retractingMotor.set(ENDGAME_RETRACTING_SPEED);
        }

        else if (isLeftAdjusting) {
            // TODO Adjust the Robot Left Position when the Driver X Button is Pressed
            adjustingMotor.set(ENDGAME_LEFT_MOTOR_SPEED);
        }

        else if (isRightAdjusting) {
            //TODO Adjust the Robot Right Position when the Driver B Button is Pressed
            adjustingMotor.set(ENDGAME_RIGHT_MOTOR_SPEED);
        }
    }


    public final Value getCurrentEndgameLockPosition(){
        return endgameLock.get();
    }

    /**
     * Don't Know if this will be needed, but it seems like it would be pretty important
     */

    public void toggleEndgameLockPosition() {
        if(endgameLockOut){
            endgameLock.set(Value.kReverse);
            endgameLockOut = false;
        }
        else if(!endgameLockOut){
            endgameLock.set(Value.kForward);
            endgameLockOut = true;
        }
    }

    /**
     * Set the robot to extend the arms
     */

    public void setIsExtending() {
        isExtending = true;
        isRetracting = false;
        isLeftAdjusting = false;
        isRightAdjusting = false;

    }

    /**
     * Set the robot to retract the arms
     */

    public void setIsRetracting() {
        isRetracting = true;
        isExtending = false;
        isLeftAdjusting = false;
        isRightAdjusting = false;
    }

    /**
     * Set the robot to adjust it's left position on the bar
     */

    public void setIsLeftAdjusting() {
        isLeftAdjusting = true;
        isExtending = false;
        isRetracting = false;
        isRightAdjusting = false;

    }

    /**
     * Set the robot to adjust it's right position on the bar
     */

    public void setIsRightAdjusting() {
        isRightAdjusting = true;
        isExtending = false;
        isRetracting = false;
        isLeftAdjusting = false;

    }
}