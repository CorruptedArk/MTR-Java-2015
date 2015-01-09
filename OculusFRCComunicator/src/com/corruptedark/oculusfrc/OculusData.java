package com.corruptedark.oculusfrc;
import com.oculusvr.capi.*;

public class OculusData {

	OvrLibrary ovrLib; 
	Hmd hmd;
	Posef pose;
	
	public OculusData(){
		init();
	}
	
	public void init(){
		ovrLib = OvrLibrary.INSTANCE;
		ovrLib.ovr_Initialize();
		hmd = Hmd.create(0);
		
	}
	
	public void startTracking(){
		if(hmd != null){	
			hmd.configureTracking(OvrLibrary.ovrTrackingCaps.ovrTrackingCap_Idle|
									OvrLibrary.ovrTrackingCaps.ovrTrackingCap_MagYawCorrection|
									OvrLibrary.ovrTrackingCaps.ovrTrackingCap_Position, 0);
			TrackingState trackingState = hmd.getSensorState(ovrLib.ovr_GetTimeInSeconds());
		
			if((trackingState.StatusFlags & (OvrLibrary.ovrStatusBits.ovrStatus_OrientationTracked | OvrLibrary.ovrStatusBits.ovrStatus_PositionTracked)) != 0){
				pose = trackingState.HeadPose.Pose;
			}
		}
	}
	
	public double getXServoAngularPosition(){
		return pose.Orientation.x/Math.PI;
	}
	
	public double getYServoAngularPosition(){
		return pose.Orientation.y/Math.PI;
	}
	
	public double getZServoAngularPosition(){
		return pose.Orientation.z/Math.PI;
	}
	
	public double getXDegreeAngularPosition(){
		return pose.Orientation.x*(180/Math.PI);
	}
	
	public double getYDegreeAngularPosition(){
		return pose.Orientation.y*(180/Math.PI);
	}
	
	public double getZDegreeAngularPosition(){
		return pose.Orientation.z*(180/Math.PI);
	}
	
	
	
	public void destroyResources(){
		hmd.destroy();
		ovrLib.ovr_Shutdown();
	}
	
}
