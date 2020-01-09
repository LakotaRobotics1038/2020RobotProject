package frc.subsystem;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Joystick1038;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CANSpark1038;

public class Shooter extends Subsystem {
    // TODO change port numbers
    private final int SHOOTER_MOTOR_1_PORT = 0; 
    private final int SHOOTER_MOTOR_2_PORT = 1; 
    private CANSpark1038 shooterMotor1 = new CANSpark1038(SHOOTER_MOTOR_1_PORT, MotorType.kBrushless);
    private CANSpark1038 shooterMotor2 = new CANSpark1038(SHOOTER_MOTOR_2_PORT, MotorType.kBrushless);
    private static Shooter shooter;

    public static Shooter getInstance() {
        if(shooter == null) {
            System.out.println("creating a new shooter");
            shooter = new Shooter();
        }
        return shooter;
    }
    
    private Shooter() {
        
    }
   
   public void setShooterEnabled() {

   }


   
   
    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub

    }
}