package com.corruptedark.oculusfrc;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OculusTables {
	private static NetworkTable table;

	static OculusData oculus= new OculusData(); 
	
	public static void main(String[] args) {
		
		new OculusTables().run();
		
	}
	
	public void run(){
		
		
		oculus.startTracking();
		
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roboRio-1528.local");  
		table = NetworkTable.getTable("oculus");
		
		
		while(true){
			try{
				Thread.sleep(1);
			}catch(InterruptedException ex){
				Logger.getLogger(OculusTables.class.getName()).log(Level.SEVERE,null,ex);
			}
			
			
				table.putNumber("xDegree",oculus.getXDegreeAngularPosition());
				table.putNumber("xServo",oculus.getXServoAngularPosition());
				table.putNumber("yDegree",oculus.getYDegreeAngularPosition());
				table.putNumber("yServo",oculus.getYServoAngularPosition());
				table.putNumber("zDegree",oculus.getZDegreeAngularPosition());
				table.putNumber("zServo",oculus.getZServoAngularPosition());
				
				System.out.println(table.getNumber("xServo"));
				
				System.out.println("xDegree: "+oculus.getXDegreeAngularPosition());
				System.out.println("xServo: "+oculus.getXServoAngularPosition());
				System.out.println("yDegree: "+oculus.getYDegreeAngularPosition());
				System.out.println("yServo: "+oculus.getYServoAngularPosition());
				System.out.println("zDegree: "+oculus.getZDegreeAngularPosition());
				System.out.println("zServo: "+oculus.getZServoAngularPosition());
			
			
		}
	}
	

}
