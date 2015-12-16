package org.usfirst.frc.team1528.robot;

import edu.wpi.first.wpilibj.*;

public class ScaleChanger implements Runnable {

	private final Joystick controller;
	private final ExecutiveOrder order;
	private final int downID, upID;
	private volatile double scale;
	private final double increment, defaultScale;
	private volatile boolean running;
	
	/**
	 * Uses a single controller to change a value scale.
	 * @param controller The controller in use.
	 * @param downID The ID number of the button to decrease scale.
	 * @param upID The ID number of the button to increase scale.
	 * @param scale The scale that limits the output of a speed controller.
	 * @param increment The amount the scale will change per press.
	 */
	ScaleChanger(Joystick controller,int downID, int upID, double scale, double increment){
		this.controller = controller;
		this.order = null;
		this.downID = downID;
		this.upID = upID;
		this.scale = scale;
		this.defaultScale = scale;
		this.increment = increment;
	}
	
	/**
	 * Uses two controllers to change a value scale.
	 * @param order The executive order for two controller input.
	 * @param downID The ID number of the button to decrease scale.
	 * @param upID The ID number of the button to increase scale.
	 * @param scale The scale that limits the output of a speed controller.
	 * @param increment The amount the scale will change per press.
	 */
	ScaleChanger(ExecutiveOrder order,int downID, int upID, double scale, double increment){
		this.controller = null;
		this.order = order;
		this.downID = downID;
		this.upID = upID;
		this.scale = scale;
		this.defaultScale = scale;
		this.increment = increment;
	}
	
	/**
	 * Starts the process loop.
	 */
	@Override
	public void run() {
		running = true;

		scale = defaultScale;
		
		if(controller != null && order == null){
			scale();
		}else if(controller == null && order != null){
			executiveScale();
		}
	}
	
	/**
	 * The process loop for a single controller.
	 */
	private void scale(){
		while(running){
			
			if(pressed()){
				if(controller.getRawButton(upID)){
					if(scale + increment <= 1.0){
						scale += increment;
					}
				}else if(controller.getRawButton(downID)){
					if(scale - increment > 0.0){
						scale -= increment;
					}
				}
				
				while(pressed()){
					
				}
			}
		}
	}
	
	/**
	 * Checks if one of the defined buttons are pressed.
	 * @return True if only one of the defined buttons are pressed.
	 */
	private boolean pressed(){
		return controller.getRawButton(downID) ^ controller.getRawButton(upID);
	}
	
	/**
	 * The process loop for two controllers.
	 */
	private void executiveScale(){
		while(running){
			
			if(executivePressed()){
				Joystick controller;
				if(order.getReleaseState()){
					controller = order.congress;
				}else{
					controller = order.president;
				}
				
				if(controller.getRawButton(upID)){
					if(scale + increment <= 1.0){
						scale += increment;
					}
				}else if(controller.getRawButton(downID)){
					if(scale - increment > 0.0){
						scale -= increment;
					}
				}
				
				while(executivePressed()){
					
				}
			}
		}
	}
	
	/**
	 * Checks if one of the defined buttons on the governing controller is pressed.
	 * @return True if only one of the defined buttons on the governing controller is pressed.
	 */
	private boolean executivePressed(){
		Joystick controller;
		if(order.getReleaseState()){
			controller = order.congress;
		}else{
			controller = order.president;
		}
		
		return controller.getRawButton(downID) ^ controller.getRawButton(upID);
	}
	
	/**
	 * Gets the current scale.
	 * @return The current scale. 
	 */
	public double getScale(){
		return scale;
	}
	
	/**
	 * Stops the process loop.
	 */
	public void stop(){
		running = false;
	}
}
