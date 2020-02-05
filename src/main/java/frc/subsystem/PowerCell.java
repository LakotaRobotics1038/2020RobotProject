/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystem;

import javax.lang.model.util.ElementScanner6;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.PiReader;
import frc.robot.CANSpark1038;

/**
 * Add your docs here.
 */
public class PowerCell {
    private static PowerCell powerCell;
    private boolean haveBall = false;
    private int balls = 5;
    //private CANSpark1038 motor1 = new CANSpark1038(0, MotorType.kBrushed);
    //private CANSpark1038 motor2 = new CANSpark1038(0, MotorType.kBrushed);
    //private CANSpark1038 motor3 = new CANSpark1038(0, MotorType.kBrushed);
    private CANSpark1038 shuttleMotor = new CANSpark1038(0, MotorType.kBrushed);
    //private CANSpark1038 motor5 = new CANSpark1038(0, MotorType.kBrushed);
    private PiReader piReader = PiReader.getInstance();

    public static PowerCell getInstance() {
        if(powerCell == null) {
            System.out.println("creating a new powercell");
            powerCell = new PowerCell();
        }
        return powerCell;
    }

    public void ballsPeriodic() {
        if(PiReader./*get boi*/isFirstBall())//see ball at start sensor
        {
            if(PiReader.isLastBall())//dont see ball at end sensor
            {
                shuttleMotor.set(.5);
            }
        }
        else
        {
            shuttleMotor.set(0);
        }
      
    }
    public void feedShooter(double power){
        shuttleMotor.set(power);
    }
}
