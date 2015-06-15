package org.usfirst.frc.team1528.robot;
import edu.wpi.first.wpilibj.*;
public class SwitchThing implements Runnable{
	Joystick stick;
	int axis;
	boolean running;
	
	public SwitchThing(Joystick stick, int axis ){
		
	}
	
	@Override
	public void run() {
		running = true;
		
		while(running){
			
		}
		
	}
	
	public void stop(){
		running = false;
	}

}
