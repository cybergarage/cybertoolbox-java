/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : CylinderSensor.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;

public class CylinderSensorNode extends Node {
	
	private String	enabledFieldName		= "enabled";
	private String	autoOffsetFieldName		= "autoOffset";
	private String	diskAngleFieldName		= "diskAngle";
	private String	minAngleFieldName		= "minAngle";
	private String	maxAngleFieldName		= "maxAngle";
	private String	offsetFieldName			= "offset";
	private String	rotationEventOutName	= "rotation";
	private String	trackPointEventOutName	= "trackPoint";

	public CylinderSensorNode() {
		setHeaderFlag(false);
		setType(cylinderSensorTypeName);

		// enabled exposed field
		SFBool enabled = new SFBool(true);
		addExposedField(enabledFieldName, enabled);

		// autoOffset exposed field
		SFBool autoOffset = new SFBool(true);
		addExposedField(autoOffsetFieldName, autoOffset);

		// diskAngle exposed field
		SFFloat diskAngle = new SFFloat(0.262f);
		addExposedField(diskAngleFieldName, diskAngle);

		// minAngle exposed field
		SFFloat minAngle = new SFFloat(0.0f);
		addExposedField(minAngleFieldName, minAngle);

		// maxAngle exposed field
		SFFloat maxAngle = new SFFloat(-1.0f);
		addExposedField(maxAngleFieldName, maxAngle);

		// offset exposed field
		SFFloat offset = new SFFloat(0.0f);
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
	//	DiskAngle
	////////////////////////////////////////////////
	
	public void setDiskAngle(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(diskAngleFieldName);
		sffloat.setValue(value);
	}
	public float getDiskAngle() {
		SFFloat sffloat = (SFFloat)getExposedField(diskAngleFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	MinAngle
	////////////////////////////////////////////////
	
	public void setMinAngle(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(minAngleFieldName);
		sffloat.setValue(value);
	}
	public float getMinAngle() {
		SFFloat sffloat = (SFFloat)getExposedField(minAngleFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	MaxAngle
	////////////////////////////////////////////////
	
	public void setMaxAngle(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(maxAngleFieldName);
		sffloat.setValue(value);
	}
	public float getMaxAngle() {
		SFFloat sffloat = (SFFloat)getExposedField(maxAngleFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	Offset
	////////////////////////////////////////////////
	
	public void setOffset(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(offsetFieldName);
		sffloat.setValue(value);
	}
	public float getOffset() {
		SFFloat sffloat = (SFFloat)getExposedField(offsetFieldName);
		return sffloat.getValue();
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
	public CylinderSensor next() {
		return (CylinderSensor)next(getType());
	}

	public CylinderSensor nextTraversal() {
		return (CylinderSensor)nextTraversalByType(getType());
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

		printStream.println(indentString + "\t" + "autoOffset " + autoOffset );
		printStream.println(indentString + "\t" + "diskAngle " + getDiskAngle() );
		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "maxAngle " + getMaxAngle() );
		printStream.println(indentString + "\t" + "minAngle " + getMinAngle() );
		printStream.println(indentString + "\t" + "offset " + getOffset() );
	}
}
