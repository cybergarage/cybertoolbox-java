/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Coordinate.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class CoordinateNode extends Node {

	private String	pointFieldName = "point";

	public CoordinateNode() {
		setHeaderFlag(false);
		setType(coordinateTypeName);

		// point exposed field
		MFVec3f mfpoint = new MFVec3f();
		mfpoint.setName(pointFieldName);
		addExposedField(mfpoint);
	}

	////////////////////////////////////////////////
	//	point 
	////////////////////////////////////////////////

	public void addPoint(float point[]) {
		MFVec3f mfpoint = (MFVec3f)getExposedField(pointFieldName);
		mfpoint.addValue(point);
	}

	public void addPoint(float x, float y, float z) {
		MFVec3f mfpoint = (MFVec3f)getExposedField(pointFieldName);
		mfpoint.addValue(x, y, z);
	}

	public int getNPoints() {
		MFVec3f mfpoint = (MFVec3f)getExposedField(pointFieldName);
		return mfpoint.getSize();
	}

	public void setPoint(int index, float point[]) {
		MFVec3f mfpoint = (MFVec3f)getExposedField(pointFieldName);
		mfpoint.set1Value(index, point);
	}

	public void setPoint(int index, float x, float y, float z) {
		MFVec3f mfpoint = (MFVec3f)getExposedField(pointFieldName);
		mfpoint.set1Value(index, x, y, z);
	}

	public void getPoint(int index, float point[]) {
		MFVec3f mfpoint = (MFVec3f)getExposedField(pointFieldName);
		mfpoint.get1Value(index, point);
	}

	public float[] getPoint(int index) {
		float value[] = new float[3];
		getPoint(index, value);
		return value;
	}

	public void removePoint(int index) {
		MFVec3f mfpoint = (MFVec3f)getExposedField(pointFieldName);
		mfpoint.removeValue(index);
	}

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
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float point[] = new float[3];
		printStream.println(indentString + "\tpoint [");
		for (int n=0; n<getNPoints(); n++) {
			getPoint(n, point);
			if (n < getNPoints()-1)
				printStream.println(indentString + "\t\t" + point[X] + " " + point[Y] + " " + point[Z] + ",");
			else	
				printStream.println(indentString + "\t\t" + point[X] + " " + point[Y] + " " + point[Z]);
		}
		printStream.println(indentString + "\t]");
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Coordinate next() {
		return (Coordinate)next(getType());
	}

	public Coordinate nextTraversal() {
		return (Coordinate)nextTraversalByType(getType());
	}
*/

}