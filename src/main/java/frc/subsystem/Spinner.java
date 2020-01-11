package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CANSpark1038;

public class Spinner extends Subsystem {
    private final int SPINNER_MOTOR_PORT = 50;
    private CANSpark1038 spinnerMotor = new CANSpark1038(SPINNER_MOTOR_PORT, MotorType.kBrushless);
    private boolean rotationEnabled = false;
    private boolean colorEnabled = false;
    private static Spinner spinner;

    public static Spinner getInstance() {
        if (spinner == null) {
            System.out.println("creating a new spinner");
            spinner = new Spinner();
        }
        return spinner;
    }

    private Spinner() {

    }

    public void spin() {
        spinnerMotor.set(.5);
    }

    public void spinnerPeriodic() {
        if (rotationEnabled){
            //TODO spins the thing
        }
        else if (colorEnabled){
            //TODO do the other thing
        }
    }

    public void setRotationEnabled(){
        if(!colorEnabled){
            rotationEnabled = true;
        }
    }

    public void setcolorEnabled(){
        if(!rotationEnabled){
            colorEnabled = true;
        }
    }

    

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}