package frc.auton.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Limelight;
import frc.robot.Limelight.LEDStates;
import frc.subsystem.Shooter;

public class AimCommand extends CommandBase {
    private Shooter shooter = Shooter.getInstance();
    private Limelight limelight = Limelight.getInstance();

    private boolean turned = false;
    private final double TURRET_SPEED = 0.2;

    private final int TURRET_90_DEGREES = 39500;

    @Override
    public void initialize() {
        limelight.changeLEDStatus(LEDStates.On);
        turned = false;
    }

    @Override
    public void execute() {
        shooter.shootManually(-.55);

        // TODO: Get the limelight working here
        if (shooter.getTurretEncoder() < TURRET_90_DEGREES) {
            shooter.turnTurret(TURRET_SPEED);
        } else {
            shooter.turnTurret(0);
            turned = true;
        }
    }

    @Override
    public void end(boolean interuppted) {
        limelight.changeLEDStatus(LEDStates.Off);
    }

    @Override
    public boolean isFinished() {
        return turned;
    }
}