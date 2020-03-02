package frc.auton.commands;
import frc.robot.Limelight;
import frc.subsystem.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Shooter
 */
public class AimCommand extends CommandBase {
    private Shooter shooterInstance = Shooter.getInstance();
    private final Limelight limelight = Limelight.getInstance();
    private boolean turned = false;
    public AimCommand() {
    }

    @Override
    public void initialize() {
        turned = false;
    }

    @Override
    public void execute() {
        
        shooterInstance.shootManually(-.55);
      if(shooterInstance.getTurretEncoder() < 39500){
            shooterInstance.turnTurret(0.2);
      } else {
            shooterInstance.turnTurret(0);
            System.out.println("Finished execute");
            turned = true;
      }      
    }

    @Override
    public void end(boolean interuppted) {
        
        limelight.turnLEDsOff();
    }

    @Override
    public boolean isFinished() {
        return turned;
    }
}