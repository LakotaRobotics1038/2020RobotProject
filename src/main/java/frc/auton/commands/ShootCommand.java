package frc.auton.commands;
import frc.subsystem.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Shooter
 */
public class ShootCommand extends CommandBase {
    private Shooter shooterInstance = Shooter.getInstance();

    public ShootCommand() {
        shooterInstance.enable();
    }

    @Override
    public void execute() {
        shooterInstance.periodic();
    }

    @Override
    public void end(boolean interuppted) {
        if(interuppted) {
        }
        
        shooterInstance.disable();
    }
}