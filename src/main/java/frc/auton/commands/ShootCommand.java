package frc.auton.commands;
import frc.subsystem.PowerCell;
import frc.subsystem.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Shooter
 */
public class ShootCommand extends CommandBase {
    private Shooter shooterInstance = Shooter.getInstance();
    private final int SHOOTER_TARGET_SPEED = 0;

    public ShootCommand() {
        shooterInstance.enable();
    }

    @Override
    public void execute() {
        shooterInstance.move();
        shooterInstance.shootManually(-.6);
        if(shooterInstance.getShooterSpeed() >= SHOOTER_TARGET_SPEED) {
            shooterInstance.feedBall();
        }
    }

    @Override
    public void end(boolean interuppted) {
        if(interuppted) {
        }
        
        shooterInstance.disable();
    }
}