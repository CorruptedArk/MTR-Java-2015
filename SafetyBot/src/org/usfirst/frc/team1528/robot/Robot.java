
package org.usfirst.frc.team1528.robot;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Relay;
/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive safteyDrive;
    Joystick safteyController;
    Talon safteyMotor, safteyMotor2;
    DigitalInput safteySwitch;
    DoubleSolenoid safetyPiston,piston;
    Relay safetyRelay;
  //Constants for Buttons
    static final int A_BUTTON = 1;
    static final int B_BUTTON = 2;
    static final int X_BUTTON = 3;
    static final int Y_BUTTON = 4;
    static final int LEFT_BUMPER = 5;
    static final int RIGHT_BUMPER = 6;
    static final int BACK_BUTTON = 7;
    static final int START_BUTTON = 8;
    static final int LEFT_JOYSTICK_CLICK = 9;
    static final int RIGHT_JOYSTICK_CLICK = 10;
    
    //Constants for Axes
    static final int LEFT_X_AXIS = 0;
    static final int LEFT_Y_AXIS = 1;
    static final int LEFT_TRIGGER_AXIS = 2;
    static final int RIGHT_TRIGGER_AXIS = 3;
    static final int RIGHT_X_AXIS = 4;
    static final int RIGHT_Y_AXIS = 5;
    static final int D_PAD = 6; 
    
    
    public Robot() {
        safteyDrive = new RobotDrive(0,1,2,3);
        safteyController = new Joystick(0);
        safteySwitch = new DigitalInput(0);
        safetyPiston = new DoubleSolenoid(0,1);
        safetyRelay = new Relay(0);
        safteyMotor2 = new Talon(5);
        safteyMotor = new Talon(4);
    } 

    
    public void autonomous() {
    	/*
    	safteyDrive.mecanumDrive_Cartesian(1 , 0, 1, 00);
        Timer.delay(666);  */
        
    }

    
    public void operatorControl() {
    	while(isOperatorControl() && isEnabled()){
    	
    		safteyMotor.setSafetyEnabled(true);
    		safteyMotor2.setSafetyEnabled(true);
    		
    		
    		
    		
    		safteyDrive.setSafetyEnabled(true);
    		double x = buffer(LEFT_X_AXIS,safteyController,true,0.18,-0.18);
    		double y = buffer(LEFT_Y_AXIS, safteyController,true,0.18,-0.18);
    		double r = buffer(RIGHT_X_AXIS, safteyController,true,0.18, -0.18);
    		safteyDrive.mecanumDrive_Cartesian(x, y, r, 0.0);
    		
    		safteyMotor.set(buffer(LEFT_TRIGGER_AXIS,safteyController,true,0.18,-0.18));
    		safteyMotor2.set(buffer(RIGHT_TRIGGER_AXIS,safteyController,true,0.18,-0.18));
    		
    		/*if(safteyController.getRawButton(B_BUTTON)){
    			safteyDrive.mecanumDrive_Cartesian(0, 0, 1.0, 0);
    			Timer.delay(5.0);
    			safteyDrive.mecanumDrive_Cartesian(0, 0, 0, 0);	
    		}
    			
    		if(!safteySwitch.get()){
    			safteyMotor.set(buffer(RIGHT_Y_AXIS, safteyController,true,0.18, -0.18)*.4000000000);
    		}else{
    			safteyMotor.set(buffer(RIGHT_X_AXIS, safteyController,true,0.18, -0.18)*1.00);
    		}
    		
    		if(safteyController.getRawButton(A_BUTTON)){
    			safetyRelay.set(Relay.Value.kForward);
    		}else{
    			safetyRelay.set(Relay.Value.kOff);
    		}*/
    		
    			
    			
    		Timer.delay(0.01);
    	}
    		
    		
    }
    	
    

    /**
     * Runs during test mode
     */
    public void test() {
    	

    	
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
    public double buffer(int axisNum, Joystick joystickName, boolean inverted, double highMargin, double lowMargin) {
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
     * @param scale The amount you want to multiply the output by.
     * @return moveOut - The buffered axis data from joystickName.getRawAxis().
     **/
    public double buffer(int axisNum, Joystick joystickName, boolean inverted, double highMargin, double lowMargin, double scale) {
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
        
        scale = Math.abs(scale);
        
        if(scale >= 1){
            scale = 1;
        }
        
        moveOut = moveOut*scale;
        
        return moveOut;
    }
   public double buffer1(int axis, Joystick one, boolean backwards, double high, double low, double scale){
	   double inputMove = one.getRawAxis(axis);
	   double outputMove;
	   outputMove = 0.0;
	   
	   if(inputMove >= low && inputMove <= high){
		   outputMove = 0.0;
	   } else if(backwards){
		   outputMove = -inputMove;
	   }else{
		   outputMove = inputMove;
	   }
	   scale = Math.abs(scale);
	   if(scale >= 1){
		   scale = 1;
	   }
	   outputMove = outputMove*scale;
	   	
	   return outputMove;
	   	
   }
	   
    
    

}