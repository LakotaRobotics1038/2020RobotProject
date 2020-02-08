package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CANSpark1038;

public class Acquisition extends Subsystem {
    //ports
    private final int beaterBarPort = 54;
    
    //Solenoid channels
    private final int solenoidChannel1 = 0;
    private final int solenoidChannel2 = 1;

    //Solenoid
    private DoubleSolenoid acquisitionOut = new DoubleSolenoid(solenoidChannel1, solenoidChannel2);
    private boolean acquisitionPositionOut = false;

    //motor
    private CANSpark1038 beaterBar = new CANSpark1038(beaterBarPort, MotorType.kBrushless); //beatDatBoi or lashingLad

    //motor speeds
    private final static double beaterBarFwdSpeed = 0.5;
    //private final static double runBeaterBarRevSpeed = -0.5;
    
    //acquisition instance
    private static Acquisition acquisition;

    /**
     * returns the acquisiton instance when the robot starts
     * 
     * @return acquisition instance
     */
    public static Acquisition getInstance() {
        if(acquisition == null) {
            System.out.println("creating a new acquisition");
            acquisition = new Acquisition();
        }
        return acquisition;
    }

    /**
     * sends out and pulls back in the acquisition
     */
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

    /**
     * sets the speed that the acquistion will pull the balls into storage
     */
    public void runBeaterBarFwd() {
        beaterBar.set(beaterBarFwdSpeed);
    }

    /**
     * sets the speed that the acquisiont will throw the balls out of storage
     */
    // public void runBeaterBarRev() {
    //     beaterBar.set(runBeaterBarRevSpeed);
    // }

    /**
     * stops the acquistion from pulling in or throwing balls out of storage
     */
    public void stopBeaterBar() {
        beaterBar.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }
   
}