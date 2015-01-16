package org.usfirst.frc.team1528.robot;

import edu.wpi.first.wpilibj.*;

public class LiftControl implements Runnable {

	private boolean running;
	private final Joystick driver;
	private final int upID, downID;
	private final boolean upAxisPositive, downAxisPositive;
	private final SpeedController leftMotor, rightMotor;
	private final String inputType;
	private final double defaultSpeed;
	private final ExecutiveOrder order;
	
	/**
	 * Constructor. Uses buttons and a single operator.
	 * @param driver The operator's joystick.
	 * @param upButtonID The ID of the up button.
	 * @param downButtonID The ID of the down button.
	 * @param defaultSpeed The speed of movement from 0 to 1.
	 * @param leftMotor The left speed controller object.
	 * @param rightMotor The right speed controller object.
	 */
	public LiftControl(Joystick driver, int upButtonID, int downButtonID, double defaultSpeed, SpeedController leftMotor, SpeedController rightMotor){
		this.driver = driver;
		this.order = null;
		this.upID = upButtonID;
		this.downID = downButtonID;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.inputType = "button";
		if(defaultSpeed < -1){
			defaultSpeed = -1;
		}else if(defaultSpeed > 1){
			defaultSpeed = 1;
		}
		this.defaultSpeed = Math.abs(defaultSpeed);
		this.upAxisPositive = false;
		this.downAxisPositive = false;
	}
	
	/**
	 * Constructor. Uses buttons and a single operator.
	 * @param driver The operator's joystick.
	 * @param upAxisID The ID of the up axis.
	 * @param downAxisID The ID of the down axis.
	 * @param defaultSpeed The speed of movement from 0 to 1.
	 * @param leftMotor The left speed controller object.
	 * @param rightMotor The right speed controller object.
	 */
	public LiftControl(Joystick driver, int upAxisID, int downAxisID, boolean upAxisPositive, boolean downAxisPositive, SpeedController leftMotor, SpeedController rightMotor){
		this.driver = driver;
		this.order = null;
		this.upID = upAxisID;
		this.downID = downAxisID;
		this.upAxisPositive = upAxisPositive;
		this.downAxisPositive = downAxisPositive;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.inputType = "axis";
		this.defaultSpeed = 0.0;
	}
	
	public LiftControl(ExecutiveOrder order, int upButtonID, int downButtonID, double defaultSpeed, SpeedController leftMotor, SpeedController rightMotor){
		this.driver = null;
		this.order = order;
		this.upID = upButtonID;
		this.downID = downButtonID;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.inputType = "button";
		if(defaultSpeed < -1){
			defaultSpeed = -1;
		}else if(defaultSpeed > 1){
			defaultSpeed = 1;
		}
		this.defaultSpeed = Math.abs(defaultSpeed);
		this.upAxisPositive = false;
		this.downAxisPositive = false;
		
	}
	
	public LiftControl(ExecutiveOrder order, int upAxisID, int downAxisID, boolean upAxisPositive, boolean downAxisPositive, SpeedController leftMotor, SpeedController rightMotor){
		this.driver = null;
		this.order = order;
		this.upID = upAxisID;
		this.downID = downAxisID;
		this.upAxisPositive = upAxisPositive;
		this.downAxisPositive = downAxisPositive;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.inputType = "axis";
		this.defaultSpeed = 0.0;
	}
	
	@Override
	public void run() {
		running = true;
		if(order != null && inputType.equalsIgnoreCase("button")){
			executiveButtonControl();
		}else if(order != null && inputType.equalsIgnoreCase("axis")){
			executiveAxisControl();
		}else if(driver != null && inputType.equalsIgnoreCase("button")){
			buttonControl();
		}else if(driver != null && inputType.equalsIgnoreCase("axis")){
			axisControl();
		}else{
			throw new IllegalArgumentException("Something was invalid.");
		}
		
	}
	
	private void executiveButtonControl(){
		while(running){
			if(getExecutiveButtonPressed(upID) && !getExecutiveButtonPressed(downID)){
				leftMotor.set(defaultSpeed);
				rightMotor.set(-defaultSpeed);
			}else if(!getExecutiveButtonPressed(upID) && getExecutiveButtonPressed(downID)){
				leftMotor.set(-defaultSpeed);
				rightMotor.set(defaultSpeed);
			}else{
				leftMotor.set(0.0);
				rightMotor.set(0.0);
			}
			
			
			Timer.delay(0.005);
		}
	}
	
	private void buttonControl(){
		while(running){
			if(driver.getRawButton(upID) && !driver.getRawButton(downID)){
				leftMotor.set(defaultSpeed);
				rightMotor.set(-defaultSpeed);
			}else if(!driver.getRawButton(upID) && driver.getRawButton(downID)){
				leftMotor.set(-defaultSpeed);
				rightMotor.set(defaultSpeed);
			}else{
				leftMotor.set(0.0);
				rightMotor.set(0.0);
			}
			
			
			Timer.delay(0.005);
		}
	}
	
	private void axisControl(){
		while(running){
			if(getAxisPressed(upID,driver,upAxisPositive) && !getAxisPressed(downID,driver,downAxisPositive)){
				leftMotor.set(buffer(upID,driver,true,0.18,-0.18));
				rightMotor.set(buffer(upID,driver,false,0.18,-0.18));
			}else if(!getAxisPressed(upID,driver,upAxisPositive) && getAxisPressed(downID,driver,downAxisPositive)){
				leftMotor.set(buffer(downID,driver,true,0.18,-0.18));
				rightMotor.set(buffer(downID,driver,false,0.18,-0.18));
			}else{
				leftMotor.set(0.0);
				rightMotor.set(0.0);
			}
			Timer.delay(0.005);
		}
	}
	
	
	private void executiveAxisControl(){
		
		while(running){
			Joystick currentDriver;
			
			if(order.getReleaseState()){
				currentDriver = order.congress;
			}else{
				currentDriver = order.president;
			}
			
			if(getAxisPressed(upID,currentDriver,upAxisPositive) && !getAxisPressed(downID,currentDriver,downAxisPositive)){
				leftMotor.set(buffer(upID,currentDriver,true,0.18,-0.18));
				rightMotor.set(buffer(upID,currentDriver,false,0.18,-0.18));
			}else if(!getAxisPressed(upID,currentDriver,upAxisPositive) && getAxisPressed(downID,currentDriver,downAxisPositive)){
				leftMotor.set(buffer(downID,currentDriver,true,0.18,-0.18));
				rightMotor.set(buffer(downID,currentDriver,false,0.18,-0.18));
			}else{
				leftMotor.set(0.0);
				rightMotor.set(0.0);
			}
			Timer.delay(0.005);
		}
	}
	public void stop(){
		running = false;
	}
	
	 /**
     * Uses an ExecutiveOrder object to check if the button is pressed.
     * @return pressed 
     */
    private boolean getExecutiveButtonPressed(int toggler) {
        boolean pressed = false;
        
        if(order.president.getRawButton(toggler)){
            order.trap();
            pressed = true;
        }
        else if(order.congress.getRawButton(toggler) && order.getReleaseState()){
            pressed = true;
        }
        
        
        return pressed;
    }
    
    private boolean getAxisPressed(int ID, Joystick currentDriver, boolean axisPositive){
    	boolean pressed;
    	if(buffer(ID,driver,true,0.18,-0.18) > 0.0 == axisPositive){
    		pressed = true;
    	}else if(buffer(ID,driver,true,0.18,-0.18) < 0.0 == !axisPositive){
    		pressed = true;
    	}else{
    		pressed = false;
    	}
    	
    	return pressed;
    }
	
	/**
     * This function buffers Joystick.getRawAxis() input.
     * @param axisNum The ID for the axis of a Joystick.
     * @param joystickName The Joystick that input is coming from. 
     * @param inverted Is it flipped?
     * @param highMargin The high margin of the buffer.
     * @param lowMargin The low margin of the buffer.
     * @return moveOut - The buffered axis data from joystickName.getRawAxis().
     **/
    private double buffer(int axisNum, Joystick joystickName, boolean inverted, double highMargin, double lowMargin) {
        double moveIn = joystickName.getRawAxis(axisNum);
        double moveOut;
        moveOut = 0.0;
        
        if(moveIn >= lowMargin && moveIn <= highMargin ) {
            moveOut = 0.0;
        }
        else{
            if(inverted){
                moveOut = -moveIn;
            }
            else if(!inverted){ 
                moveOut = moveIn;
            }    
        }
	
	return moveOut;
   }
   
    
    /**
     * This function buffers Joystick.getRawAxis() input.
     * @param axisNum The ID for the axis of a Joystick.
     * @param joystickName The Joystick that input is coming from. 
     * @param inverted Is it flipped?
     * @param highMargin The high margin of the buffer.
     * @param lowMargin The low margin of the buffer.
     * @param scale The amount you want to divide the output by.
     * @return moveOut - The buffered axis data from joystickName.getRawAxis().
     **/
    private double buffer(int axisNum, Joystick joystickName, boolean inverted, double highMargin, double lowMargin, double scale) {
        double moveIn = joystickName.getRawAxis(axisNum);
        double moveOut;
        moveOut = 0.0;
        
        if(moveIn >= lowMargin && moveIn <= highMargin ) {
            moveOut = 0.0;
        }
        else{
            if(inverted){
                moveOut = -moveIn;
            }
            else if(!inverted){ 
                moveOut = moveIn;
            }    
        }
	
        if(scale <= 1){
            scale = 1;
        }
        
        moveOut = moveOut/scale;
        
	return moveOut;
   }

}
