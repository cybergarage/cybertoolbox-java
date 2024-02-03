/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TextureCoordinate.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class TextureCoordinateNode extends Node {
	
	private String	pointFieldName = "point";

	public TextureCoordinateNode() {
		setHeaderFlag(false);
		setType(textureCoordinateTypeName);

		// point exposed field
		MFVec2f mfpoint = new MFVec2f();
		mfpoint.setName(pointFieldName);
		addExposedField(mfpoint);
	}

	////////////////////////////////////////////////
	//	point 
	////////////////////////////////////////////////

	public void addPoint(float point[]) {
		MFVec2f mfpoint = (MFVec2f)getExposedField(pointFieldName);
		mfpoint.addValue(point);
	}

	public void addPoint(float x, float y) {
		MFVec2f mfpoint = (MFVec2f)getExposedField(pointFieldName);
		mfpoint.addValue(x, y);
	}

	public int getNPoints() {
		MFVec2f mfpoint = (MFVec2f)getExposedField(pointFieldName);
		return mfpoint.getSize();
	}

	public void setPoint(int index, float point[]) {
		MFVec2f mfpoint = (MFVec2f)getExposedField(pointFieldName);
		mfpoint.set1Value(index, point);
	}

	public void setPoint(int index, float x, float y) {
		MFVec2f mfpoint = (MFVec2f)getExposedField(pointFieldName);
		mfpoint.set1Value(index, x, y);
	}

	public void getPoint(int index, float point[]) {
		MFVec2f mfpoint = (MFVec2f)getExposedField(pointFieldName);
		mfpoint.get1Value(index, point);
	}

	public float[] getPoint(int index) {
		float value[] = new float[2];
		getPoint(index, value);
		return value;
	}

	public void removePoint(int index) {
		MFVec2f mfpoint = (MFVec2f)getExposedField(pointFieldName);
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
		float point[] = new float[2];
		printStream.println(indentString + "\tpoint [");
		for (int n=0; n<getNPoints(); n++) {
			getPoint(n, point);
			if (n < getNPoints()-1)
				printStream.println(indentString + "\t\t" + point[X] + " " + point[Y] +  ",");
			else	
				printStream.println(indentString + "\t\t" + point[X] + " " + point[Y]);
		}
		printStream.println(indentString + "\t]");
	}
	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public TextureCoordinate next() {
		return (TextureCoordinate)next(getType());
	}

	public TextureCoordinate nextTraversal() {
		return (TextureCoordinate)nextTraversalByType(getType());
	}
*/

}