import java.io.File;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class FollowPath extends Command {
EncoderFollower leftFollower;
EncoderFollower rightFollower;

File leftCSV;
File rightCSV;

Trajectory rightTrajectory;
Trajectory leftTrajectory;

Notifier notifier;

double dt;

double p = 0.8;
double i = 0.;
double d = 0;
double v = 1 / 12;
double a = 0;
public FollowPath(String pathName) {
requires(Robot.chassisSubsystem);

leftCSV = new File("/home/lvuser/deploy/" + pathName + “.left”);
rightCSV = new File("/home/lvuser/deploy/" + pathName + “.right”);

leftTrajectory = Pathfinder.readFromCSV(leftCSV);
rightTrajectory = Pathfinder.readFromCSV(rightCSV);

notifier = new Notifier(new RunProfile());
dt = leftTrajectory.get(0).dt;

System.out.println(“CSV has been locked and loaded”);
}

// Called just before this Command runs the first time
@Override
protected void initialize() {
leftFollower = new EncoderFollower(leftTrajectory);
rightFollower = new EncoderFollower(rightTrajectory);

leftFollower.reset();
rightFollower.reset();

leftFollower.configureEncoder((int)Robot.chassisSubsystem.getEncoderLeft(), 128, 6 / 12);
rightFollower.configureEncoder((int)Robot.chassisSubsystem.getEncoderRight(), 128, 6 / 12);

leftFollower.configurePIDVA( p,  i, d, v, a);
rightFollower.configurePIDVA(p , i, d , v , a);
notifier.startPeriodic(dt);

System.out.println("Initialized");
}

// Called repeatedly when this Command is scheduled to run
@Override
protected void execute() {
}

// Make this return true when this Command no longer needs to run execute()
@Override
protected boolean isFinished() {
System.out.println(“Finished”);
return leftFollower.isFinished() && rightFollower.isFinished();

}

// Called once after isFinished returns true
@Override
protected void end() {
notifier.stop();
Robot.chassisSubsystem.stop();
}

// Called when another command which requires one or more of the same
// subsystems is scheduled to run
@Override
protected void interrupted() {
end();
}
class RunProfile implements java.lang.Runnable{
int segmentNumber = 0;

@Override
public void run(){


double leftOutput = leftFollower.calculate((int)Robot.chassisSubsystem.getEncoderLeft());
double rightOutput = rightFollower.calculate((int) Robot.chassisSubsystem.getEncoderRight());

double gyroHeading = 0;

double desiredHeading = Pathfinder.d2r(leftFollower.getHeading());

double angleDifference = gyroHeading + desiredHeading;

double turn = 0.08 *  (-1. / 80.) * angleDifference;

Robot.chassisSubsystem.tankDrive(rightOutput - turn, leftOutput - turn);

segmentNumber++;