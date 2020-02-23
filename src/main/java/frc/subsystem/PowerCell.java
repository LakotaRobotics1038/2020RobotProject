/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//TODO switch pireader values to digital inputs
package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.CANSpark1038;
import edu.wpi.first.wpilibj.DigitalInput;

public class PowerCell {
    // ports
    private final int shuttleMotorPort = 62;
    private final int laserStartPort = 6;
    private final int laserEndPort = 5;

    // shuttle motor and speed
    private CANSpark1038 shuttleMotor = new CANSpark1038(shuttleMotorPort, MotorType.kBrushless);
    private final static double shuttleMotorSpeed = -0.4; // negative is forward

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
            if (laserStart.get())// see ball at start sensor
            {
                if (!laserEnd.get())// dont see ball at end sensor
                {
                    shuttleMotor.set(shuttleMotorSpeed);
                }
            } else {
                shuttleMotor.set(0);
            }
        } else if (manualStorageForward) {
            shuttleMotor.set(shuttleMotorSpeed);
        } else if (manualStorageReverse) {
            shuttleMotor.set(-shuttleMotorSpeed);
        }
    }
}
