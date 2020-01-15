package frc.subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.CANSpark1038;
import frc.robot.Encoder1038;
import frc.robot.PiReader;

public class Spinner implements Subsystem {
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

                 while (currentRevolutions < 4){
                 spinnerMotor.set(.5);   
                 }
                rotationEnabled = false;
                spinnerMotor.set(0);

        } else if (colorEnabled) {
            String gameData = DriverStation.getInstance().getGameSpecificMessage();
            //TODO pi reader isn't going to read color sensor, a java class will
            if (PiReader.getColorSensorVal() != gameData) {
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

    public Boolean getRotationEnabled(){
        return rotationEnabled;
    }

    public Boolean getColorEnabled(){
        return colorEnabled;
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }
}
