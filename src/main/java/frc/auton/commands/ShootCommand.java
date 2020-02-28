package frc.auton.commands;
import frc.robot.Limelight;
import frc.subsystem.PowerCell;
import frc.subsystem.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;;

/**
 * Shooter
 */
public class ShootCommand extends SequentialCommandGroup {
    private Shooter shooterInstance = Shooter.getInstance();
    private final int SHOOTER_TARGET_SPEED = 0;
    private final Limelight limelight = Limelight.getInstance();

    public ShootCommand() {
        shooterInstance.enable();
    }

    @Override
    public void execute() {
        shooterInstance.move();
        if(limelight.canSeeTarget()){
            shooterInstance.shootManually(-.6);
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