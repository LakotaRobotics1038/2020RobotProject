/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystem.Acquisition;
import frc.subsystem.Storage;

public class AcquireCommand extends CommandBase {

    Acquisition acquisition = Acquisition.getInstance();
    Storage storage = Storage.getInstance();

    private static double END_TIME;

    /**
     * Creates a new MoveAcquisitionCommand.
     */
    public AcquireCommand(double endTime) {
        END_TIME = endTime;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        acquisition.toggleAcquisitionPosition();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Timer.getMatchTime() <= 14) {
            storage.periodic();
            acquisition.runBeaterBarFwd();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        acquisition.stopBeaterBar();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Timer.getMatchTime() <= END_TIME;
    }
}
