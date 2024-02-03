/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ProximitySensor.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;

import vrml.*;
import vrml.field.*;
import vrml.util.Debug;

public class ProximitySensorNode extends Node {
	
	private String	enabledFieldName		= "enabled";
	private String	centerFieldName		= "center";
	private String	sizeFieldName			= "size";

	private String	positionEventOutName	= "position";
	private String	orientationEventOutName	= "orientation";
	private String	enterTimeEventOutName	= "enterTime";
	private String	exitTimeEventOutName	= "exitTime";

	public ProximitySensorNode() {
		setHeaderFlag(false);
		setType(proximitySensorTypeName);
		setRunnable(true);

		// enabled exposed field
		SFBool enabled = new SFBool(true);
		addExposedField(enabledFieldName, enabled);

		// center exposed field
		SFVec3f center = new SFVec3f(0.0f, 0.0f, 0.0f);
		addExposedField(centerFieldName, center);

		// size exposed field
		SFVec3f size = new SFVec3f(0.0f, 0.0f, 0.0f);
		addExposedField(sizeFieldName, size);

		
		// isActive eventOut field
		ConstSFBool isActive = new ConstSFBool(false);
		addEventOut(isActiveFieldName, isActive);

		// position eventOut field
		ConstSFVec3f position = new ConstSFVec3f(0.0f, 0.0f, 0.0f);
		addEventOut(positionEventOutName, position);

		// orientation eventOut field
		ConstSFRotation orientation = new ConstSFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addEventOut(orientationEventOutName, orientation);

		// enterTime eventOut field
		ConstSFTime enterTime = new ConstSFTime(0.0f);
		addEventOut(enterTimeEventOutName, enterTime);

		// exitTime eventOut field
		ConstSFTime exitTime = new ConstSFTime(0.0f);
		addEventOut(exitTimeEventOutName, exitTime);
	}

	////////////////////////////////////////////////
	//	Enabled
	////////////////////////////////////////////////
	
	public void setEnabled(boolean value) {
		SFBool bEnabled = (SFBool)getExposedField(enabledFieldName);
		bEnabled.setValue(value);
	}
	public boolean getEnabled() {
		SFBool bEnabled = (SFBool)getExposedField(enabledFieldName);
		return bEnabled.getValue();
	}
	public boolean isEnabled() {
		return getEnabled();
	}

	////////////////////////////////////////////////
	//	Center
	////////////////////////////////////////////////
	
	public void setCenter(float value[]) {
		SFVec3f sfvec3f = (SFVec3f)getExposedField(centerFieldName);
		sfvec3f.setValue(value);
	}
	public void setCenter(float x, float y, float z) {
		SFVec3f sfvec3f = (SFVec3f)getExposedField(centerFieldName);
		sfvec3f.setValue(x, y, z);
	}
	public void getCenter(float value[]) {
		SFVec3f sfvec3f = (SFVec3f)getExposedField(centerFieldName);
		sfvec3f.getValue();
	}

	////////////////////////////////////////////////
	//	Size
	////////////////////////////////////////////////
	
	public void setSize(float value[]) {
		SFVec3f sfvec3f = (SFVec3f)getExposedField(sizeFieldName);
		sfvec3f.setValue(value);
	}
	public void setSize(float x, float y, float z) {
		SFVec3f sfvec3f = (SFVec3f)getExposedField(sizeFieldName);
		sfvec3f.setValue(x, y, z);
	}
	public void getSize(float value[]) {
		SFVec3f sfvec3f = (SFVec3f)getExposedField(sizeFieldName);
		sfvec3f.getValue();
	}

	////////////////////////////////////////////////
	//	isActive
	////////////////////////////////////////////////
	
	public void setIsActive(boolean value) {
		ConstSFBool sfbool = (ConstSFBool)getEventOut(isActiveFieldName);
		sfbool.setValue(value);
	}
	public boolean getIsActive() {
		ConstSFBool sfbool = (ConstSFBool)getEventOut(isActiveFieldName);
		return sfbool.getValue();
	}
	public boolean isActive() {
		return getIsActive();
	}

	////////////////////////////////////////////////
	//	Position
	////////////////////////////////////////////////
	
	public void setPositionChanged(float value[]) {
		ConstSFVec3f position = (ConstSFVec3f)getEventOut(positionEventOutName);
		position.setValue(value);
	}
	public void setPositionChanged(float x, float y, float z) {
		ConstSFVec3f position = (ConstSFVec3f)getEventOut(positionEventOutName);
		position.setValue(x, y, z);
	}
	public void getPositionChanged(float value[]) {
		ConstSFVec3f position = (ConstSFVec3f)getEventOut(positionEventOutName);
		position.getValue(value);
	}

	////////////////////////////////////////////////
	//	Orientation
	////////////////////////////////////////////////
	
	public void setOrientationChanged(float value[]) {
		ConstSFRotation orientation = (ConstSFRotation)getEventOut(orientationEventOutName);
		orientation.setValue(value);
	}
	public void setOrientationChanged(float x, float y, float z, float rot) {
		ConstSFRotation orientation = (ConstSFRotation)getEventOut(orientationEventOutName);
		orientation.setValue(x, y, z, rot);
	}
	public void getOrientationChanged(float value[]) {
		ConstSFRotation orientation = (ConstSFRotation)getEventOut(orientationEventOutName);
		orientation.getValue(value);
	}

	////////////////////////////////////////////////
	//	EnterTime
	////////////////////////////////////////////////
	
	public void setEnterTime(double value) {
		ConstSFTime time = (ConstSFTime)getEventOut(enterTimeEventOutName);
		time.setValue(value);
	}
	public double getEnterTime() {
		ConstSFTime time = (ConstSFTime)getEventOut(enterTimeEventOutName);
		return time.getValue();
	}

	////////////////////////////////////////////////
	//	ExitTime
	////////////////////////////////////////////////
	
	public void setExitTime(double value) {
		ConstSFTime time = (ConstSFTime)getEventOut(exitTimeEventOutName);
		time.setValue(value);
	}
	public double getExitTime() {
		ConstSFTime time = (ConstSFTime)getEventOut(exitTimeEventOutName);
		return time.getValue();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public ProximitySensor next() {
		return (ProximitySensor)next(getType());
	}

	public ProximitySensor nextTraversal() {
		return (ProximitySensor)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	 inRegion
	////////////////////////////////////////////////
	
	private boolean mInRegion = false;
	
	public void setInRegion(boolean value) {
		mInRegion = value;
	}

	public boolean inRegion() {
		return mInRegion;
	} 

	public boolean isRegion(float vpos[], float center[], float size[]) {
		for (int n=0; n<3; n++) {
			if (vpos[n] < center[n] - size[n]/2.0f)
				return false;
			if (center[n] + size[n]/2.0f < vpos[n])
				return false;
		}
		return true;
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
		setInRegion(false);
		setRunnableIntervalTime(250);
	}


	public void uninitialize() {
	}

	public double getCurrentSystemTime() {
		Date	date = new Date();
		return (double)date.getTime() / 1000.0;
	}

	private float vpos[]	= new float[3];
	private float center[]	= new float[3];
	private float size[]	= new float[3];
		
	public void update() {
		
		if (isEnabled() == false)
			return;

		SceneGraph sg = getSceneGraph();
		if (sg == null)
			return;

		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode();

		view.getPosition(vpos);
		
		getCenter(center);
		getSize(size);

		if (inRegion() == false) {
			if (isRegion(vpos, center, size) == true) {
				setInRegion(true);
				double time = getCurrentSystemTime();
				setEnterTime(time);
				sendEvent(getEventOut(enterTimeEventOutName));
				setIsActive(true);
				sendEvent(getEventOut(isActiveFieldName));
			}
		}
		else {
			if (isRegion(vpos, center, size) == false) {
				setInRegion(false);
				double time = getCurrentSystemTime();
				setExitTime(time);
				sendEvent(getEventOut(exitTimeEventOutName));
				setIsActive(false);
				sendEvent(getEventOut(isActiveFieldName));
			}
		}
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool enabled = (SFBool)getExposedField(enabledFieldName);
		SFVec3f center = (SFVec3f)getExposedField(centerFieldName);
		SFVec3f size = (SFVec3f)getExposedField(sizeFieldName);

		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "center " + center );
		printStream.println(indentString + "\t" + "size " + size );
	}
}
