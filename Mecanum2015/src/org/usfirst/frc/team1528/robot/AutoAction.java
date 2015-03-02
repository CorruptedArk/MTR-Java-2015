package org.usfirst.frc.team1528.robot;


/**
 * A class that holds the desired states of the robot for a step during 
 * advanced autonomous and the duration the robot will wait to execute the next step.
 * @author Noah
 *
 */
public class AutoAction{
	double xMovement, yMovement, twist, time, motorSpeed;
	boolean openLift;
	
	/**
	 * Constructor.
	 * @param xMovement The left/right movement of the robot.
	 * @param yMovement The forward/backward movement of the robot. 
	 * @param twist The rotational movement of the robot.
	 * @param time The duration that robot waits to call the next action.
	 * @param motorSpeed The up/down motion of the lift.
	 * @param openLift Will the grabber be open?
	 */
	public AutoAction(double xMovement, double yMovement, double twist, double time, double motorSpeed, boolean openLift){
		this.xMovement = xMovement;
		this.yMovement = yMovement;
		this.twist = twist;
		this.time = time;
		this.motorSpeed = motorSpeed;
		this.openLift = openLift;
	}
	
	/**
	 * Checks if an action has equivalent values.
	 * @param action The AutoAction being compared.
	 * @return True if the values are equal.
	 */
	public boolean equals(AutoAction action){
		
		return action.xMovement == xMovement 
				&& action.yMovement == yMovement
				&& action.twist == twist
				&& action.motorSpeed == motorSpeed
				&& action.openLift == openLift;
	}
	
}
