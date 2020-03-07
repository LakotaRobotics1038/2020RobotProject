package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.CANSpark1038;
import frc.robot.ColorSensor1038;

public class Spinner implements Subsystem {
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensor1038(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();
    private final Color kBlueMinimumTarget = ColorMatch.makeColor(0.1, 0.4, 0.4);
    private final Color kGreenMinimumTarget = ColorMatch.makeColor(0.18, 0.5, 0.2);
    private final Color kRedMinimumTarget = ColorMatch.makeColor(0.5, 0.2, 0.05);
    private final Color kYellowMinimumTarget = ColorMatch.makeColor(0.3, 0.45, 0.05);
    private final Color kBlueMaximumTarget = ColorMatch.makeColor(0.2, 0.5, 0.5);
    private final Color kGreenMaximumTarget = ColorMatch.makeColor(0.28, 0.6, 0.3);
    private final Color kRedMaximumTarget = ColorMatch.makeColor(0.6, 0.3, 0.15);
    private final Color kYellowMaximumTarget = ColorMatch.makeColor(0.4, 0.55, 0.15);
    private final String colorString = "Unknown"; 
    Color detectedColor = colorSensor.getColor();
    ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);


    private final int SPINNER_MOTOR_PORT = 50;
    private CANSpark1038 spinnerMotor = new CANSpark1038(SPINNER_MOTOR_PORT, MotorType.kBrushless);
    private CANEncoder spinnerEncoder = spinnerMotor.getEncoder();
    private boolean rotationEnabled = false;
    private boolean colorEnabled = false;
    private static Spinner spinner;
    private double startingSpinnerCount = spinnerEncoder.getPosition();
    private double currentCounts;
    private double currentRevolutions;

    public static Spinner getInstance() {
        if (spinner == null) {
            System.out.println("creating a new spinner");
            spinner = new Spinner();
        }
        return spinner;
    }

    private Spinner() {

    }

    public void spin() {
        spinnerMotor.set(.1);
    }

    public void spinnerPeriodic() {
        double currentCounts = spinnerEncoder.getPosition() - startingSpinnerCount;
        double currentRevolutions = currentCounts / spinnerEncoder.getCountsPerRevolution();
        if (rotationEnabled) {

            while (currentRevolutions < 4) {
                spinnerMotor.set(.5);
            }
            rotationEnabled = false;
            spinnerMotor.set(0);

        } else if (colorEnabled) {
            String gameData = DriverStation.getInstance().getGameSpecificMessage();
            if (getCurrentColor() != gameData) {
                spinnerMotor.set(.5);
            } else {
                rotationEnabled = false;
                spinnerMotor.set(0);
            }
        }
    }

    public void setRotationEnabled() {
        if (!colorEnabled) {
            rotationEnabled = true;
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

    public String getCurrentColor(){
        if (0.07 < colorSensor.getRed() && colorSensor.getRed() < 0.17){
            return "Blue";
        }
        else if (0.5 < colorSensor.getRed() && colorSensor.getRed() < 0.6){
            return "Red";
        }
        else if (0.22 < colorSensor.getRed() && colorSensor.getRed() < 0.32) {
            return "Green";
        }
        else if (0.35 < colorSensor.getRed() && colorSensor.getRed() < 0.45){
            return "Yellow";
        }
        else {
            return "Unknown";
        }
    }
}
