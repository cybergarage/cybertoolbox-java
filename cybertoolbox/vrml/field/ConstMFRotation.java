/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFRotation.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFRotation extends ConstMField {

	public ConstMFRotation() {
		setType(fieldTypeConstMFRotation);
	}

	public ConstMFRotation(MFRotation rotations) {
		setType(fieldTypeConstMFRotation);
		copy(rotations);
	}

	public ConstMFRotation(ConstMFRotation rotations) {
		setType(fieldTypeConstMFRotation);
		copy(rotations);
	}

	public void addValue(float x, float y, float z, float rot) {
		SFRotation rotation = new SFRotation(x, y, z, rot);
		add(rotation);
	}

	public void addValue(float value[]) {
		SFRotation rotation = new SFRotation(value);
		add(rotation);
	}

	public void addValue(String value) {
		SFRotation sfvalue = new SFRotation(value);
		add(sfvalue);
	}
	
	public void addValue(SFRotation rotation) {
		add(rotation);
	}

	public void insertValue(int index, float x, float y, float z, float rot) {
		SFRotation rotation = new SFRotation(x, y, z, rot);
		insert(index, rotation);
	}

	public void insertValue(int index, float value[]) {
		SFRotation rotation = new SFRotation(value);
		insert(index, rotation);
	}

	public void insertValue(int index, String value) {
		SFRotation rotation = new SFRotation(value);
		insert(index, rotation);
	}
	
	public void insertValue(int index, SFRotation rotation) {
		insert(index, rotation);
	}

	public void get1Value(int index, float value[]) {
		SFRotation rotation = (SFRotation)getField(index);
		if (rotation != null)
			rotation.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 1.0f;
			value[3] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFRotation rotation = (SFRotation)getField(index);
		if (rotation != null)
			rotation.setValue(value);
	}

	public void set1Value(int index, float x, float y, float z, float angle) {
		SFRotation rotation = (SFRotation)getField(index);
		if (rotation != null)
			rotation.setValue(x, y, z, angle);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[4];
		for (int n=0; n<getSize(); n++) {
			get1Value(n, value);
			if (n < getSize()-1)
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z] + " " + value[3] + ",");
			else	
				printStream.println(indentString + value[X] + " " + value[Y] + " " + value[Z] + " " + value[3]);
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
		MFRotation field = new MFRotation();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}