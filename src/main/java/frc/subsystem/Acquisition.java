package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.CANSpark1038;

public class Acquisition implements Subsystem {
    // Ports
    private final int beaterBarPort = 54;
    
    // Solenoid channels
    private final int RAISE_ACQUISITION_CHANNEL = 3;
    private final int LOWER_ACUQUISITION_CHANNEL = 2;

    // Solenoid
    private DoubleSolenoid acquisitionSolenoid = new DoubleSolenoid(RAISE_ACQUISITION_CHANNEL, LOWER_ACUQUISITION_CHANNEL);
    private boolean acquisitionOut = false;

    // Motor
    private CANSpark1038 beaterBar = new CANSpark1038(beaterBarPort, MotorType.kBrushless);

    // Motor speeds

    /*-----------------------------------------*/
    /* Clue #4                                 */
    /* Find where you pick which auton is run. */
    /*-----------------------------------------*/
    private final static double BEATER_BAR_SPEED = .65;
    
    // Acquisition instance
    private static Acquisition acquisition;


    public static Acquisition getInstance() {
        if(acquisition == null) {
            System.out.println("creating a new acquisition");
            acquisition = new Acquisition();
        }
        return acquisition;
    }

    /*---------------------------------------------------------------------------------------*/
    /* Clue #10                                                                              */
    /* Find the spot where the "Shooter Angle" is output for the driver and operator to see. */
    /*---------------------------------------------------------------------------------------*/
    public void toggleAcquisitionPosition() {
        if(acquisitionOut) {
            acquisitionSolenoid.set(Value.kForward);
            acquisitionOut = false;
        } else {
            acquisitionSolenoid.set(Value.kReverse);
            acquisitionOut = true;
        }
    }

    /*--------------------------------------------------*/
    /* Clue #3                                          */
    /* Find the  numerical value of "BEATER_BAR_SPEED". */
    /*--------------------------------------------------*/
    public void runBeaterBarFwd() {
        beaterBar.set(BEATER_BAR_SPEED);
    }

    public void runBeaterBarRev() {
        beaterBar.set(-BEATER_BAR_SPEED);
    }

    public void stopBeaterBar() {
        beaterBar.set(0);
    }
}