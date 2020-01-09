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
    private int frontLaserSensorData = 0;
    private double lineFollowerData = 0;
    private int rearLaserSensorData = 0;
    private int frontLeftLaserSensorData = 0;
    private int frontRightLaserSensorData = 0;
    private int scoringAccelerometerData = 0;
    private int acquisitionAccelerometerData = 0;

    // Objects
    private static SerialPort piPort;
    private static PiReader PiReader;

    private String[] sensors = { "frontLeftLaserSensor", "lineFollower" };

    private Map<String, Double> sensorValues = Map.of("frontLeftLaserSensor", null, "lineFollower", null);

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
     * The front laser looking towards the ground
     * 
     * @return Distance to ground from front bottom laser in cm
     */
    public int getFrontBottomLaserVal() {
        return frontLaserSensorData;
    }

    /**
     * The rear laser looking towards the ground
     * 
     * @return Distance to ground from rear bottom laser in cm
     */
    public int getRearBottomLaserVal() {
        number = rearLaserSensorData;
        long longNumber = Math.round(number);
        int intNumber = Math.toIntExact(longNumber);
        return intNumber;
    }

    /**
     * The front left laser looking forwards
     * 
     * @return Distance to object from front left in cm
     */
    public int getFrontLeftLaserVal() {
        return (int) Math.round(sensorValues.get("frontLeftLaserSensor"));
    }

    /**
     * The front right laser looking forwards
     * 
     * @return Distance to object from front right in cm
     */
    public int getFrontRightLaserVal() {
        return frontRightLaserSensorData;
    }

    /**
     * Position of middle of white tape
     * 
     * @return Middle of white tape as an average
     */
    public double getLineFollowerVal() {
        return lineFollowerData;
    }

    /**
     * Accelerometer on the four bar
     * 
     * @return Angle of scoring arm by calculating from vertical and horizontal
     *         forces
     */
    public int getScoringAccelerometerVal() {
        return scoringAccelerometerData;
    }

    /**
     * Accelerometer on the wrist piece
     * 
     * @return Angle of wrist by calculating from vertical and horizontal forces
     */
    public int getAcqAccelerometerVal() {
        return acquisitionAccelerometerData;
    }
}
