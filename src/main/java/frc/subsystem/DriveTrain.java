package frc.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.CANSpark1038;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
public class DriveTrain implements Subsystem {
    public enum driveModes {
        tankDrive, singleArcadeDrive, dualArcadeDrive
    };

    public driveModes currentDriveMode = driveModes.dualArcadeDrive;

    public final double WHEEL_DIAMETER = 4;
    private final int HIGH_GEAR_PORT = 0;
    private final int LOW_GEAR_PORT = 1;

    public DoubleSolenoid GearChangeSolenoid = new DoubleSolenoid(LOW_GEAR_PORT, HIGH_GEAR_PORT);
    public boolean isHighGear = false;

    public static CANSpark1038 CANSparkRightFront = new CANSpark1038(57, MotorType.kBrushless);
    public static CANSpark1038 CANSparkRightBack = new CANSpark1038(58, MotorType.kBrushless);
    public static CANSpark1038 CANSparkLeftFront = new CANSpark1038(52, MotorType.kBrushless);//previously 55
    public static CANSpark1038 CANSparkLeftBack = new CANSpark1038(56, MotorType.kBrushless);

    public CANEncoder CANSparkRightEncoder = CANSparkRightBack.getEncoder();
    public CANEncoder CANSparkLeftEncoder = CANSparkLeftBack.getEncoder();

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
        CANSparkRightBack.setInverted(true);
        CANSparkRightFront.setInverted(true);

        CANSparkLeftBack.setIdleMode(IdleMode.kCoast);
        CANSparkLeftFront.setIdleMode(IdleMode.kCoast);
        CANSparkRightBack.setIdleMode(IdleMode.kCoast);
        CANSparkRightFront.setIdleMode(IdleMode.kCoast);

        CANSparkRightFront.follow(CANSparkRightBack);
        CANSparkLeftFront.follow(CANSparkLeftBack);
        
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
        return CANSparkRightEncoder.getPosition() * -1;
    }

    public double getCANSparkLeftEncoder() {
        return CANSparkLeftEncoder.getPosition() * -1;
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
    }

    // Switch between drive modes
    public void driveModeToggler() {
        switch (currentDriveMode) {
        case tankDrive:
            currentDriveMode = driveModes.singleArcadeDrive;
            break;
        case singleArcadeDrive:
            currentDriveMode = driveModes.dualArcadeDrive;
            break;
        case dualArcadeDrive:
            currentDriveMode = driveModes.tankDrive;
            break;
        default:
            System.out.println("Help I have fallen and I can't get up!");
            break;
        }
    }

    // Drive robot with tank controls (input range -1 to 1 for each stick)
    public void tankDrive(double leftStickInput, double rightStickInput) {
        differentialDrive.tankDrive(leftStickInput, rightStickInput, true);
    }

    // Drive robot using a single stick (input range -1 to 1)
    public void singleAracadeDrive(double speed, double turnValue) {
        differentialDrive.arcadeDrive(speed, turnValue, true);
    }

    // Drive robot using 2 sticks (input ranges -1 to 1)
    public void dualArcadeDrive(double yaxis, double xaxis) {
        differentialDrive.arcadeDrive(yaxis, xaxis, true);
    }

   
}