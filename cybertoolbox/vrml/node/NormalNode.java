/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Normal.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class NormalNode extends Node {

	private String	vectorFieldName = "vector";

	public NormalNode () {
		setHeaderFlag(false);
		setType(normalTypeName);

		// vector exposed field
		MFVec3f vector = new MFVec3f();
		vector.setName(vectorFieldName);
		addExposedField(vector);
	}

	////////////////////////////////////////////////
	//	vector
	////////////////////////////////////////////////
	
	public void addVector(float value[]) {
		MFVec3f vector = (MFVec3f)getExposedField(vectorFieldName);
		vector.addValue(value);
	}
	public void addVector(float x, float y, float z) {
		MFVec3f vector = (MFVec3f)getExposedField(vectorFieldName);
		vector.addValue(x, y, z);
	}
	public int getNVectors() {
		MFVec3f vector = (MFVec3f)getExposedField(vectorFieldName);
		return vector.getSize();
	}
	public void setVector(int index, float value[]) {
		MFVec3f vector = (MFVec3f)getExposedField(vectorFieldName);
		vector.set1Value(index, value);
	}
	public void setVector(int index, float x, float y, float z) {
		MFVec3f vector = (MFVec3f)getExposedField(vectorFieldName);
		vector.set1Value(index, x, y, z);
	}
	public void getVector(int index, float value[]) {
		MFVec3f vector = (MFVec3f)getExposedField(vectorFieldName);
		vector.get1Value(index, value);
	}
	public float[] getVector(int index) {
		float value[] = new float[3];
		getVector(index, value);
		return value;
	}
	public void removeVector(int index) {
		MFVec3f vector = (MFVec3f)getExposedField(vectorFieldName);
		vector.removeValue(index);
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
		float vector[] = new float[3];
		printStream.println(indentString + "\tvector [");
		for (int n=0; n<getNVectors(); n++) {
			getVector(n, vector);
			if (n < getNVectors()-1)
				printStream.println(indentString + "\t\t" + vector[X] + " " + vector[Y] + " " + vector[Z] + ",");
			else	
				printStream.println(indentString + "\t\t" + vector[X] + " " + vector[Y] + " " + vector[Z]);
		}
		printStream.println(indentString + "\t]");
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Normal next() {
		return (Normal)next(getType());
	}

	public Normal nextTraversal() {
		return (Normal)nextTraversalByType(getType());
	}
*/

}