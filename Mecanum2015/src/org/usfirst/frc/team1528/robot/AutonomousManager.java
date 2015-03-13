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
	Solenoid brakeOut, brakeIn;
	
	/**
	 * Constructor. 
	 * @param drive The RobotDrive controlling the robot movement.
	 * @param liftMotor The motor controlling the lift of the robot.
	 * @param brakeOut The solenoid the extends the brake.
	 * @param brakeIn The solenoid the retracts the brake.
	 */
	public AutonomousManager(RobotDrive drive, SpeedController liftMotor, Solenoid brakeOut, Solenoid brakeIn){
		this.drive = drive;
		this.liftMotor = liftMotor;
		this.brakeOut = brakeOut;
		this.brakeIn = brakeIn;
	}
	
	/**
	 * Constructor. No brake.
	 * @param drive
	 * @param liftMotor
	 */
	public AutonomousManager(RobotDrive drive, SpeedController liftMotor){
		this.drive = drive;
		this.liftMotor = liftMotor;
		this.brakeIn = null;
		this.brakeOut = null;
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
		if(brakeIn == null && brakeOut == null){
			drive.setSafetyEnabled(false);
			for(int i = 0; i < actionList.size(); i++){
				AutoAction action = actionList.get(i);
				drive.mecanumDrive_Cartesian(action.xMovement, action.yMovement, action.twist, 0.0);
				liftMotor.set(action.motorSpeed);
				Timer.delay(action.time);
			}
		}else{
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
				Timer.delay(action.time);
			}
		}
	}
	
}
