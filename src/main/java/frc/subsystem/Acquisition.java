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
    private final int RAISE_ACQUISITION_CHANNEL = 2;
    private final int LOWER_ACUQUISITION_CHANNEL = 3;

    //Solenoid
    private DoubleSolenoid acquisitionOut = new DoubleSolenoid(RAISE_ACQUISITION_CHANNEL, LOWER_ACUQUISITION_CHANNEL);
    // TODO: make an enum
    private boolean acquisitionPositionOut = false;

    //motor
    private CANSpark1038 beaterBar = new CANSpark1038(beaterBarPort, MotorType.kBrushless); //beatDatBoi or lashingLad

    //motor speeds
    private final static double beaterBarFwdSpeed = .65;
    private final static double runBeaterBarRevSpeed = -.65;
    
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
            acquisitionOut.set(Value.kForward);
            System.out.println("Putting acquisiton up");
            acquisitionPositionOut = false;
        }
        else if(!acquisitionPositionOut)
        {
            acquisitionOut.set(Value.kReverse);
            System.out.println("Putting acquisition down");
            acquisitionPositionOut = true;
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
    public void runBeaterBarRev() {
        beaterBar.set(runBeaterBarRevSpeed);
    }

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