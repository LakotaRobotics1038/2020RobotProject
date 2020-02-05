/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.BufferedReader;
import java.util.Map;

import edu.wpi.first.hal.util.UncleanStatusException;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * Add your docs here.
 */
public class PiReader {
    // Variables
    private String piOutput;
    private String piDataMap[];
    private static String inputBuffer = "";
    private String line;
    private double number = 0;
    private final int BAUD_RATE = 115200;

    // Sensors
    private static int gyro;
    private static boolean leftEndgameSwitch;
    private static boolean rightEndgameSwitch;
    private static String colorSensor;
    private static boolean firstBallSensor;
    private static boolean lastBallSensor;

    // Objects
    private static SerialPort piPort;
    private static PiReader PiReader;

    /**
     * Returns the pi instance created when the robot starts
     * 
     * @return pi instance
     */
    public static PiReader getInstance() {
        if (PiReader == null) {
            PiReader = new PiReader();
        }
        return PiReader;
    }

    /**
     * Initializes the pi reader (empty currently)
     */
    private PiReader() {

    }

    /**
     * Creates serial port listener
     */
    public void initialize() {
        piPort = new SerialPort(BAUD_RATE, SerialPort.Port.kMXP);
        System.out.println("Created new pi reader");
    }

    public void testPi() {
        try {
            piPort.writeString("silly");
            //System.out.println(piPort.readString() + " oops");
        } catch(UncleanStatusException err) {
            System.out.println(err);
        }
    }

    /**
     * Updates pi values and reads pi serial port
     */
    public void readpi() {
        try {
            piPort.writeString("silly");
            while (piPort.getBytesReceived() != 0) {
                piOutput = piPort.readString();
                if (piOutput != "") {
                    piDataMap = piOutput.split(",");
                    gyro = Integer.parseInt(piDataMap[0]);
                    leftEndgameSwitch = Boolean.parseBoolean(piDataMap[1]);
                    rightEndgameSwitch = Boolean.parseBoolean(piDataMap[2]);
                    colorSensor = (piDataMap[3]);
                    firstBallSensor = Boolean.parseBoolean(piDataMap[4]);
                    lastBallSensor = Boolean.parseBoolean(piDataMap[5]);

                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        } catch (UncleanStatusException e) {
            System.out.println(e);
        }
    }

    public void recalibrateGyro() {
        piPort.writeString("Recalibrate dat saucy boi, bitch");
    }

    public void resetGyro() {
        piPort.writeString("Reset dat saucy boi, bitch");
    }

    /**
     * Closes serial port listener
     */
    public void stopSerialPort() {
        System.out.println("im gonna close it");
        piPort.close();
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public static int getGyroVal() {
        return gyro;
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public static boolean getLeftEndgameSwitchVal() {
        return leftEndgameSwitch;
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public static Boolean getRightEndgameSwitchVal() {
        return rightEndgameSwitch;
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public static String getColorSensorVal() {
        return colorSensor;
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public static Boolean isFirstBall() {
        return firstBallSensor;
    }
    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public static Boolean isLastBall() {
        return lastBallSensor;
    }
}
