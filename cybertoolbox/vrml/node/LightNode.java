/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: LightNode.java
*
******************************************************************/

package vrml.node;

import vrml.*;
import vrml.field.*;

public abstract class LightNode extends Node {

	private String	onFieldString					= "on";
	private String	intensityFieldString			= "intensity";
	private String	colorFieldString				= "color";

	public LightNode() {
		setHeaderFlag(false);

		// on exposed field
		SFBool bon = new SFBool(true);
		bon.setName(onFieldString);
		addExposedField(bon);

		// intensity exposed field
		SFFloat intensity = new SFFloat(1.0f);
		intensity.setName(intensityFieldString);
		addExposedField(intensity);

		// color exposed field
		SFColor color = new SFColor(1.0f, 1.0f, 1.0f);
		color.setName(colorFieldString);
		addExposedField(color);
	}

	////////////////////////////////////////////////
	//	On
	////////////////////////////////////////////////
	
	public void setOn(boolean on) {
		SFBool bon = (SFBool)getExposedField(onFieldString);
		bon.setValue(on);
	}
	public boolean getOn() {
		SFBool bon = (SFBool)getExposedField(onFieldString);
		return bon.getValue();
	}
	public boolean isOn() {
		SFBool bon = (SFBool)getExposedField(onFieldString);
		return bon.getValue();
	}
	public SFBool getOnField() {
		return (SFBool)getExposedField(onFieldString);
	}

	////////////////////////////////////////////////
	//	Intensity
	////////////////////////////////////////////////
	
	public void setIntensity(float value) {
		SFFloat intensity = (SFFloat)getExposedField(intensityFieldString);
		intensity.setValue(value);
	}
	public float getIntensity() {
		SFFloat intensity = (SFFloat)getExposedField(intensityFieldString);
		return intensity.getValue();
	}

	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public void setColor(float value[]) {
		SFColor color = (SFColor)getExposedField(colorFieldString);
		color.setValue(value);
	}
	public void setColor(float r, float g, float b) {
		SFColor color = (SFColor)getExposedField(colorFieldString);
		color.setValue(r, g, b);
	}
	public void getColor(float value[]) {
		SFColor color = (SFColor)getExposedField(colorFieldString);
		color.getValue(value);
	}
}