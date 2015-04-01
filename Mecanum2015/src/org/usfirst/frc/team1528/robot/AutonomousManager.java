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
	
	/**
	 * Constructor. 
	 * @param drive The RobotDrive controlling the robot movement.
	 * @param liftMotor The motor controlling the lift of the robot.
	 */
	public AutonomousManager(RobotDrive drive, SpeedController liftMotor){
		this.drive = drive;
		this.liftMotor = liftMotor;
	}
	
	
	
	/**
	 * Adds an AutoAction to the action list. Uses the raw parameters.
	 * @param xMovement The left/right movement of the robot.
	 * @param yMovement The forward/backward movement of the robot. 
	 * @param twist The rotational movement of the robot.
	 * @param time The duration that robot waits to call the next action.
	 * @param motorSpeed The up/down motion of the lift.
	 */
	public void addAutoAction(double xMovement, double yMovement, double twist, double time, double motorSpeed){
		actionList.add(new AutoAction(xMovement, yMovement, twist, time, motorSpeed));
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
			liftMotor.set(action.motorSpeed);
			Timer.delay(action.time);
		}
	}
	
}
