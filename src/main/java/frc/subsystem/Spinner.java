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
    private final ColorSensor1038 colorSensor = new ColorSensor1038(I2C.Port.kOnboard);

    private final int SPINNER_MOTOR_PORT = 50;
    private final double SPINNER_MOTOR_SPEED = .5;
    // private CANSpark1038 spinnerMotor = new CANSpark1038(SPINNER_MOTOR_PORT,
    // MotorType.kBrushless);
    private Spark spinnerMotor = new Spark(2);
    // private CANEncoder spinnerEncoder = spinnerMotor.getEncoder();
    private boolean rotationEnabled = false;
    private boolean colorEnabled = false;
    private static Spinner spinner;
    private int colorCount = 0;
    private Colors initialColor;
    private Colors lastColor;
    private final String desiredColor = "R";

    public static Spinner getInstance() {
        if (spinner == null) {
            System.out.println("Creating a new spinner");
            spinner = new Spinner();
        }
        return spinner;
    }

    private Spinner() {

    }

    public void setRotationEnabled() {
        if (!colorEnabled) {
            System.out.println("here");
            rotationEnabled = true;
            initialColor = colorSensor.getClosestColor();
            spinnerMotor.set(SPINNER_MOTOR_SPEED);
        }
    }

    public void setcolorEnabled() {
        if (!rotationEnabled) {
            colorEnabled = true;
        }
    }

    public Boolean getRotationEnabled() {
        return rotationEnabled;
    }

    public Boolean getColorEnabled() {
        return colorEnabled;
    }

    public void spinnerPeriodic() {
        if (rotationEnabled) {
            // if current color is startcolor, += counter
            // if counter < 8, stop motor
            Colors current = colorSensor.getClosestColor();
            boolean condition = initialColor == current && current != lastColor;
            System.out.println("init" + initialColor + " current " + current + " last " + lastColor + " count " + colorCount);
            if (condition) {
                colorCount += 1;
            }
            
            if (colorCount >= 8) {
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