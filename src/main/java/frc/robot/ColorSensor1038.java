package frc.robot;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensor1038 extends ColorSensorV3 {
    private final ColorMatch colorMatcher;

    private final Color BLUE_TARGET = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color GREEN_TARGET = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color RED_TARGET = ColorMatch.makeColor(0.413, 0.398, 0.189);
    private final Color YELLOW_TARGET = ColorMatch.makeColor(0.309, 0.542, 0.150);

    public enum Colors {
        Red, Blue, Green, Yellow, Unknown
    }

    public ColorSensor1038(Port port) {
        super(port);
        colorMatcher = new ColorMatch();
        colorMatcher.addColorMatch(BLUE_TARGET);
        colorMatcher.addColorMatch(GREEN_TARGET);
        colorMatcher.addColorMatch(RED_TARGET);
        colorMatcher.addColorMatch(YELLOW_TARGET);
    }

    public Colors getClosestColor() {
        Color detectedColor = this.getColor();

        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        Colors color;
        if (match.color == BLUE_TARGET) {
            color = Colors.Blue;
        } else if (match.color == RED_TARGET) {
            color = Colors.Red;
        } else if (match.color == GREEN_TARGET) {
            color = Colors.Green;
        } else if (match.color == YELLOW_TARGET) {
            color = Colors.Yellow;
        } else {
            color = Colors.Unknown;
        }
        return color;
    }

}