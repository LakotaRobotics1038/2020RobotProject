package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class Limelight {
    // LimeLight instance
    private static Limelight limelight;

    // Network table
    private static NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private static NetworkTable table = tableInstance.getTable("limelight");

    // Network table values
    private final NetworkTableEntry tv = table.getEntry("tv");
    private final NetworkTableEntry tx = table.getEntry("tx");
    private final NetworkTableEntry ty = table.getEntry("ty");

    private double valid_target;
    private double x;
    private double y;

    // offset default value
    private final int defaultOffset = 0;

    public enum LEDStates {
        On(0), Off(1);
        private int value; 
        private LEDStates(final int value) { this.value = value; }
    };

    private Limelight() {
       changeLEDStatus(LEDStates.Off);
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
            } catch (final NullPointerException e) {
                System.out.println("uh-oh " + e);
            }
        }
        return limelight;
    }

    /**
     * reads the values from the network table
     */
    public void read() {
        valid_target = tv.getDouble(defaultOffset);
        x = tx.getDouble(defaultOffset);
        y = ty.getDouble(defaultOffset);
        // System.out.println(valid_target + ", " + x + ", " + y);
    }

    /**
     * tells if robot has acquired the target
     * 
     * @return whether or not the robot has a target
     */
    public boolean canSeeTarget() {
        return valid_target == 1;
    }

    /**
     * how far off center horizontally the robot is
     */
    public double getXOffset() {
        x = tx.getDouble(defaultOffset);
        return x;
    }

    /**
     * returns how far from center vertically the robot is
     * @return distance from center vertically
     */
    public double getYOffset() {
        /* if they tell me what to do i wont do it	
         * it ha been 5 minutes since they told me to do something	
         * i am starting to wonder if they are all idiots. 	
         * drew and sam are talking nerd talk	
         * sam is slacking but still talking nerd	
         * i am very bored and want chocy milk	
         * i won the war over the yard stick	
         * they still have not noticed              -Shawn Tomas
         */
        y = ty.getDouble(defaultOffset);
        return y;
    }

    public void changeLEDStatus(final LEDStates state) {
        table.getEntry("ledMode").setDouble(state.value);
    }

    public double getMotorPower() {
        double power = ty.getDouble(defaultOffset);
        if(power < -13) {       //Red Zone
            return power * -.005 + .49;
        }
        else if(power < -8) {  //Blue Zone
            return power * -.002 + .49;
        }
        else if(power < 2) {  //Yellow Zone
            return power * -.0025 + .49;
        }
        else {                 //Green Zone
            return power * .03 + .49;
        }
    }
    
    public double getShooterSetpoint() {
        final double setpoint = ty.getDouble(defaultOffset);
        return (setpoint * -250 + 31000)/(410.00);
    }
}
