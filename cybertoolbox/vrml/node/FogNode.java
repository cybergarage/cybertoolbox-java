/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Fog.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class FogNode extends BindableNode {

	//// Exposed Field ////////////////
	private String	colorExposedFieldName			= "color";
	private String	fogTypeExposedFieldName			= "fogType";
	private String	visibilityRangeExposedFieldName	= "visibilityRange";

	public FogNode() {

		setHeaderFlag(false);
		setType(fogTypeName);

		///////////////////////////
		// Exposed Field 
		///////////////////////////
		
		// color exposed field
		SFColor color = new SFColor(1, 1, 1);
		addExposedField(colorExposedFieldName, color);

		// fogType exposed field
		SFString fogType = new SFString("LINEAR");
		addExposedField(fogTypeExposedFieldName, fogType);

		// visibilityRange exposed field
		SFFloat visibilityRange = new SFFloat(0);
		addExposedField(visibilityRangeExposedFieldName, visibilityRange);
	}

	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public void setColor(float value[]) {
		SFColor color = (SFColor)getExposedField(colorExposedFieldName);
		color.setValue(value);
	}
	public void setColor(float r, float g, float b) {
		SFColor color = (SFColor)getExposedField(colorExposedFieldName);
		color.setValue(r, g, b);
	}
	public void getColor(float value[]) {
		SFColor color = (SFColor)getExposedField(colorExposedFieldName);
		color.getValue(value);
	}

	////////////////////////////////////////////////
	//	FogType
	////////////////////////////////////////////////

	public void setFogType(String value) {
		SFString fogType = (SFString)getExposedField(fogTypeExposedFieldName);
		fogType.setValue(value);
	}
	public String getFogType() {
		SFString fogType = (SFString)getExposedField(fogTypeExposedFieldName);
		return fogType.getValue();
	}

	public boolean isLiner() {
		String linerString = "LINEAR";
		return linerString.equalsIgnoreCase(getFogType());
	}

	public boolean isExponential() {
		String linerString = "EXPONENTIAL";
		return linerString.equalsIgnoreCase(getFogType());
	}
	
	////////////////////////////////////////////////
	//	VisibilityRange
	////////////////////////////////////////////////

	public void setVisibilityRange(float value) {
		SFFloat visibilityRange = (SFFloat)getExposedField(visibilityRangeExposedFieldName);
		visibilityRange.setValue(value);
	}
	public float getVisibilityRange() {
		SFFloat visibilityRange = (SFFloat)getExposedField(visibilityRangeExposedFieldName);
		return visibilityRange.getValue();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Fog next() {
		return (Fog)next(getType());
	}

	public Fog nextTraversal() {
		return (Fog)nextTraversalByType(getType());
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
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		SFColor color = (SFColor)getExposedField(colorExposedFieldName);
		SFString fogType = (SFString)getExposedField(fogTypeExposedFieldName);

		printStream.println(indentString + "\t" + "color " + color);
		printStream.println(indentString + "\t" + "fogType " + fogType);
		printStream.println(indentString + "\t" + "visibilityRange " + getVisibilityRange());

	}
}
