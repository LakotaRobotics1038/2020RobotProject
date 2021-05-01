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
    private final int ENDGAME_LOCK_PORT = 7;
    private final int ENDGAME_UNLOCK_PORT = 6;
    private final int SPARK_PORT = 53;
    private final int MAX_COUNT = 165; //Needs to be updated with actual value
    private final int MIN_COUNT = 0;  //Needs to be updated with actual value
    public enum directionsOptions {preExtend, extending, retracting, stop};
    public directionsOptions Directions = directionsOptions.stop;
    public DoubleSolenoid MotorLockSolenoid = new DoubleSolenoid(MOTOR_UNLOCK_PORT, MOTOR_LOCK_PORT);
    public DoubleSolenoid EndgameLockSolenoid = new DoubleSolenoid(ENDGAME_UNLOCK_PORT, ENDGAME_LOCK_PORT);
    public boolean safetyIsLocked = true; //Safety lock for endgame
    public boolean MotorIsLocked = true; //Motor lock for endgame
    public boolean endgameState = false;
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
        encoder.setPosition(0);
        motorLock();
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
            case preExtend:
                if(encoder.getPosition() > -8) {
                    motor.set(-.25);
                    System.out.println("PreExtend");
                }
                else {
                    motorUnLock();
                    motor.set(.5);
                    Directions = directionsOptions.extending;
                }
                break;
            case extending:
                //Extends Endgame
                if (encoder.getPosition() < MAX_COUNT && !safetyIsLocked && !MotorIsLocked) {
                    motor.set(.5);
                    System.out.println("Extending Endgame");
                }
                else {
                    motor.set(0);
                    Directions = directionsOptions.stop;
                    motorLock();

                }
                break;
            case retracting:
                //Retracts Endgame
                if (encoder.getPosition() > MIN_COUNT && !safetyIsLocked) {
                    motor.set(-.4);
                    System.out.println("Retracting Endgame");
                }
                else {
                    motor.set(0);
                    Directions = directionsOptions.stop;
                }

                break;
            case stop:
                //Stops Endgame
                motor.set(0);
                motorLock();
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

    //Unlocks Endgame and Endgame Motor
    public void endgameUnlock() {
        if (safetyIsLocked && MotorIsLocked) {
            EndgameLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            MotorLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            safetyIsLocked = false;
            MotorIsLocked = false;
            System.out.println("Endgame is unlocked");
        }
        else {
            System.out.println("Endgame is already unlocked");
        }
    }
    //Locks Endgame Motor
    public void motorLock() {
        if (!MotorIsLocked ) {
            MotorLockSolenoid.set(DoubleSolenoid.Value.kForward);
            MotorIsLocked = true;
        }
    }
    //Unlocks Endgame Motor
    public void motorUnLock() {
        if (MotorIsLocked) {
            MotorLockSolenoid.set(DoubleSolenoid.Value.kReverse);
            MotorIsLocked = false;
        }
    }
    //Extends and Retracts endgame
    public void onButton() {
        if (endgameState == false && MotorIsLocked && Directions == directionsOptions.stop) {    //Checks to see if X has been pressed before and if the motor is locked
            System.out.println("Begin Extend");
            endgameUnlock();    //Unlocks endgame safety and motor
            encoder.setPosition(0);
            Directions = directionsOptions.preExtend;
            //Directions = directionsOptions.extending;   //Starts extending endgame
            endgameState = true;     //tells the robot that the button has been pressed 
        }
        else if (endgameState == true && (Directions == directionsOptions.stop)) {   //Checks to see if the button has been pressed before
            System.out.println("Begin retract");
            Directions = directionsOptions.retracting;  //Starts retracting endgame
            //safetyLock();  //Locks the safety
            endgameState = false; //Tells the robot x has been pressed again
        }
    }
}