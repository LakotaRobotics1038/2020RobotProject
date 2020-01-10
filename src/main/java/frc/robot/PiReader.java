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
    private final int BAUD_RATE = 9600;

    // Sensors
    

    // Objects
    private static SerialPort piPort;
    private static PiReader PiReader;

    private String[] sensors = { "gyro", "leftEndgameSwitch", "rightEndgameSwitch", "colorSensor"};

    private Map<String, Double> sensorValues = Map.of("gyro", null, "leftEndgameSwitch", null, "rightEndgameSwitch", null, "colorSensor", null);

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

    /**
     * Updates pi values and reads pi serial port
     */
    public void readpi() {
        try {
            while (piPort.getBytesReceived() != 0) {
                piOutput = piPort.readString();
                inputBuffer += piOutput;
            }
            line = "";
            while (inputBuffer.indexOf("\r") != -1) {
                int point = inputBuffer.indexOf("\r");
                line = inputBuffer.substring(0, point);
                if (inputBuffer.length() > point + 1) {
                    inputBuffer = inputBuffer.substring(point + 2, inputBuffer.length());
                } else {
                    inputBuffer = "";
                }
            }
            if (line != "") {
                piDataMap = line.split(",");
                // TODO does this work?
                for (int i = 0; i < piDataMap.length; i++) {
                    sensorValues.put(sensors[i], Double.parseDouble(piDataMap[i]));
                }
                // frontLaserSensorData = Integer.parseInt(piDataMap[0]);
                // rearLaserSensorData = Integer.parseInt(piDataMap[1]);
                // frontLeftLaserSensorData = Integer.parseInt(piDataMap[2]);
                // frontRightLaserSensorData = Integer.parseInt(piDataMap[3]);
                // acquisitionAccelerometerData = Integer.parseInt(piDataMap[4]);
                // scoringAccelerometerData = Integer.parseInt(piDataMap[5]);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        } catch (UncleanStatusException e) {
            System.out.println(e);
        }
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
    public int getGyroVal() {
        return (int) Math.round(sensorValues.get("gyro"));
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public int getLeftEndgameSwitchVal() {
        return (int) Math.round(sensorValues.get("leftEndgameSwitch"));
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public int getRightEndgameSwitchVal() {
        return (int) Math.round(sensorValues.get("rightEndgameSwitch"));
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public int getColorSensorVal() {
        return (int) Math.round(sensorValues.get("colorSensor"));
    }
}
