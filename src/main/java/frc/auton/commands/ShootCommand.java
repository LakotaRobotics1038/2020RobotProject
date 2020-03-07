package frc.auton.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import frc.subsystem.Shooter;

public class ShootCommand extends CommandBase {
    private Shooter shooterInstance = Shooter.getInstance();

    private final double END_TIME = 2;

    @Override
    public void execute() {
        System.out.println(Timer.getMatchTime());
        shooterInstance.feedBall();
    }

    @Override
    public void end(boolean interuppted) {
        shooterInstance.shootManually(0);
        shooterInstance.noFeedBall();
    }

    @Override
    public boolean isFinished() {
        return Timer.getMatchTime() <= END_TIME;
    }
}