package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CANSpark1038;
import frc.robot.Joystick1038;

public class Spinner extends Subsystem {
    public CANSpark1038 spinnerMotor = new CANSpark1038(50, MotorType.kBrushless);
    
    @Override
    protected void initDefaultCommand() {
        //TODO Auto-generated method stub
    }{  //we don't know why the exta curly brackets are needed, but they are soooooooooooooooo
        
    
    

    if(JoystickName.getLeftButton() == true) {
        spinnerMotor.set(.5);
    }
}
}
