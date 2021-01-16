package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;  
import frc.robot.CANSpark1038;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

public class Endgame implements Subsystem {
    private final int LOCK_PORT = 0;
    private final int UNLOCK_PORT = 1;
    private final int SPARK_PORT = 0;
    private final int MOTOR_SPEED = 0.0;
    private final int FINAL_COUNT =0;
    public DoubleSolenoid LockSolenoid = new DoubleSolenoid(UNLOCK_PORT, LOCK_PORT);
    public boolean isLocked = false;
    public CANSpark1038 motor = new CANSpark1038(SPARK_PORT, MotorType.kBrushless);
    public CANEncoder encoder = new CANEncoder(motor);

    public void lock() {
        isLocked = true;
        LockSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void unlock() {
        isUnlocked = false;
        LockSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void raise(){
        if (encoder.getPosition() < FINAL_COUNT){
            motor.set(MOTOR_SPEED);
        }
        else {
            motor.set(0);
            lock();
        }
    }

    public void toggleLock(){
    //turn pneumatics on and off
    if  (isLocked){
        unlock();
    }
    else {
        // raise here??
        lock();
    }
    }
}