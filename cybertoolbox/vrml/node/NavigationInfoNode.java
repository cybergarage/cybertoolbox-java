/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NavigationInfo.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.lang.String;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class NavigationInfoNode extends BindableNode {
	
	//// Exposed Field ////////////////
	private String	visibilityLimitExposedFieldName	= "visibilityLimit";
	private String	avatarSizeExposedFieldName		= "avatarSize";
	private String	typeExposedFieldName			= "type";
	private String	headlightExposedFieldName		= "headlight";
	private String	speedExposedFieldName			= "speed";

	public NavigationInfoNode() {
		setHeaderFlag(false);
		setType(navigationInfoTypeName);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// visibilityLimit exposed field
		SFFloat visibilityLimit = new SFFloat(0);
		addExposedField(visibilityLimitExposedFieldName, visibilityLimit);

		// avatarSize exposed field
		MFFloat avatarSize = new MFFloat();
		addExposedField(avatarSizeExposedFieldName, avatarSize);

		// type exposed field
		MFString type = new MFString();
		addExposedField(typeExposedFieldName, type);

		// headlight exposed field
		SFBool headlight = new SFBool(false);
		addExposedField(headlightExposedFieldName, headlight);

		// speed exposed field
		SFFloat speed = new SFFloat(1);
		addExposedField(speedExposedFieldName, speed);
	}

	////////////////////////////////////////////////
	// Type
	////////////////////////////////////////////////

	public void addType(String value) {
		MFString type = (MFString)getExposedField(typeExposedFieldName);
		type.addValue(value);
	}
	public int getNTypes() {
		MFString type = (MFString)getExposedField(typeExposedFieldName);
		return type.getSize();
	}
	public void setType(int index, String value) {
		MFString type = (MFString)getExposedField(typeExposedFieldName);
		type.set1Value(index, value);
	}
	public String getType(int index) {
		MFString type = (MFString)getExposedField(typeExposedFieldName);
		return type.get1Value(index);
	}
	public void removeType(int index) {
		MFString type = (MFString)getExposedField(typeExposedFieldName);
		type.removeValue(index);
	}

	////////////////////////////////////////////////
	// avatarSize
	////////////////////////////////////////////////

	public void addAvatarSize(float value) {
		MFFloat avatarSize = (MFFloat)getExposedField(avatarSizeExposedFieldName);
		avatarSize.addValue(value);
	}
	public int getNAvatarSizes() {
		MFFloat avatarSize = (MFFloat)getExposedField(avatarSizeExposedFieldName);
		return avatarSize.getSize();
	}
	public void setAvatarSize(int index, float value) {
		MFFloat avatarSize = (MFFloat)getExposedField(avatarSizeExposedFieldName);
		avatarSize.set1Value(index, value);
	}
	public float getAvatarSize(int index) {
		MFFloat avatarSize = (MFFloat)getExposedField(avatarSizeExposedFieldName);
		return avatarSize.get1Value(index);
	}
	public void removeAvatarSize(int index) {
		MFFloat avatarSize = (MFFloat)getExposedField(avatarSizeExposedFieldName);
		avatarSize.removeValue(index);
	}

	////////////////////////////////////////////////
	//	Headlight
	////////////////////////////////////////////////
	
	public void setHeadlight(boolean value) {
		SFBool headlight = (SFBool)getExposedField(headlightExposedFieldName);
		headlight.setValue(value);
	}
	public boolean getHeadlight() {
		SFBool headlight = (SFBool)getExposedField(headlightExposedFieldName);
		return headlight.getValue();
	}

	////////////////////////////////////////////////
	//	VisibilityLimit
	////////////////////////////////////////////////

	public void setVisibilityLimit(float value) {
		SFFloat visibilityLimit = (SFFloat)getExposedField(visibilityLimitExposedFieldName);
		visibilityLimit.setValue(value);
	}
	public float getVisibilityLimit() {
		SFFloat visibilityLimit = (SFFloat)getExposedField(visibilityLimitExposedFieldName);
		return visibilityLimit.getValue();
	}

	////////////////////////////////////////////////
	//	Speed
	////////////////////////////////////////////////
	
	public void setSpeed(float value) {
		SFFloat time = (SFFloat)getExposedField(speedExposedFieldName);
		time.setValue(value);
	}
	public float getSpeed() {
		SFFloat time = (SFFloat)getExposedField(speedExposedFieldName);
		return time.getValue();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public NavigationInfo next() {
		return (NavigationInfo)next(getType());
	}

	public NavigationInfo nextTraversal() {
		return (NavigationInfo)nextTraversalByType(getType());
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
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool headlight = (SFBool)getExposedField(headlightExposedFieldName);

		printStream.println(indentString + "\t" + "visibilityLimit " + getVisibilityLimit());
		printStream.println(indentString + "\t" + "headlight " + headlight );
		printStream.println(indentString + "\t" + "speed " + getSpeed() );

		MFString type = (MFString)getExposedField(typeExposedFieldName);
		printStream.println(indentString + "\t" + "type [");
		type.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFFloat avatarSize = (MFFloat)getExposedField(avatarSizeExposedFieldName);
		printStream.println(indentString + "\t" + "avatarSize [");
		avatarSize.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
