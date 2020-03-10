/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.auton;

import frc.auton.commands.AimCommand;
import frc.auton.commands.ShootCommand;

/**
 * Add your docs here.
 */
public class Shooting5BallAuton extends Auton {
    public Shooting5BallAuton() {
        super();
        
        addCommands(
            new AcquisitionAuton(),
            new AimCommand(),
            new ShootCommand()
        );
    }

}
