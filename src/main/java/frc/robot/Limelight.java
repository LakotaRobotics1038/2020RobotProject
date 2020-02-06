/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class Limelight {
    //LimeLight instance
    private static Limelight limelight;

    //network table
    private static NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private static NetworkTable table = tableInstance.getTable("limelight");
    
    //network table values
    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    private double valid_target;
    private double x;
    private double y;

    //offset default value
    private int defaultOffset = 0;
    
    public Limelight() {

    }

    /**
     * returns limelight instance when robot is turned on
     * 
     * @return the limelight instance
     */
    public static Limelight getInstance() {
        if (limelight == null) {
            System.out.println("Creating limelight");
            try {
                limelight = new Limelight();
            } catch(NullPointerException e){
                System.out.println("uh-oh " + e);
            }
        }
        return limelight;
    }

    public void initialize() {
    }

    /**
     * reads the values from the network table
     */
    public void read() {
        valid_target = tv.getDouble(defaultOffset);
        x = tx.getDouble(defaultOffset);
        y = ty.getDouble(defaultOffset);
        System.out.println(valid_target + ", " + x + ", " + y);
    }

    /**
     * tells if robot has acquired the target
     * 
     * @return whether or not the robot has a target
     */
    public boolean isTarget() {
        if (valid_target == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * how far off center horizontally the robot is
     */
    public double getXOffset() {
        // if they tell me what to do i wont do it
        // it ha been 5 minutes since they told me to do something
        // i am starting to wonder if they are all idiots. 
        // drew and sam are talking nerd talk
        // sam is slacking but still talking nerd
        // i am very bored and want chocy milk
        // i won the war over the yard stick
        // they still have not noticed              -Shawn Tomas

        x = tx.getDouble(defaultOffset);
        return x;
    }

    /**
     * returns how far from center vertically the robot is
     * @return distance from center vertically
     */
    public double getYOffset() {
        y = ty.getDouble(defaultOffset);
        return y;
    }
    

}
