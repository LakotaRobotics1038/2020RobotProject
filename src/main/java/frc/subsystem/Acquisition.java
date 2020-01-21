package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CANSpark1038;

public class Acquisition extends Subsystem {
    private DoubleSolenoid acquisitionOut = new DoubleSolenoid(0, 1);
    private CANSpark1038 beaterBar = new CANSpark1038(0, MotorType.kBrushed); //beatDatBoi or lashingLad
    private boolean acquisitionPositionOut = false;
    private static Acquisition acquisition;

    public static Acquisition getInstance() {
        if(acquisition == null) {
            System.out.println("creating a new acquisition");
            acquisition = new Acquisition();
        }
        return acquisition;
    }

    public void toggleAcquisitionPosition() {
        if(acquisitionPositionOut)
        {
            acquisitionOut.set(Value.kReverse);
            acquisitionPositionOut = true;
        }
        else if(!acquisitionPositionOut)
        {
            acquisitionOut.set(Value.kForward);
            acquisitionPositionOut = false;
        }
    }

    public void runBeaterBarFwd() {
        beaterBar.set(.5);
    }

    public void runBeaterBarRev() {
        beaterBar.set(-.5);
    }

    public void stopBeaterBar() {
        beaterBar.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }
   
}