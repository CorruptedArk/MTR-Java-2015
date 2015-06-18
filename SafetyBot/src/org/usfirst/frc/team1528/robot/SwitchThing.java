package org.usfirst.frc.team1528.robot;
import edu.wpi.first.wpilibj.*;
public class SwitchThing implements Runnable{
	Joystick stick;
	int button;
	boolean running, isForward, defaultState;
	
	public SwitchThing(Joystick stick, int button, boolean defaultState){
		this.stick = stick;
		this.button = button;
		isForward = defaultState;
		this.defaultState = defaultState;
	}
	
	@Override
	public void run() {
		running = true;
		isForward = defaultState;
		while(running){
			if(stick.getRawButton(button)){
				isForward = !isForward;
				while(stick.getRawButton(button)){
					
				}	
			}
			Timer.delay(0.005);
		}
		
	}
	
	public void stop(){
		running = false;
	}
	
	public boolean isForward(){
		return isForward;
	}

}
