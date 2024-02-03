/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SphereSensor.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;

public class SphereSensorNode extends Node {
	
	private String	enabledFieldName		= "enabled";
	private String	autoOffsetFieldName		= "autoOffset";
	private String	offsetFieldName			= "offset";
	private String	rotationEventOutName	= "rotation";
	private String	trackPointEventOutName	= "trackPoint";

	public SphereSensorNode() {
		setHeaderFlag(false);
		setType(sphereSensorTypeName);

		// enabled exposed field
		SFBool enabled = new SFBool(true);
		addExposedField(enabledFieldName, enabled);

		// autoOffset exposed field
		SFBool autoOffset = new SFBool(true);
		addExposedField(autoOffsetFieldName, autoOffset);

		// offset exposed field
		SFRotation offset = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addExposedField(offsetFieldName, offset);

	
		// isActive eventOut field
		ConstSFBool isActive = new ConstSFBool(false);
		addEventOut(isActiveFieldName, isActive);

		// rotation eventOut field
		ConstSFRotation rotation = new ConstSFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		addEventOut(rotationEventOutName, rotation);

		// trackPoint eventOut field
		ConstSFVec3f trackPoint = new ConstSFVec3f(0.0f, 0.0f, 0.0f);
		addEventOut(trackPointEventOutName, trackPoint);
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
	//	AutoOffset
	////////////////////////////////////////////////
	
	public void setAutoOffset(boolean value) {
		SFBool sfbool = (SFBool)getExposedField(autoOffsetFieldName);
		sfbool.setValue(value);
	}
	public boolean getAutoOffset() {
		SFBool sfbool = (SFBool)getExposedField(autoOffsetFieldName);
		return sfbool.getValue();
	}
	public boolean isAutoOffset() {
		return getAutoOffset();
	}

	////////////////////////////////////////////////
	//	Offset
	////////////////////////////////////////////////
	
	public void setOffset(float value[]) {
		SFRotation sfrotation = (SFRotation)getExposedField(offsetFieldName);
		sfrotation.setValue(value);
	}
	public void getOffset(float value[]) {
		SFRotation sfrotation = (SFRotation)getExposedField(offsetFieldName);
		sfrotation.getValue(value);
	}

	////////////////////////////////////////////////
	//	isActive
	////////////////////////////////////////////////
	
	public void setIsActive(boolean value) {
		SFBool sfbool = (SFBool)getExposedField(isActiveFieldName);
		sfbool.setValue(value);
	}
	public boolean getIsActive() {
		SFBool sfbool = (SFBool)getExposedField(isActiveFieldName);
		return sfbool.getValue();
	}
	public boolean isActive() {
		return getAutoOffset();
	}

	////////////////////////////////////////////////
	//	Rotation
	////////////////////////////////////////////////
	
	public void setRotationChanged(float value[]) {
		ConstSFRotation time = (ConstSFRotation)getEventOut(rotationEventOutName);
		time.setValue(value);
	}
	public void setRotationChanged(float x, float y, float z, float rot) {
		ConstSFRotation time = (ConstSFRotation)getEventOut(rotationEventOutName);
		time.setValue(x, y, z, rot);
	}
	public void getRotationChanged(float value[]) {
		ConstSFRotation time = (ConstSFRotation)getEventOut(rotationEventOutName);
		time.getValue(value);
	}

	////////////////////////////////////////////////
	//	TrackPoint
	////////////////////////////////////////////////
	
	public void setTrackPointChanged(float value[]) {
		ConstSFVec3f sfvec3f = (ConstSFVec3f)getEventOut(trackPointEventOutName);
		sfvec3f.setValue(value);
	}
	public void setTrackPointChanged(float x, float y, float z) {
		ConstSFVec3f sfvec3f = (ConstSFVec3f)getEventOut(trackPointEventOutName);
		sfvec3f.setValue(x, y, z);
	}
	public void getTrackPointChanged(float value[]) {
		ConstSFVec3f sfvec3f = (ConstSFVec3f)getEventOut(trackPointEventOutName);
		sfvec3f.getValue(value);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public SphereSensor next() {
		return (SphereSensor)next(getType());
	}

	public SphereSensor nextTraversal() {
		return (SphereSensor)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
		setIsActive(false);
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool autoOffset = (SFBool)getExposedField(autoOffsetFieldName);
		SFBool enabled = (SFBool)getExposedField(enabledFieldName);
		SFRotation offset = (SFRotation)getExposedField(offsetFieldName);

		printStream.println(indentString + "\t" + "autoOffset " + autoOffset );
		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "offset " + offset );
	}
}
