/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.auton;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.auton.commands.AcquireCommand;
import frc.auton.commands.DriveStraightCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AcquisitionAuton extends ParallelCommandGroup {
  /**
   * Creates a new AcquisitionAuton.
   */
  public AcquisitionAuton() {
    super();
    addCommands(
      new AcquireCommand(),
      new DriveStraightCommand(200)
    );
  }
}
