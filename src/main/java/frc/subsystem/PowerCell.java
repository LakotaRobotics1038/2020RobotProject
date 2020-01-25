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
    private CANSpark1038 motor2 = new CANSpark1038(0, MotorType.kBrushed);
    private CANSpark1038 motor3 = new CANSpark1038(0, MotorType.kBrushed);
    private CANSpark1038 motor4 = new CANSpark1038(0, MotorType.kBrushed);
    private CANSpark1038 motor5 = new CANSpark1038(0, MotorType.kBrushed);
    //private PiReader piReader = PiReader.getInstance();

    public static PowerCell getInstance() {
        if(powerCell == null) {
            System.out.println("creating a new powercell");
            powerCell = new PowerCell();
        }
        return powerCell;
    }
    
    public void haveFiveBall() {
        haveBall = true;
        if(!PiReader.getFirstBallSwitchVal()) {
            balls = 1;
        }
        else if(!PiReader.getSecondBallSwitchVal()) {
            balls = 2;
        }
        else if(!PiReader.getThirdBallSwitchVal()) {
            balls = 3;
        }
        else if(!PiReader.getFourthBallSwitchVal()) {
            balls = 4;
        }
        else {
            balls = 5;
        }

    }

    public void ballsPeriodic() {
        if(haveBall = true) {
            if(balls==4) {
                if(!PiReader.getFourthBallSwitchVal()) {
                    //run motor 5
                    motor5.set(.5);
                }
                else {
                    haveBall = false;
                    motor5.set(0);
                }
            }    
            else if(balls==3) {
                if(!PiReader.getThirdBallSwitchVal()) {
                    //run motor 4,5
                    motor4.set(.5);
                    motor5.set(.5);
                }
                else {
                    haveBall = false;
                    motor4.set(0);
                    motor5.set(0);
                }
            }
            else if(balls==2) {
                if(!PiReader.getSecondBallSwitchVal()) {
                    //run motor 3,4,5
                    motor3.set(.5);
                    motor4.set(.5);
                    motor5.set(.5);
                }
                else {
                    haveBall = false;
                    //turn off motors
                    motor3.set(0);
                    motor4.set(0);
                    motor5.set(0);
                }
            }
            else if(balls==1) {
                if(!PiReader.getFirstBallSwitchVal()) {
                    //run motor 2,3,4,5
                    motor2.set(.5);
                    motor3.set(.5);
                    motor4.set(.5);
                    motor5.set(.5);
                }
                else {
                    haveBall = false;
                    //turn off motors
                    motor2.set(0);
                    motor3.set(0);
                    motor4.set(0);
                    motor5.set(0);
                }
            }
            else if(balls==5) {
                haveBall = false;
            }
        }
      
    }
}
