/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFVec2f.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFVec2f extends ConstMField {

	public ConstMFVec2f() {
		setType(fieldTypeConstMFVec2f);
	}

	public ConstMFVec2f(MFVec2f vectors) {
		setType(fieldTypeConstMFVec2f);
		copy(vectors);
	}

	public ConstMFVec2f(ConstMFVec2f vectors) {
		setType(fieldTypeConstMFVec2f);
		copy(vectors);
	}

	public void addValue(float x, float y) {
		SFVec2f vector = new SFVec2f(x, y);
		add(vector);
	}

	public void addValue(float value[]) {
		SFVec2f vector = new SFVec2f(value);
		add(vector);
	}

	public void addValue(String value) {
		SFVec2f sfvalue = new SFVec2f(value);
		add(sfvalue);
	}
	
	public void addValue(SFVec2f vector) {
		add(vector);
	}

	public void insertValue(int index, float x, float y) {
		SFVec2f vector = new SFVec2f(x, y);
		insert(index, vector);
	}

	public void insertValue(int index, float value[]) {
		SFVec2f vector = new SFVec2f(value);
		insert(index, vector);
	}

	public void insertValue(int index, String value) {
		SFVec2f vector = new SFVec2f(value);
		insert(index, vector);
	}
	
	public void insertValue(int index, SFVec2f vector) {
		insert(index, vector);
	}

	public void get1Value(int index, float value[]) {
		SFVec2f vector = (SFVec2f)getField(index);
		if (vector != null)
			vector.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFVec2f vector = (SFVec2f)getField(index);
		if (vector != null)
			vector.setValue(value);
	}

	public void set1Value(int index, float x, float y) {
		SFVec2f vector = (SFVec2f)getField(index);
		if (vector != null)
			vector.setValue(x, y);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[2];
		for (int n=0; n<getSize(); n++) {
			get1Value(n, value);
			if (n < getSize()-1)
				printStream.println(indentString + value[X] + " " + value[Y] + ",");
			else	
				printStream.println(indentString + value[X] + " " + value[Y]);
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
		MFVec2f field = new MFVec2f();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}