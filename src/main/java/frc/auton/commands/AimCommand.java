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

    public AimCommand() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        
      limelight.turnLEDsOn();
        shooterInstance.move();
        System.out.println(limelight.canSeeTarget());
    }

    @Override
    public void end(boolean interuppted) {
        
        limelight.turnLEDsOff();
    }

    @Override
    public boolean isFinished() {
        return limelight.canSeeTarget() && shooterInstance.isFinished();
    }
}