package org.usfirst.frc.team1528.robot;

import edu.wpi.first.wpilibj.*;
import java.util.ArrayList;

/**
 * A class to manage actions for my advanced autonomous mode
 * @author Noah
 *
 */
public class AutonomousManager {

	ArrayList<AutoAction> actionList = new ArrayList<AutoAction>();
	RobotDrive drive;
	SpeedController liftMotor;
	Solenoid grabberOut, grabberIn, brakeOut, brakeIn;
	
	/**
	 * Constructor. 
	 * @param drive The RobotDrive controlling the robot movement.
	 * @param liftMotor The motor controlling the lift of the robot.
	 * @param grabberOut The solenoid that pushes the grabber out.
	 * @param grabberIn The sloenoid that pushes the grabber in.
	 * @param brakeOut The solenoid the extends the brake.
	 * @param brakeIn The solenoid the retracts the brake.
	 */
	public AutonomousManager(RobotDrive drive, SpeedController liftMotor, Solenoid grabberOut, Solenoid grabberIn, Solenoid brakeOut, Solenoid brakeIn){
		this.drive = drive;
		this.liftMotor = liftMotor;
		this.grabberOut = grabberOut;
		this.grabberIn = grabberIn;
		this.brakeOut = brakeOut;
		this.brakeIn = brakeIn;
	}
	
	/**
	 * Adds an AutoAction to the action list. Uses the raw parameters.
	 * @param xMovement The left/right movement of the robot.
	 * @param yMovement The forward/backward movement of the robot. 
	 * @param twist The rotational movement of the robot.
	 * @param time The duration that robot waits to call the next action.
	 * @param motorSpeed The up/down motion of the lift.
	 * @param openLift Will the grabber be open?
	 */
	public void addAutoAction(double xMovement, double yMovement, double twist, double time, double motorSpeed, boolean openLift){
		actionList.add(new AutoAction(xMovement, yMovement, twist, time, motorSpeed, openLift));
	}
	
	/**
	 * Adds an AutoAction to the action list. Uses an existing action.
	 * @param action The Action.
	 */
    public void addAutoAction(AutoAction action){
    	actionList.add(action);
    }
	
    /**
     * Makes the robot perform all actions consecutively.
     */
	public void performAllActions(){
		drive.setSafetyEnabled(false);
		for(int i = 0; i < actionList.size(); i++){
			AutoAction action = actionList.get(i);
			drive.mecanumDrive_Cartesian(action.xMovement, action.yMovement, action.twist, 0.0);
			if(action.motorSpeed == 0.0 ){
				brakeOut.set(true);
				brakeIn.set(false);
			}else{
				brakeOut.set(false);
				brakeIn.set(true);
			}
			liftMotor.set(action.motorSpeed);
			grabberOut.set(action.openLift);
			grabberIn.set(!action.openLift);
			Timer.delay(action.time);
		}
	}
	
}
