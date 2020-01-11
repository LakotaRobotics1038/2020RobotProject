package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CANSpark1038;


public class Spinner extends Subsystem {
    public CANSpark1038 spinnerMotor = new CANSpark1038(50, MotorType.kBrushless);
    
    public void spin() {
        spinnerMotor.set(.5);
    }


    @Override
    protected void initDefaultCommand() {
        //TODO Auto-generated method stub
    }  
}
