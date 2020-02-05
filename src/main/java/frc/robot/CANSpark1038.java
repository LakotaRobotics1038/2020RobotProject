package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

/**
 * Creates a new CAN controlled spark max motor controller object
 * 
 * @param id   CAN bus ID of motor
 * @param type Motor type (Brushed or Brushless)
 */

public class CANSpark1038 extends CANSparkMax {
    public CANSpark1038(int id, CANSparkMaxLowLevel.MotorType type) {
        super(id, type);
    }
}