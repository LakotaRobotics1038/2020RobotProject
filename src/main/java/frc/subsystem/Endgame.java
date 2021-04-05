package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;  
import frc.robot.CANSpark1038;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.CANEncoder;

public class Endgame implements Subsystem {
    private final int LOCK_PORT = 4;
    private final int UNLOCK_PORT = 5;
    private final int SPARK_PORT = 53;
    private final double MOTOR_SPEED = 0.25;
    private final int FINAL_COUNT = 60;
    public enum Directions {extending, retracting, neutral};
    public Directions currentDirections = Directions.neutral;
    public DoubleSolenoid LockSolenoid = new DoubleSolenoid(UNLOCK_PORT, LOCK_PORT);
    public boolean isLocked = false;
    public CANSpark1038 motor = new CANSpark1038(SPARK_PORT, MotorType.kBrushless);
    public CANEncoder encoder = new CANEncoder(motor);

    public void periodic(double joystick) {
        //motor.set(joystick);
        // if(Math.abs(encoder.getPosition()) >= (FINAL_COUNT - 20)) {
        //     currentDirections = Directions.neutral;
        // }
        // else if(Math.abs(encoder.getPosition()) <= (20) && currentDirections == Directions.retracting) {
        //     currentDirections = Directions.neutral;
        // }
        // switch (currentDirections) {
        //     case extending:
        //         //motor.set(0.5);
        //         System.out.println("Extend");
        //         break;
        //     case retracting:
        //         //motor.set(-0.5);
        //         System.out.println("Retract");
        //         break;
        //     case neutral:
        //         motor.set(0);
        //         //System.out.println("Neutral");
        //         break;
        // }
    }
    public void in() {
        currentDirections = Directions.retracting;
    }
    public void lock() {
        isLocked = true;
        LockSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void unlock() {
        isLocked = false;
        LockSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    // public void extend() {
    //     motor.set(0.25);
    // }
    // public void raise(){
    //     if (Math.abs(encoder.getPosition()) > 20){
    //         if(encoder.getPosition() < 0) {
    //             motor.set(MOTOR_SPEED);
    //         }
    //         else{
    //             motor.set(-1 * MOTOR_SPEED);
    //         }
    //     }
    //     else {
    //         motor.set(0);
    //         lock();
    //     }
    // }

    public void toggleLock(){
    //turn pneumatics on and off
    if  (isLocked){
        unlock();
        currentDirections = Directions.extending;
    }
    else {
        lock();
        currentDirections = Directions.neutral;
    }
    }
}