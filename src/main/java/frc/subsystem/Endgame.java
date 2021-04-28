package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;  
import frc.robot.CANSpark1038;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.CANEncoder;

//NOTE WHEN MOTOR IS LOCKED ENDGAME CANNOT EXTEND BUT IT CAN RETRACT

public class Endgame implements Subsystem {
    private final int MOTOR_LOCK_PORT = 4;
    private final int MOTOR_UNLOCK_PORT = 5;
    private final int ENDGAME_LOCK_PORT = 6;
    private final int ENDGAME_UNLOCK_PORT = 7;
    private final int SPARK_PORT = 53;
    private final int MAX_COUNT = 175; //Needs to be updated with actual value
    private final int MIN_COUNT = 25;  //Needs to be updated with actual value
    public enum directionsOptions {extending, retracting, stop};
    public directionsOptions Directions = directionsOptions.stop;
    public DoubleSolenoid MotorLockSolenoid = new DoubleSolenoid(MOTOR_UNLOCK_PORT, MOTOR_LOCK_PORT);
    public DoubleSolenoid EndgameLockSolenoid = new DoubleSolenoid(ENDGAME_UNLOCK_PORT, ENDGAME_LOCK_PORT);
    public boolean EndgameIsLocked = true; //Safety lock for endgame
    public boolean MotorIsLocked = false; //Motor lock for endgame
    public boolean endgameState = false;
    public CANSpark1038 motor = new CANSpark1038(SPARK_PORT, MotorType.kBrushless);
    public CANEncoder encoder = new CANEncoder(motor);
    public void periodic() {
        switch (Directions) {
            default:
                //Default Value for endgame motor
                motor.set(0);
                break;

            case extending:
                //Extends Endgame
                if (encoder.getPosition() <= MAX_COUNT && !EndgameIsLocked && !MotorIsLocked) {
                    motor.set(.25);
                    System.out.println("Extending Endgame");
                }
                else {
                    Directions = directionsOptions.stop;
                    motorLock();

                }
                break;
            case retracting:
                //Retracts Endgame
                if (encoder.getPosition() >= MIN_COUNT && !EndgameIsLocked && !MotorIsLocked) {
                    motor.set(-.25);
                    System.out.println("Retracting Endgame");
                }
                else {
                    Directions = directionsOptions.stop;
                }

                break;
            case stop:
                //Stops Endgame
                motor.set(0);
                motorLock();
                MotorIsLocked = true;
                System.out.println("Stopped Endgame");
                break;
        }

    }

    //Locks Endgame's Safety
    public void endgameLock() {
        if (encoder.getPosition() <= MIN_COUNT && !EndgameIsLocked && !MotorIsLocked) {
            EndgameLockSolenoid.set(DoubleSolenoid.Value.kForward);
            EndgameIsLocked = true;
            System.out.println("Endgame is locked");
        }
        else {
            System.out.println("Endgame is already locked");
        }
    }

    //Unlocks Endgame and Endgame Motor
    public void endgameUnlock() {
        if (encoder.getPosition() <= MIN_COUNT && EndgameIsLocked && MotorIsLocked) {
            EndgameLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            MotorLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            EndgameIsLocked = false;
            MotorIsLocked = false;
            System.out.println("Endgame is unlocked");
        }
        else {
            System.out.println("Endgame is already unlocked");
        }
    }
    //Locks Endgame Motor
    public void motorLock() {
        if (encoder.getPosition() >= MAX_COUNT && !MotorIsLocked ) {
            MotorLockSolenoid.set(DoubleSolenoid.Value.kForward);
            MotorIsLocked = true;
        }
    }
    //Unlocks Endgame Motor
    public void motorUnLock() {
        if (encoder.getPosition() <= MIN_COUNT && MotorIsLocked ) {
            MotorLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            MotorIsLocked = false;
        }
    }
    //Extends and Retracts endgame
    public void onButton() {
        if (endgameState == false && MotorIsLocked) {    //Checks to see if X has been pressed before and if the motor is locked
            endgameUnlock();    //Unlocks endgame safety and motor
            Directions = directionsOptions.extending;   //Starts extending endgame
            endgameState = true;     //tells the robot that the button has been pressed 
        }
        else if (endgameState == true && (Directions == directionsOptions.stop)) {   //Checks to see if the button has been pressed before
            Directions = directionsOptions.retracting;  //Starts retracting endgame
            //endgameLock();  //Locks the safety
            endgameState = false; //Tells the robot x has been pressed again
        }
    }
}