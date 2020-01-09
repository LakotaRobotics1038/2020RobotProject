package frc.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Joystick1038;
import frc.robot.CANSpark1038;


public class Endgame extends Subsystem {
    private final int endgameMotorPort = 54;
    private CANSpark1038 endgameMotor = new CANSpark1038(endgameMotorPort, MotorType.kBrushless);
    private static Endgame endgame;
    private boolean isLifting = false;
    private boolean isAdjusting = false;


    private static Endgame getInstance() {
        if (endgame == null){
            System.out.println("Creating a new Endgame");
            endgame = new Endgame();
        }
        return endgame;
    }

    public void endgamePeriodic(){
        if (isLifting){
            //TODO Lifts the Robot
        }
        else if (isAdjusting){
            //TODO Adjust the Robot Position on

            //Change Limit Switch when Drew/Sam get Done with Class 
            while (limitSwitch.get()>0){
                endgameMotor.set(.5);
            }

            while (limitSwitch.get()<0){
                endgameMotor.set(-.5);
            }
        }
    }    


    public void setIsLifting(){
        if(!isAdjusting){
            isLifting = true;
        }
    }

    public void setIsAdjusting(){
        if(!isLifting){
            isAdjusting = true;
        }

    }
    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub

    }
}