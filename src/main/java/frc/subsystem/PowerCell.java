/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystem;

import javax.lang.model.util.ElementScanner6;

/**
 * Add your docs here.
 */
public class PowerCell {
    private boolean haveBall = false;
    private int balls = 5;
    
    public void haveFiveBall() {
        haveBall = true;
        if(one empty) {
            balls = 1;
        }
        else if(two empty) {
            balls = 2;
        }
        else if(three empty) {
            balls = 3;
        }
        else if(four empty) {
            balls = 4;
        }
        else {
            balls = 5;
        }

    }

    public void ballsPeriodic() {
        if(haveBall = true) {
            if(balls==4) {
                if(limit 4 notactive) {
                    run motor 5
                }
                else {
                    haveBall = false;
                    turn off motors
                }
            }    
            else if(balls==3) {
                if(limit 3 notactive) {
                    run motor 4,5
                }
                else {
                    haveBall = false;
                    turn off motors
                }
            }
            else if(balls==2) {
                if(limit 2 notactive) {
                    run motor 3,4,5
                }
                else {
                    haveBall = false;
                    turn off motors
                }
            }
            else if(balls==1) {
                if(limit 1 notactive) {
                    run motor 2,3,4,5
                }
                else {
                    haveBall = false;
                    turn off motors
                }
            }
            else if(balls==5) {
                haveBall = false;
            }
        }
      
    }
}
