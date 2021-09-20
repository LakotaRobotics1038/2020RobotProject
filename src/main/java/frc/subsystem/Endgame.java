package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;  
import frc.robot.CANSpark1038;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.CANEncoder;

//NOTE WHEN MOTOR IS LOCKED ENDGAME CANNOT EXTEND BUT IT CAN RETRACT
//Writen by julian heidt, your welcome! (kidding i had a lot of help with this, thanks for everyone who helped!)
public class Endgame implements Subsystem {
    private final int MOTOR_LOCK_PORT = 4;
    private final int MOTOR_UNLOCK_PORT = 5;
    private final int ENDGAME_LOCK_PORT = 6;
    private final int ENDGAME_UNLOCK_PORT = 7;
    private final int SPARK_PORT = 53;
    private final int MAX_COUNT = 0; // was 220, that is the value at the bottom of endgame, top of endgame should be 0 Needs to be updated with actual value
    private final int MIN_COUNT = -200;  //Needs to be updated with actual value
    public enum directionsOptions {preExtend, extending, retracting, stop};
    public directionsOptions Directions = directionsOptions.stop;
    public DoubleSolenoid MotorLockSolenoid = new DoubleSolenoid(MOTOR_UNLOCK_PORT, MOTOR_LOCK_PORT);
    public DoubleSolenoid EndgameLockSolenoid = new DoubleSolenoid(ENDGAME_UNLOCK_PORT, ENDGAME_LOCK_PORT);
    public boolean safetyIsLocked = true; //Safety lock for endgame
    public boolean MotorIsLocked = true; //Motor lock for endgame
    public boolean endgameState = false; //Is endgame up or down
    public boolean preExtendState = false; //Has prextend been completed
    public CANSpark1038 motor = new CANSpark1038(SPARK_PORT, MotorType.kBrushless);
    public CANEncoder encoder = new CANEncoder(motor);
    private static Endgame endgame;

    public static Endgame getInstance() {
        if (endgame == null) {
            System.out.println("Creating a new DriveTrain");
            endgame = new Endgame();
        }
        return endgame;
    }

    public void reset() {
        endgameState = false;
        encoder.setPosition(50); // 220 is the current position
        //motorLock();
        Directions = directionsOptions.stop;
    }
    public void periodic() {

        System.out.println(endgameState + " " + MotorIsLocked + " " + encoder.getPosition() + " " + safetyIsLocked);
        switch (Directions) {
            default:
                //Default Value for endgame motor
                System.out.println("How?");
                motor.set(0);
                break;
            
            //sets the motor to the preextend state, this lets the motor unlock. 
            //The motor needs to move down first so that the lock can unlock
            case preExtend: 
                if(encoder.getPosition() < 8) {
                    motorUnLock();
                    motor.set(-.25);
                    System.out.println("PreExtend");
                    preExtendState = true;
                }
                else {
                    motorUnLock();
                    motor.set(.5);
                    Directions = directionsOptions.stop;
                }
                break;

            //sets the motor to the extending state. This will have Endgame go up until it reaches the max count. 
            case extending:
                //Extends Endgame
                //motorUnLock();
                if (encoder.getPosition() < MAX_COUNT) {
                    Directions = directionsOptions.preExtend;
                    motor.set(.5);
                    System.out.println("Extending Endgame");
                }
                else {
                    motor.set(0);
                    Directions = directionsOptions.stop;
                    motorLock();

                }
                break;

            //sets the motor to the retracting state. This will have Endgame go down until it reaches the minium count.    
            case retracting:
                motorLock();
                //Retracts Endgame
                if (encoder.getPosition() > MIN_COUNT) {
                    motor.set(-.8);
                    System.out.println("Retracting Endgame");
                }
                else {
                    motor.set(0);
                    Directions = directionsOptions.stop;
                }

                break;

            //sets the motor to the stop state. This will set the motor to stop and lock the motor. Call this after every single case.
            case stop:
                //Stops Endgame
                //motor.set(-.8);
                //Thread.sleep(10);
                motor.set(0);
                if (!preExtendState) {
                    motorLock();
                }
                System.out.println("Stopped Endgame");
                break;
        
        }

    }

    //Locks Endgame's Safety
    public void safetyLock() {
        if (!safetyIsLocked) {
            EndgameLockSolenoid.set(DoubleSolenoid.Value.kForward);
            safetyIsLocked = true;
            System.out.println("Endgame is locked");
        }
        else {
            System.out.println("Endgame is already locked");
        }
    }

    public void safetyUnlock() {
        if (safetyIsLocked) {
            EndgameLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            safetyIsLocked = false;
            System.out.println("Endgame is unlocked");
        }
        else {
            System.out.println("Endgame is already unlocked");
        }
    }

    //Unlocks Endgame and Endgame Motor
    // public void endgameUnlock() {
    //     if (safetyIsLocked && MotorIsLocked) {
    //         EndgameLockSolenoid.set(DoubleSolenoid.Value.kReverse);
    //         MotorLockSolenoid.set(DoubleSolenoid.Value.kReverse);
    //         safetyIsLocked = false;
    //         MotorIsLocked = false;
    //         System.out.println("Endgame is unlocked");
    //     }
    //     else {
    //         System.out.println("Endgame is already unlocked");
    //     }
    // }
    //Locks Endgame Motor
    public void motorLock() {
        if (!MotorIsLocked) {
            MotorLockSolenoid.set(DoubleSolenoid.Value.kForward);
            MotorIsLocked = true;
            preExtendState = false;
        }
    }
    //Unlocks Endgame Motor
    public void motorUnLock() {
        if (MotorIsLocked) {
            MotorLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            MotorIsLocked = false;
        }
    }
    
    // public void manual(double speed) {
    //     if(endgameState == true) {
    //         motor.set(-.3);
    //     }
    // }
    
    //Extends and Retracts endgame
    public void onJoyStick(double joystickValue) {
            System.out.println("Begin Extend");
            safetyUnlock(); //Unlocks endgame safety and motor
            if (joystickValue > 0) {
                if (!preExtendState) {
                    Directions = directionsOptions.preExtend; //runs pre-extend
                }
                else {
                    Directions = directionsOptions.extending; //runs extend
                }
            }
            else if (joystickValue < 0) {
                Directions = directionsOptions.retracting;   //Starts retracting endgame
            }
            else if (joystickValue == 0) {
                Directions = directionsOptions.stop; //stops endgame from moving
            }
        
        // else if (endgameState == true && (Directions == directionsOptions.stop)) {   //Checks to see if the button has been pressed before
        //     System.out.println("Begin retract");
        //     Directions = directionsOptions.retracting;  //Starts retracting endgame
        //     //safetyLock();  //Locks the safety
        //     endgameState = false; //Tells the robot x has been pressed again
        // }
    }
    
}