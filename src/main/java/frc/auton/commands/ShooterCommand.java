package frc.auton.commands;
import frc.subsystem.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Shooter
 */
public class ShooterCommand extends CommandBase {
    private Shooter shooterInstance = Shooter.getInstance();
    public void execute() {
            shooterInstance.feedBall();
    }
}