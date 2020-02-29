package frc.auton.commands;

import frc.subsystem.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

/**
 * Shooter
 */
public class ShootCommand extends CommandBase {
    private Shooter shooterInstance = Shooter.getInstance();

    public ShootCommand() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println(Timer.getMatchTime());
        shooterInstance.feedBall();
        shooterInstance.shootManually(-.6);
        
      

    }

    @Override
    public void end(boolean interuppted) {


        shooterInstance.goToCrashPosition();
        shooterInstance.shootManually(0);
    }

    @Override
    public boolean isFinished() {
        return Timer.getMatchTime() <= 2;
    }
}