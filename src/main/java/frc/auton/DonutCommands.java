package frc.auton;

import frc.auton.commands.TurnCommand;

public class DonutCommands extends Auton {
       public static int s = 360;
       public static double END_TIME; 
       
        
        public DonutCommands(double endTime) {
            super();
            new TurnCommand(s);
            
        }
        @Override
        public void execute() {
          if(Timer.getMatchTime() <= 14) {
            storage.periodic();
            acquisition.runBeaterBarFwd();
          }
        }
        
        @Override
  public boolean isFinished() {
    return Timer.getMatchTime() <= END_TIME;
  }
    }