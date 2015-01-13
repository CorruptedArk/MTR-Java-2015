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
		TrackingState trackingState = hmd.getSensorState(ovrLib.ovr_GetTimeInSeconds());
		pose = trackingState.HeadPose.Pose;
		return (pose.Orientation.x/Math.PI)+1;
	}
	
	public double getYServoAngularPosition(){
		TrackingState trackingState = hmd.getSensorState(ovrLib.ovr_GetTimeInSeconds());
		pose = trackingState.HeadPose.Pose;
		return (pose.Orientation.y/Math.PI)+1;
	}
	
	public double getZServoAngularPosition(){
		TrackingState trackingState = hmd.getSensorState(ovrLib.ovr_GetTimeInSeconds());
		pose = trackingState.HeadPose.Pose;
		return (pose.Orientation.z/Math.PI);
	}
	
	public double getXDegreeAngularPosition(){
		TrackingState trackingState = hmd.getSensorState(ovrLib.ovr_GetTimeInSeconds());
		pose = trackingState.HeadPose.Pose;
		return (pose.Orientation.x*(180/Math.PI))+180;
	}
	
	public double getYDegreeAngularPosition(){
		TrackingState trackingState = hmd.getSensorState(ovrLib.ovr_GetTimeInSeconds());
		pose = trackingState.HeadPose.Pose;
		return (pose.Orientation.y*(180/Math.PI))+180;
	}
	
	public double getZDegreeAngularPosition(){
		TrackingState trackingState = hmd.getSensorState(ovrLib.ovr_GetTimeInSeconds());
		pose = trackingState.HeadPose.Pose;
		return (pose.Orientation.z*(180/Math.PI))+180;
	}
	
	
	
	public void destroyResources(){
		hmd.destroy();
		ovrLib.ovr_Shutdown();
	}
	
}
