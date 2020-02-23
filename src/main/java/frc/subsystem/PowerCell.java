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
    private final int photoEyeStartPort = 5;
    private final int photoEyeEndPort = 6;
    
    // shuttle motor and speed
    private CANSpark1038 shuttleMotor = new CANSpark1038(shuttleMotorPort, MotorType.kBrushless);
    private final static double shuttleMotorSpeed = -0.4; //negative is forward
    
    //declares powercell
    private static PowerCell powerCell;

    //photoeyes
    // private DigitalInput photoEyeStart = new DigitalInput(photoEyeStartPort);
    // private DigitalInput photoEyeEnd = new DigitalInput(photoEyeEndPort);

    //manual drive
    private boolean manualStorageForward = false;
    private boolean manualStorageReverse = false;

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
        if(!manualStorageForward && !manualStorageReverse){
            // if(!photoEyeStart.get())//see ball at start sensor
            // {
            //     if(photoEyeEnd.get())//dont see ball at end sensor
            //     {
            //         shuttleMotor.set(shuttleMotorSpeed);
            //     }
            // }
            // else
            // {
                shuttleMotor.set(0);
            // }
        }
        else if(manualStorageForward) {
            shuttleMotor.set(shuttleMotorSpeed);
        }
        else{
            shuttleMotor.set(-shuttleMotorSpeed);
        }
    }

    // public void test() {
    //     if (!photoEyeStart.get()) {
    //         System.out.println("has thing");
    //     }
    //     else {
    //         System.out.println("no thing");
    //     }
    // }

    public void enableManualStorage(boolean forward){
        if(forward) {
            manualStorageForward = true;
        }
        else {
            manualStorageReverse = true;
        }
    }
    
    public void disableManualStorage(){
        manualStorageReverse = false;
        manualStorageForward = false;
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
