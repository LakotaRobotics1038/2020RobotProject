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
    private static Limelight limelight;

    private static NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private static NetworkTable table = tableInstance.getTable("limelight");
    // NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");

    // std::shared_ptr<NetworkTable> table = NetworkTable::GetTable("limelight");
    // float tx = table->GetNumber("tx");
    // float ty = table->GetNumber("ty");

    private double valid_target;
    private double x;
    private double y;
    
    public Limelight() {

    }
    
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

    public void read() {
        valid_target = tv.getDouble(0);
        x = tx.getDouble(0);
        y = ty.getDouble(0);
        System.out.println(valid_target + ", " + x + ", " + y);
    }

    public boolean isTarget() {
        if (valid_target == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public double getXOffset() {
        // if they tell me what to do i wont do it
        // it ha been 5 minutes since they told me to do something
        // i am starting to wonder if they are all idiots. 
        // drew and sam are talking nerd talk
        // sam is slacking but still talking nerd
        // i am very bored and want chocy milk
        // i won the war over the yard stick
        // they still have not noticed              -Shawn Tomas


        return x;
    }

    public double getYOffset() {
        return y;
    }
    

}
