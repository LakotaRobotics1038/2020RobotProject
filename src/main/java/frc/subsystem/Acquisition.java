package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.CANSpark1038;

public class Acquisition implements Subsystem {
    // Ports
    private final int beaterBarPort = 54;

    // Solenoid channels
    private final int RAISE_ACQUISITION_CHANNEL = 3;
    private final int LOWER_ACQUISITION_CHANNEL = 2;

    // Solenoid
    private DoubleSolenoid acquisitionSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            RAISE_ACQUISITION_CHANNEL,
            LOWER_ACQUISITION_CHANNEL);
    private AcquisitionStates acquisitionState = AcquisitionStates.In;

    private enum AcquisitionStates {
        In, Out
    }

    // Motor
    private CANSpark1038 beaterBar = new CANSpark1038(beaterBarPort, MotorType.kBrushless);

    // Motor speeds
    private final static double BEATER_BAR_SPEED = .85;

    // Acquisition instance
    private static Acquisition acquisition;

    /**
     * returns the acquisition instance when the robot starts
     *
     * @return acquisition instance
     */
    public static Acquisition getInstance() {
        if (acquisition == null) {
            System.out.println("creating a new acquisition");
            acquisition = new Acquisition();
        }
        return acquisition;
    }

    /**
     * sends out and pulls back in the acquisition
     */
    public void toggleAcquisitionPosition() {
        switch (acquisitionState) {
            case In:
                acquisitionSolenoid.set(Value.kReverse);
                acquisitionState = AcquisitionStates.Out;
                break;

            case Out:
                acquisitionSolenoid.set(Value.kForward);
                acquisitionState = AcquisitionStates.In;
                break;
        }
    }

    public void down() {
        switch (acquisitionState) {
            case In:
                acquisitionSolenoid.set(Value.kReverse);
                acquisitionState = AcquisitionStates.Out;
                break;
            case Out:
                System.out.println("already out");
                break;
        }
    }

    /**
     * sets the speed that the acquisition will pull the balls into storage
     */
    public void runBeaterBarFwd() {
        beaterBar.set(BEATER_BAR_SPEED);
    }

    /**
     * sets the speed that the acquisition will throw the balls out of storage
     */
    public void runBeaterBarRev() {
        beaterBar.set(-BEATER_BAR_SPEED);
    }

    /**
     * stops the acquisition from pulling in or throwing balls out of storage
     */
    public void stopBeaterBar() {
        beaterBar.set(0);
    }
}