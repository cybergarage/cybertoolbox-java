/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFVec3f.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFVec3f extends ConstMField {

	public ConstMFVec3f() {
		setType(fieldTypeConstMFVec3f);
	}

	public ConstMFVec3f(MFVec3f vectors) {
		setType(fieldTypeConstMFVec3f);
		copy(vectors);
	}

	public ConstMFVec3f(ConstMFVec3f vectors) {
		setType(fieldTypeConstMFVec3f);
		copy(vectors);
	}

	public void addValue(float x, float y, float z) {
		SFVec3f vector = new SFVec3f(x, y, z);
		add(vector);
	}

	public void addValue(float value[]) {
		SFVec3f vector = new SFVec3f(value);
		add(vector);
	}

	public void addValue(String value) {
		SFVec3f sfvalue = new SFVec3f(value);
		add(sfvalue);
	}
	
	public void addValue(SFVec3f vector) {
		add(vector);
	}

	public void insertValue(int index, float x, float y, float z) {
		SFVec3f vector = new SFVec3f(x, y, z);
		insert(index, vector);
	}

	public void insertValue(int index, float value[]) {
		SFVec3f vector = new SFVec3f(value);
		insert(index, vector);
	}

	public void insertValue(int index, String value) {
		SFVec3f vector = new SFVec3f(value);
		insert(index, vector);
	}
	
	public void insertValue(int index, SFVec3f vector) {
		insert(index, vector);
	}

	public void get1Value(int index, float value[]) {
		SFVec3f vector = (SFVec3f)getField(index);
		if (vector != null)
			vector.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFVec3f vector = (SFVec3f)getField(index);
		if (vector != null)
			vector.setValue(value);
	}

	public void set1Value(int index, float x, float y, float z) {
		SFVec3f vector = (SFVec3f)getField(index);
		if (vector != null)
			vector.setValue(x, y, z);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[3];
		for (int n=0; n<getSize(); n++) {
			get1Value(n, value);
			if (n < getSize()-1)
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z] + ",");
			else	
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z]);
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		MFVec3f field = new MFVec3f();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}