package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.CANSpark1038;

public class Storage implements Subsystem {
    // ports
    private final int SHUTTLE_MOTOR_PORT = 62;
    private final int START_LASER_PORT = 6;
    private final int END_LASER_PORT = 5;
    private final int SHUTTLE_MOTOR_ENCODER_COUNTS = 47;
    private final int ENCODER_OFFSET = 500;

    // shuttle motor and speed
    private CANSpark1038 shuttleMotor = new CANSpark1038(SHUTTLE_MOTOR_PORT, MotorType.kBrushless);
    private CANEncoder shuttleMotorEncoder = new CANEncoder(shuttleMotor);
    private final static double shuttleMotorSpeed = 1.0;
    

    // declares storage
    private static Storage storage;

    // photoeyes
    private DigitalInput laserStart = new DigitalInput(START_LASER_PORT);
    private DigitalInput laserEnd = new DigitalInput(END_LASER_PORT);

    // manual drive
    private boolean manualStorageForward = false;
    private boolean manualStorageReverse = false;

    public enum ManualStorageModes {
        Forward, Reverse
    }

    /**
     * returns the storage instance when the robot starts
     * 
     * @return storage instance
     */
    public static Storage getInstance() {
        if (storage == null) {
            System.out.println("creating a new storage");
            storage = new Storage();
        }
        return storage;
    }

    private Storage() {
        shuttleMotor.setInverted(true);
        shuttleMotorEncoder.setPosition(SHUTTLE_MOTOR_ENCODER_COUNTS + ENCODER_OFFSET);
        shuttleMotorEncoder.setPositionConversionFactor(47/2);
    }

    public void enableManualStorage(ManualStorageModes mode) {
        switch (mode) {
        case Forward:
            manualStorageForward = true;
            break;
        case Reverse:
            manualStorageReverse = true;
            break;
        default:
            break;
        }
    }

    public void disableManualStorage() {
        manualStorageReverse = false;
        manualStorageForward = false;
    }

    /**
     * feeds the shooter
     * 
     * @param power how fast to feed the shooter
     */
    public void feedShooter(double power) {
        shuttleMotor.set(power);
    }

    /**
     * runs the ball storage
     */
    public void periodic() {
        if (!manualStorageForward && !manualStorageReverse) {
            if (shuttleMotorEncoder.getPosition() < SHUTTLE_MOTOR_ENCODER_COUNTS && !laserEnd.get())                                                                                // sensor
            {
                shuttleMotor.set(shuttleMotorSpeed);
            } else if (laserStart.get() && !laserEnd.get()) {
                shuttleMotorEncoder.setPosition(0);
            } else {
                shuttleMotor.set(0);
            }
        } else if (manualStorageForward) {
            shuttleMotor.set(shuttleMotorSpeed);
            shuttleMotorEncoder.setPosition(SHUTTLE_MOTOR_ENCODER_COUNTS + ENCODER_OFFSET);
        } else if (manualStorageReverse) {
            shuttleMotor.set(-shuttleMotorSpeed);
            shuttleMotorEncoder.setPosition(SHUTTLE_MOTOR_ENCODER_COUNTS + ENCODER_OFFSET);
        }
    }
}
