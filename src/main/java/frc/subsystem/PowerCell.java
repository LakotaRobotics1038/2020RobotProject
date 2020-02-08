/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//TODO switch pireader values to digital inputs
package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.PiReader;
import frc.robot.CANSpark1038;
import edu.wpi.first.wpilibj.DigitalInput;

public class PowerCell {
    // ports
    private final int shuttleMotorPort = 62;
    private final int photoEyeStartPort = 0;
    private final int photoEyeEndPort = 0;
    
    // shuttle motor and speed
    private CANSpark1038 shuttleMotor = new CANSpark1038(shuttleMotorPort, MotorType.kBrushless);
    private final static double shuttleMotorSpeed = 0.5;
    
    //declares powercell
    private static PowerCell powerCell;

    //photoeyes
    private DigitalInput photoEyeStart = new DigitalInput(photoEyeStartPort);
    private DigitalInput photoEyeEnd = new DigitalInput(photoEyeEndPort);

    /**
     * returns the powercell instance when the robot starts
     * 
     * @return powercell instance
     */
    public static PowerCell getInstance() {
        if(powerCell == null) {
            System.out.println("creating a new powercell");
            powerCell = new PowerCell();
        }
        return powerCell;
    }

    /**
     * runs the ball storage
     */
    public void ballsPeriodic() {
        if(photoEyeStart.get())//see ball at start sensor
        {
            if(photoEyeEnd.get())//dont see ball at end sensor
            {
                shuttleMotor.set(shuttleMotorSpeed);
            }
        }
        else
        {
            shuttleMotor.set(0);
        }
      
    }

    /**
     * feeds the shooter
     * 
     * @param power how fast to feed the shooter
     */
    public void feedShooter(double power){
        shuttleMotor.set(power);
    }
}
