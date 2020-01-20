package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;

import frc.robot.ColorSensor1038;
import frc.robot.CANSpark1038;
import frc.robot.Encoder1038;
import frc.robot.PiReader;

public class Spinner implements Subsystem {
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensor1038(i2cPort);
    private final ColorMatch colorMatcher = new ColorMatch();
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
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
            // TODO pi reader isn't going to read color sensor, a java class will
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
        if (match.color == kBlueTarget){
            return "Blue";
        }
        else if (match.color == kRedTarget){
            return "Red";
        }
        else if (match.color == kGreenTarget) {
            return "Green";
        }
        else if (match.color == kYellowTarget){
            return "Yellow";
        }
        else {
            return "Unknown";
        }

    }

    // @Override
    // protected void initDefaultCommand() {
    // // TODO Auto-generated method stub
    // }
}
