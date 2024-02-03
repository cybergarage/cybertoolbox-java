/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: Cone.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.util.*;
import vrml.field.*;

public class ConeNode extends GeometryNode {

	private String	bottomRadiusFieldName	= "bottomRadius";
	private String	heightFieldName			= "height";
	private String	topFieldName			= "top";
	private String	sideFieldName			= "side";
	private String	bottomFieldName			= "bottom";
	
	public ConeNode() {

		setHeaderFlag(false);
		setType(coneTypeName);

		// bottomRadius field
		SFFloat bottomRadius = new SFFloat(1.0f);
		addExposedField(bottomRadiusFieldName, bottomRadius);

		// height field
		SFFloat height = new SFFloat(2.0f);
		addExposedField(heightFieldName, height);

		// side field
		SFBool side = new SFBool(true);
		addExposedField(sideFieldName, side);

		// bottom field
		SFBool bottom = new SFBool(true);
		addExposedField(bottomFieldName, bottom);
	}

	////////////////////////////////////////////////
	//	bottomRadius
	////////////////////////////////////////////////

	public void setBottomRadius(float value) {
		SFFloat bottomRadius = (SFFloat)getExposedField(bottomRadiusFieldName);
		bottomRadius.setValue(value);
	}
	public float getBottomRadius() {
		SFFloat bottomRadius = (SFFloat)getExposedField(bottomRadiusFieldName);
		return bottomRadius.getValue();
	}

	////////////////////////////////////////////////
	//	height
	////////////////////////////////////////////////

	public void setHeight(float value) {
		SFFloat height = (SFFloat)getExposedField(heightFieldName);
		height.setValue(value);
	}
	public float getHeight() {
		SFFloat height = (SFFloat)getExposedField(heightFieldName);
		return height.getValue();
	}

	////////////////////////////////////////////////
	//	side
	////////////////////////////////////////////////

	public void setSide(boolean value) {
		SFBool side = (SFBool)getExposedField(sideFieldName);
		side.setValue(value);
	}
	public boolean getSide() {
		SFBool side = (SFBool)getExposedField(sideFieldName);
		return side.getValue();
	}

	////////////////////////////////////////////////
	//	bottom
	////////////////////////////////////////////////

	public void setBottom(boolean value) {
		SFBool bottom = (SFBool)getExposedField(bottomFieldName);
		bottom.setValue(value);
	}
	public boolean getBottom() {
		SFBool bottom = (SFBool)getExposedField(bottomFieldName);
		return bottom.getValue();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Cone next() {
		return (Cone)next(getType());
	}

	public Cone nextTraversal() {
		return (Cone)nextTraversalByType(getType());
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
		calculateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void calculateBoundingBox() {
		setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
		setBoundingBoxSize(getBottomRadius(), getHeight()/2.0f, getBottomRadius());
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool side = (SFBool)getExposedField(sideFieldName);
		SFBool bottom = (SFBool)getExposedField(bottomFieldName);

		printStream.println(indentString + "\t" + "bottomRadius " + getBottomRadius());
		printStream.println(indentString + "\t" + "height " + getHeight());
		printStream.println(indentString + "\t" + "side " + side);
		printStream.println(indentString + "\t" + "bottom " + bottom);
	}
}
