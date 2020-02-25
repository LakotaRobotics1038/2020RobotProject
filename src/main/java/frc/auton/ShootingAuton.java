/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
/**
 * Add your docs here.
 */
public class ShootingAuton extends Auton{

    private static double DIST_TO_BASELINE_FROM_DS_WALL = 2;

    public ShootingAuton() {
        super();
    }
    public static SequentialCommandGroup select() {
        group.addCommands(new DriveStraightCommand(DIST_TO_BASELINE_FROM_DS_WALL), new ShootCommand());
        return group;
    }
}
