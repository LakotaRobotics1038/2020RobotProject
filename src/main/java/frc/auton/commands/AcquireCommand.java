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
<<<<<<< HEAD

=======
>>>>>>> 88fd74587ed9aefdd639a2860d31a10134d83af7

public class AcquireCommand extends CommandBase {
  // private double START_TIME = 0;

  Acquisition acquisition = Acquisition.getInstance();
  Storage storage = Storage.getInstance();
<<<<<<< HEAD
  

  private final double END_TIME;
=======
  Timer timer = new Timer();

  private final double END_TIME;
  private final double START_TIME;
>>>>>>> 88fd74587ed9aefdd639a2860d31a10134d83af7
  /**
   * Creates a new MoveAcquisitionCommand.
   */
  public AcquireCommand(double endTime) {
<<<<<<< HEAD
    
    // START_TIME = Timer.getMatchTime();
    // System.out.println(START_TIME);
=======
    START_TIME = Timer.getFPGATimestamp();
>>>>>>> 88fd74587ed9aefdd639a2860d31a10134d83af7
    END_TIME = endTime;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    acquisition.down();
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    storage.periodic();
    acquisition.runBeaterBarFwd();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    acquisition.stopBeaterBar();
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
<<<<<<< HEAD
    //return Timer.getMatchTime() - START_TIME <= END_TIME;
    return Timer.getMatchTime() >= END_TIME;
=======
    //return (Timer.getFPGATimestamp() - START_TIME) <= END_TIME;
    //boolean finished = Timer.hasPeriodPassed(END_TIME);
    return (timer.get() >= END_TIME);
    //Timer.getFPGATimestamp()
>>>>>>> 88fd74587ed9aefdd639a2860d31a10134d83af7
  }
}
