package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;

public class ColorSensor1038 extends ColorSensorV3 {

    public ColorSensor1038(Port port) {
        super(port);
        // TODO Auto-generated constructor stub
    }

    public boolean isBlue(){
        RawColor color = getRawColor();
        return color.blue == blueValue;
        //TODO change color value

    }

    public boolean isGreen(){
        RawColor color = getRawColor();
        return color.green == greenValue;
        //TODO change color value

    }

    public boolean isYellow(){
        RawColor color = getRawColor();
        return color.red && color.green == yellowValue;
        //TODO change color value
    }
    public boolean isRed(){
        RawColor color = getRawColor();
        return color.red == redValue;
        //TODO change color value
    }

}