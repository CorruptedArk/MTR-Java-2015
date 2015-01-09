package com.corruptedark.oculusfrc;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OculusTables {
	private static NetworkTable table;

	static OculusData oculus; 
	
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            oculus.destroyResources();
	        }
	    }, "Shutdown-thread"));
		new OculusTables().run();
		
	}
	
	public void run(){
		oculus = new OculusData();
		try{
			oculus.startTracking();
		}catch(Exception e){
			
		}
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.15.28.2");  
		table = NetworkTable.getTable("oculus");
		
		while(true){
			try{
				Thread.sleep(100);
			}catch(InterruptedException ex){
				Logger.getLogger(OculusTables.class.getName()).log(Level.SEVERE,null,ex);
			}
			
			try{
				table.putNumber("xDegree",oculus.getXDegreeAngularPosition());
				table.putNumber("xServo",oculus.getXServoAngularPosition());
				table.putNumber("yDegree",oculus.getYDegreeAngularPosition());
				table.putNumber("yServo",oculus.getYServoAngularPosition());
				table.putNumber("zDegree",oculus.getZDegreeAngularPosition());
				table.putNumber("zServo",oculus.getZServoAngularPosition());
				
				System.out.println("xDegree: "+oculus.getXDegreeAngularPosition());
				System.out.println("xServo: "+oculus.getXServoAngularPosition());
				System.out.println("yDegree: "+oculus.getYDegreeAngularPosition());
				System.out.println("yServo: "+oculus.getYServoAngularPosition());
				System.out.println("zDegree: "+oculus.getZDegreeAngularPosition());
				System.out.println("zServo: "+oculus.getZServoAngularPosition());
			}catch(Exception e){
				
			}
			
		}
	}
	

}
