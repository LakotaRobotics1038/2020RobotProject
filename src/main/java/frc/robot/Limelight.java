package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class Limelight {
    // LimeLight instance
    private static Limelight limelight;

    // Network table
    private static NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private static NetworkTable table = tableInstance.getTable("limelight");

    // Network table values
    private NetworkTableEntry tv = table.getEntry("tv");
    private NetworkTableEntry tx = table.getEntry("tx");
    private NetworkTableEntry ty = table.getEntry("ty");

    private double valid_target;
    private double x;
    private double y;

    // offset default value
    private int defaultOffset = 0;

    public enum LEDStates {
        On(0), Off(1);
        private int value; 
        private LEDStates(int value) { this.value = value; }
    };

    private Limelight() {
       changeLEDStatus(LEDStates.Off);
    }


    public static Limelight getInstance() {
        if (limelight == null) {
            System.out.println("Creating limelight");
            try {
                limelight = new Limelight();
            } catch (NullPointerException e) {
                System.out.println("uh-oh " + e);
            }
        }
        return limelight;
    }

    /*-----------------------------------------------------------------------------*/
    /* Clue #15:                                                                   */
    /* Find the spot where we use the limelight values with PID to aim the turret. */
    /*-----------------------------------------------------------------------------*/
    public void read() {
        valid_target = tv.getDouble(defaultOffset);
        x = tx.getDouble(defaultOffset);
        y = ty.getDouble(defaultOffset);
    }

    public boolean canSeeTarget() {
        return valid_target == 1;
    }

    public double getXOffset() {
        x = tx.getDouble(defaultOffset);
        return x;
    }

    public double getYOffset() {
        /*--------------------------------------------------*/
        /* Clue #17:                                        */
        /* Read the entry below and you're a winner!!!!!!!! */
        /*--------------------------------------------------*/
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

    public void changeLEDStatus(LEDStates state) {
        table.getEntry("ledMode").setDouble(state.value);
    }

    public double getMotorPower() {
        double power = ty.getDouble(defaultOffset);
        return power * -.00417 + .55;
    }
    
    public double getShooterSetpoint() {
        double setpoint = ty.getDouble(defaultOffset);
        return (setpoint * -250 + 31000)/(4100.00);
    }
}
