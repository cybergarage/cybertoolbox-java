/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PlaneSensor.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;

public class PlaneSensorNode extends Node {
	
	private String	enabledFieldName		= "enabled";
	private String	autoOffsetFieldName		= "autoOffset";
	private String	minPositionFieldName	= "minPosition";
	private String	maxPositionFieldName	= "maxPosition";
	private String	offsetFieldName			= "offset";
	private String	translationEventOutName	= "translation";
	private String	trackPointEventOutName	= "trackPoint";

	public PlaneSensorNode() {
		setHeaderFlag(false);
		setType(planeSensorTypeName);

		// enabled exposed field
		SFBool enabled = new SFBool(true);
		addExposedField(enabledFieldName, enabled);

		// autoOffset exposed field
		SFBool autoOffset = new SFBool(true);
		addExposedField(autoOffsetFieldName, autoOffset);

		// minPosition exposed field
		SFVec2f minPosition = new SFVec2f(0.0f, 0.0f);
		addExposedField(minPositionFieldName, minPosition);

		// maxAngle exposed field
		SFVec2f maxPosition = new SFVec2f(-1.0f, -1.0f);
		addExposedField(maxPositionFieldName, maxPosition);

		// offset exposed field
		SFVec3f offset = new SFVec3f(0.0f, 0.0f, 0.0f);
		addExposedField(offsetFieldName, offset);
	
		// isActive eventOut field
		ConstSFBool isActive = new ConstSFBool(false);
		addEventOut(isActiveFieldName, isActive);

		// translation eventOut field
		ConstSFVec3f translation = new ConstSFVec3f(0.0f, 0.0f, 0.0f);
		addEventOut(translationEventOutName, translation);

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
	//	MinPosition
	////////////////////////////////////////////////
	
	public void setMinPosition(float value[]) {
		SFVec2f sfvec2f = (SFVec2f)getExposedField(minPositionFieldName);
		sfvec2f.setValue(value);
	}
	public void setMinPosition(float x, float y) {
		SFVec2f sfvec2f = (SFVec2f)getExposedField(minPositionFieldName);
		sfvec2f.setValue(x, y);
	}
	public void getMinPosition(float value[]) {
		SFVec2f sfvec2f = (SFVec2f)getExposedField(minPositionFieldName);
		sfvec2f.getValue();
	}

	////////////////////////////////////////////////
	//	MaxPosition
	////////////////////////////////////////////////
	
	public void setMaxPosition(float value[]) {
		SFVec2f sfvec2f = (SFVec2f)getExposedField(maxPositionFieldName);
		sfvec2f.setValue(value);
	}
	public void setMaxPosition(float x, float y) {
		SFVec2f sfvec2f = (SFVec2f)getExposedField(maxPositionFieldName);
		sfvec2f.setValue(x, y);
	}
	public void getMaxPosition(float value[]) {
		SFVec2f sfvec2f = (SFVec2f)getExposedField(maxPositionFieldName);
		sfvec2f.getValue();
	}

	////////////////////////////////////////////////
	//	Offset
	////////////////////////////////////////////////
	
	public void setOffset(float value[]) {
		SFVec3f vector = (SFVec3f)getExposedField(offsetFieldName);
		vector.setValue(value);
	}
	public void getOffset(float value[]) {
		SFVec3f vector = (SFVec3f)getExposedField(offsetFieldName);
		vector.getValue(value);
	}

	////////////////////////////////////////////////
	//	isActive
	////////////////////////////////////////////////
	
	public ConstSFBool getIsActiveField() {
		return (ConstSFBool)getExposedField(isActiveFieldName);
	}
	public void setIsActive(boolean value) {
		ConstSFBool sfbool = (ConstSFBool)getExposedField(isActiveFieldName);
		sfbool.setValue(value);
	}
	public boolean getIsActive() {
		ConstSFBool sfbool = (ConstSFBool)getExposedField(isActiveFieldName);
		return sfbool.getValue();
	}
	public boolean isActive() {
		return getAutoOffset();
	}

	////////////////////////////////////////////////
	//	Translation
	////////////////////////////////////////////////
	
	public ConstSFVec3f getTranslationChangedField() {
		return (ConstSFVec3f)getEventOut(translationEventOutName);
	}
	public void setTranslationChanged(float value[]) {
		ConstSFVec3f translation = (ConstSFVec3f)getEventOut(translationEventOutName);
		translation.setValue(value);
	}
	public void setTranslationChanged(float x, float y, float z) {
		ConstSFVec3f translation = (ConstSFVec3f)getEventOut(translationEventOutName);
		translation.setValue(x, y, z);
	}
	public void getTranslationChanged(float value[]) {
		ConstSFVec3f translation = (ConstSFVec3f)getEventOut(translationEventOutName);
		translation.getValue(value);
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
	public PlaneSensor next() {
		return (PlaneSensor)next(getType());
	}

	public PlaneSensor nextTraversal() {
		return (PlaneSensor)nextTraversalByType(getType());
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
		SFVec2f maxpos = (SFVec2f)getExposedField(maxPositionFieldName);
		SFVec2f minpos = (SFVec2f)getExposedField(maxPositionFieldName);
		SFVec3f offset = (SFVec3f)getExposedField(offsetFieldName);

		printStream.println(indentString + "\t" + "autoOffset " + autoOffset );
		printStream.println(indentString + "\t" + "enabled " + enabled );
		printStream.println(indentString + "\t" + "maxPosition " + maxpos );
		printStream.println(indentString + "\t" + "minPosition " + minpos );
		printStream.println(indentString + "\t" + "offset " + offset );
	}
}
