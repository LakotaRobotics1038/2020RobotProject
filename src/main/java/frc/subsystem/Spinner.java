package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.CANSpark1038;
import frc.robot.ColorSensor1038;
import frc.robot.ColorSensor1038.Colors;

public class Spinner implements Subsystem {
    
    //Variables
    private static Spinner spinner;
    private final int SPINNER_MOTOR_PORT = 51;
    private final double SPINNER_MOTOR_SPEED = .8;
    private boolean rotationEnabled = false;
    private boolean colorEnabled = false;

    //Color Sensor
    private final ColorSensor1038 colorSensor = new ColorSensor1038(I2C.Port.kOnboard);

    //Motors
    private Spark spinnerMotor = new Spark(SPINNER_MOTOR_PORT);

    //Color Variables
    private int colorCount = 0;
    private Colors initialColor;
    private Colors lastColor;
    private final String desiredColor = "R";


    /***
     * Returns the spinner instance created when the robot starts
     * 
     * @return Spinner instance
     */

    public static Spinner getInstance() {
        if (spinner == null) {
            System.out.println("Creating a new spinner");
            spinner = new Spinner();
        }
        return spinner;
    }

    /**
    * Constructs a Spinner Object
    */

    private Spinner() {

    }
    /**
     * Sets rotationEnabled to true and sets other variables necessary to rotate the spinner
     */
    public void setRotationEnabled() {
        if (!colorEnabled) {
            colorCount = 0;
            rotationEnabled = true;
            initialColor = colorSensor.getClosestColor();
            spinnerMotor.set(SPINNER_MOTOR_SPEED);
        }
    }

    /**
     * Sets colorEnabled to true
     */

    public void setcolorEnabled() {
        if (!rotationEnabled) {
            colorEnabled = true;
        }
    }

    /**
     * Returns whether rotation is enabled
     * 
     * @return Current value of rotationEnabled
     */

    public Boolean getRotationEnabled() {
        return rotationEnabled;
    }

    /**
     * Returns whether color is enabled
     * 
     * @return Current value of colorEnabled
     */

    public Boolean getColorEnabled() {
        return colorEnabled;
    }

    /**
     * If rotation is enabled, spin the color wheel 3-5 full revolutions
     * 
     * If color enabled, spin the color wheel to the game specific color
     */
    
    public void spinnerPeriodic() {
        if (rotationEnabled) {
            // To find the current color
            // Color c = colorSensor.getColor();
            // System.out.println(c.red + " " + c.green + " " + c.green);
            Colors current = colorSensor.getClosestColor();
            if (initialColor == current && current != lastColor) {
                colorCount += 1;
            }

            if (colorCount > 8) {
                spinnerMotor.set(0);
            }
            lastColor = current;
        } else if (colorEnabled) {
            String gameData = DriverStation.getInstance().getGameSpecificMessage();
            // TODO Replace desiredColor with gameData
            if (desiredColor == "R" && colorSensor.getClosestColor() == Colors.Red) {
                spinnerMotor.set(0);
            } else if (desiredColor == "G" && colorSensor.getClosestColor() == Colors.Green) {
                spinnerMotor.set(0);
            } else if (desiredColor == "B" && colorSensor.getClosestColor() == Colors.Blue) {
                spinnerMotor.set(0);
            } else if (desiredColor == "Y" && colorSensor.getClosestColor() == Colors.Yellow) {
                spinnerMotor.set(0);
            } else {
                spinnerMotor.set(SPINNER_MOTOR_SPEED);
            }

        }
    }
}