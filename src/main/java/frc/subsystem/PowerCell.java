/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.CANSpark1038;
import edu.wpi.first.wpilibj.DigitalInput;

public class PowerCell {
    // ports
    private final int shuttleMotorPort = 62;
    private final int laserStartPort = 6;
    private final int laserEndPort = 5;
    private final int SHUTTLE_MOTOR_ENCODER_COUNTS = 47;

    // shuttle motor and speed
    private CANSpark1038 shuttleMotor = new CANSpark1038(shuttleMotorPort, MotorType.kBrushless);
    private CANEncoder shuttleMotorEncoder = new CANEncoder(shuttleMotor);
    private final static double shuttleMotorSpeed = 1.0;

    // declares powercell
    private static PowerCell powerCell;

    // photoeyes
    private DigitalInput laserStart = new DigitalInput(laserStartPort);
    private DigitalInput laserEnd = new DigitalInput(laserEndPort);

    // manual drive
    private boolean manualStorageForward = false;
    private boolean manualStorageReverse = false;

    public enum ManualStorageModes {
        Forward, Reverse
    }

    /**
     * returns the powercell instance when the robot starts
     * 
     * @return powercell instance
     */
    public static PowerCell getInstance() {
        if (powerCell == null) {
            System.out.println("creating a new powercell");
            powerCell = new PowerCell();
        }
        return powerCell;
    }

    private PowerCell() {
        shuttleMotor.setInverted(true);
        shuttleMotorEncoder.setPosition(SHUTTLE_MOTOR_ENCODER_COUNTS + 500);
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
        // System.out.println("kas" + laserStart.get() + " " + laserEnd.get());
        // System.out.println("sto" + manualStorageForward + " " +
        // manualStorageReverse);
        // System.out.println(shuttleMotorEncoder.getPosition());
        if (!manualStorageForward && !manualStorageReverse) {
            if (shuttleMotorEncoder.getPosition() < SHUTTLE_MOTOR_ENCODER_COUNTS && !laserEnd.get())// see ball at start                                                                                   // sensor
            {
                // System.out.println(shuttleMotorEncoder.getPosition());
                shuttleMotor.set(shuttleMotorSpeed);
            } else if (laserStart.get() && !laserEnd.get()) {
                shuttleMotorEncoder.setPosition(0);
            } else {
                shuttleMotor.set(0);
            }
        } else if (manualStorageForward) {
            shuttleMotor.set(shuttleMotorSpeed);
            shuttleMotorEncoder.setPosition(SHUTTLE_MOTOR_ENCODER_COUNTS + 500);
        } else if (manualStorageReverse) {
            shuttleMotor.set(-shuttleMotorSpeed);
            shuttleMotorEncoder.setPosition(SHUTTLE_MOTOR_ENCODER_COUNTS + 500);
        }
    }
}
