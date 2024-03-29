package frc.subsystem;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.CANSpark1038;

public class DriveTrain implements Subsystem {
    public enum DriveModes {
        tankDrive, singleArcadeDrive, dualArcadeDrive
    };

    public DriveModes currentDriveMode = DriveModes.dualArcadeDrive;

    public final double WHEEL_DIAMETER = 6;
    private final int HIGH_GEAR_PORT = 0;
    private final int LOW_GEAR_PORT = 1;

    public DoubleSolenoid GearChangeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            LOW_GEAR_PORT, HIGH_GEAR_PORT);
    public boolean isHighGear = false;

    public static CANSpark1038 CANSparkRightFront = new CANSpark1038(57, MotorType.kBrushless);
    public static CANSpark1038 CANSparkRightBack = new CANSpark1038(58, MotorType.kBrushless);
    public static CANSpark1038 CANSparkLeftFront = new CANSpark1038(52, MotorType.kBrushless);// previously 55
    public static CANSpark1038 CANSparkLeftBack = new CANSpark1038(56, MotorType.kBrushless);

    public RelativeEncoder CANSparkRightEncoder = CANSparkRightBack.getEncoder();
    public RelativeEncoder CANSparkLeftEncoder = CANSparkLeftBack.getEncoder();

    private DifferentialDrive differentialDrive;
    private static DriveTrain driveTrain;

    public static DriveTrain getInstance() {
        if (driveTrain == null) {
            System.out.println("Creating a new DriveTrain");
            driveTrain = new DriveTrain();
        }
        return driveTrain;
    }

    private DriveTrain() {
        CANSparkLeftBack.restoreFactoryDefaults();
        CANSparkLeftFront.restoreFactoryDefaults();
        CANSparkRightBack.restoreFactoryDefaults();
        CANSparkRightFront.restoreFactoryDefaults();

        CANSparkLeftBack.setInverted(true);
        CANSparkLeftFront.setInverted(true);
        CANSparkRightBack.setInverted(false);
        CANSparkRightFront.setInverted(false);

        CANSparkLeftBack.setIdleMode(IdleMode.kCoast);
        CANSparkLeftFront.setIdleMode(IdleMode.kCoast);
        CANSparkRightBack.setIdleMode(IdleMode.kCoast);
        CANSparkRightFront.setIdleMode(IdleMode.kCoast);

        CANSparkRightFront.follow(CANSparkRightBack);
        CANSparkLeftFront.follow(CANSparkLeftBack);
        CANSparkRightEncoder.setPositionConversionFactor(1 / 24.785587);
        CANSparkLeftEncoder.setPositionConversionFactor(1 / 24.785587);

        differentialDrive = new DifferentialDrive(CANSparkLeftBack, CANSparkRightBack);
    }

    // Get and return distance driven by the left of the robot in inches
    public double getLeftDriveEncoderDistance() {
        return CANSparkLeftEncoder.getPosition() * Math.PI * WHEEL_DIAMETER;
    }

    // Get and return distance driven by the right of the robot in inches
    public double getRightDriveEncoderDistance() {
        return CANSparkRightEncoder.getPosition() * Math.PI * WHEEL_DIAMETER;
    }

    public double getCANSparkRightEncoder() {
        return -CANSparkRightEncoder.getPosition();
    }

    public double getCANSparkLeftEncoder() {
        return -CANSparkLeftEncoder.getPosition();
    }

    // Pneumatics
    public void highGear() {
        isHighGear = true;
        GearChangeSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void lowGear() {
        isHighGear = false;
        GearChangeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void resetEncoders() {
        CANSparkRightEncoder.setPosition(0);
        CANSparkLeftEncoder.setPosition(0);
    }

    // Switch between drive modes
    public void driveModeToggler() {
        switch (currentDriveMode) {
            case tankDrive:
                currentDriveMode = DriveModes.singleArcadeDrive;
                break;
            case singleArcadeDrive:
                currentDriveMode = DriveModes.dualArcadeDrive;
                break;
            case dualArcadeDrive:
                currentDriveMode = DriveModes.tankDrive;
                break;
            default:
                System.out.println("Help I have fallen and I can't get up!");
                break;
        }
    }

    /**
     * Drive the robot in tank mode
     *
     * @param leftStickInput  the forward speed of the left side of the robot
     *                        (-1 to 1)
     * @param rightStickInput the forward speed of the right side of the robot
     *                        (-1 to 1)
     */
    public void tankDrive(double leftStickInput, double rightStickInput) {
        differentialDrive.tankDrive(leftStickInput, rightStickInput, true);
    }

    /**
     * Drive the robot in single stick arcade mode
     *
     * @param speed The forward speed of the robot (-1 to 1)
     * @param turn  The turn speed of the robot (-1 to 1)
     */
    public void arcadeDrive(double speed, double turn) {
        differentialDrive.arcadeDrive(speed, turn, true);
    }
}