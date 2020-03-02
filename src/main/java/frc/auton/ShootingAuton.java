/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.auton;

import frc.auton.commands.DriveStraightCommand;
import frc.auton.commands.ShootCommand;
import frc.auton.commands.AimCommand;
/**
 * Add your docs here.
 */
public class ShootingAuton extends Auton{


    public ShootingAuton() {
        super();
        addCommands(new AimCommand(), new ShootCommand(), new DriveStraightCommand(65)); //new DriveStraightCommand(65)
    }
}
