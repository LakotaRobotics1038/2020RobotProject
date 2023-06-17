/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.subsystem.Storage;
import frc.robot.Joystick1038.PovPositions;
import frc.subsystem.Acquisition;
import frc.subsystem.Shooter;
import frc.subsystem.DriveTrain;
import frc.subsystem.Storage.ManualStorageModes;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private final Gyro1038 gyro = Gyro1038.getInstance();

    // Compressor
    public Compressor c = new Compressor(PneumaticsModuleType.CTREPCM);

    // Drive
    private final DriveTrain driveTrain = DriveTrain.getInstance();

    // Joystick
    private final Joystick1038 driverJoystick = new Joystick1038(0);
    private final Joystick1038 operatorJoystick = new Joystick1038(1);
    public double multiplier = .8;

    // Storage
    private final Storage storage = Storage.getInstance();

    // Acquisition
    private final Acquisition acquisition = Acquisition.getInstance();
    private boolean prevOperatorYState = false;

    // shooter
    private final Shooter shooter = Shooter.getInstance();
    private double shooterSpeed = 0.5;
    private PovPositions prevPovPosition = PovPositions.None;

    // spinner
    // private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kMXP);
    // private final Dashboard dashboard = Dashboard.getInstance();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        shooter.resetTurretEncoder();
        gyro.calibrate();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        // dashboard.update();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     */
    @Override
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    /**
     * This function is called periodically during operator control.
     *
     */
    @Override
    public void teleopPeriodic() {
        operator();
        driver();
        storage.periodic();
        SmartDashboard.putNumber("Shooter speed", -shooterSpeed);
    }

    public void driver() {
        switch (driveTrain.currentDriveMode) {
            case tankDrive:
                driveTrain.tankDrive(driverJoystick.getLeftY() * multiplier,
                        driverJoystick.getRightY() * multiplier);
                break;
            case dualArcadeDrive:
                driveTrain.arcadeDrive(driverJoystick.getLeftY() * multiplier,
                        driverJoystick.getRightX() * multiplier);
                break;
            case singleArcadeDrive:
                driveTrain.arcadeDrive(driverJoystick.getLeftY() * multiplier,
                        driverJoystick.getLeftX() * multiplier);
                break;
        }

        if (driverJoystick.getBackButton()) {
            driveTrain.driveModeToggler();
        }

        if (driverJoystick.getRightBumper() && driverJoystick.getRightTriggerDigital()) {
            multiplier = 1;
            driveTrain.highGear();
        } else if (driverJoystick.getRightBumper()) {
            multiplier = 1;
            driveTrain.lowGear();
        } else if (driverJoystick.getRightTriggerDigital()) {
            multiplier = .8;
            driveTrain.highGear();
        } else {
            multiplier = .8;
            driveTrain.lowGear();
        }
    }

    public void operator() {
        if (operatorJoystick.getRightBumper()) {
            acquisition.runBeaterBarFwd();
        } else if (operatorJoystick.getRightTriggerDigital()) {
            acquisition.runBeaterBarRev();
        } else {
            acquisition.stopBeaterBar();
        }

        if (operatorJoystick.getYButton() && !prevOperatorYState) {
            acquisition.toggleAcquisitionPosition();
            prevOperatorYState = true;
        } else if (!operatorJoystick.getYButton()) {
            prevOperatorYState = false;
        }

        if (operatorJoystick.getLeftBumper()) {
            shooter.shootManually(shooterSpeed);
            operatorJoystick.setLeftRumble(1);
            operatorJoystick.setRightRumble(1);
        } else {
            shooter.shootManually(0);
            operatorJoystick.setRightRumble(0);
            operatorJoystick.setLeftRumble(0);
        }

        if (operatorJoystick.getLeftTriggerDigital() && operatorJoystick.getLeftBumper()) {
            shooter.feedBall();
        } else if (operatorJoystick.getLeftY() > .5) {
            storage.enableManualStorage(ManualStorageModes.Forward);
        } else if (operatorJoystick.getLeftY() < -.5) {
            storage.enableManualStorage(ManualStorageModes.Reverse);
        } else {
            storage.disableManualStorage();
        }

        switch (operatorJoystick.getPOVPosition()) {
            case Up:
                if (prevPovPosition != PovPositions.Up)
                    shooterSpeed = MathUtil.clamp(shooterSpeed += .1, 0.1, 1);
                break;
            case Down:
                if (prevPovPosition != PovPositions.Down)
                    shooterSpeed = MathUtil.clamp(shooterSpeed -= .1, 0.1, 1);
                break;
            default:
                break;
        }
        prevPovPosition = operatorJoystick.getPOVPosition();

        shooter.moveTurret(-operatorJoystick.getRightX());
    }
}
