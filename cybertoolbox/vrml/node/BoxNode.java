/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: Box.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class BoxNode extends GeometryNode {

	private String	sizeFieldName		= "size";
	
	public BoxNode() {

		setHeaderFlag(false);
		setType(boxTypeName);

		// size exposed field
		SFVec3f size = new SFVec3f(2.0f, 2.0f, 2.0f);
		size.setName(sizeFieldName);
		addExposedField(size);
	}

	////////////////////////////////////////////////
	//	size
	////////////////////////////////////////////////

	public void setSize(float value[]) {
		SFVec3f size = (SFVec3f)getExposedField(sizeFieldName);
		size.setValue(value);
	}
	public void setSize(float x, float y, float z) {
		SFVec3f size = (SFVec3f)getExposedField(sizeFieldName);
		size.setValue(x, y, z);
	}
	public void getSize(float value[]) {
		SFVec3f size = (SFVec3f)getExposedField(sizeFieldName);
		size.getValue(value);
	}
	public float getX() {
		return ((SFVec3f)getExposedField(sizeFieldName)).getX();
	}
	public float getY() {
		return ((SFVec3f)getExposedField(sizeFieldName)).getY();
	}
	public float getZ() {
		return ((SFVec3f)getExposedField(sizeFieldName)).getZ();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Box next() {
		return (Box)next(getType());
	}

	public Box nextTraversal() {
		return (Box)nextTraversalByType(getType());
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
		setBoundingBoxSize(getX()/2.0f, getY()/2.0f, getZ()/2.0f);
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[3];
		getSize(value);	printStream.println(indentString + "\t" + "size " + value[X] + " "+ value[Y] + " " + value[Z] );
	}
}
