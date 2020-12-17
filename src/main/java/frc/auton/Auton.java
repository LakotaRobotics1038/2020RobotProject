package frc.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Auton extends SequentialCommandGroup {

    protected String position;

    public Auton(String positionIn) {
        position = positionIn;
    }

    public Auton() {
       
    }
}
