/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFColor.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFColor extends ConstMField {

	public ConstMFColor() {
		setType(fieldTypeConstMFColor);
	}

	public ConstMFColor(MFColor colors) {
		setType(fieldTypeConstMFColor);
		copy(colors);
	}

	public ConstMFColor(ConstMFColor colors) {
		setType(fieldTypeConstMFColor);
		copy(colors);
	}

	public void addValue(float r, float g, float b) {
		SFColor color = new SFColor(r, g, b);
		add(color);
	}

	public void addValue(float value[]) {
		SFColor color = new SFColor(value);
		add(color);
	}

	public void addValue(String value) {
		SFColor sfvalue = new SFColor(value);
		add(sfvalue);
	}
	
	public void addValue(SFColor color) {
		add(color);
	}

	public void insertValue(int index, float r, float g, float b) {
		SFColor color = new SFColor(r, g, b);
		insert(index, color);
	}

	public void insertValue(int index, float value[]) {
		SFColor color = new SFColor(value);
		insert(index, color);
	}

	public void insertValue(int index, String value) {
		SFColor color = new SFColor(value);
		insert(index, color);
	}
	
	public void insertValue(int index, SFColor color) {
		insert(index, color);
	}

	public void get1Value(int index, float value[]) {
		SFColor color = (SFColor)getField(index);
		if (color != null)
			color.getValue(value);
		else {
			value[0] = 0.0f;
			value[1] = 0.0f;
			value[2] = 0.0f;
		}
	}

	public void set1Value(int index, float value[]) {
		SFColor color = (SFColor)getField(index);
		if (color != null)
			color.setValue(value);
	}

	public void set1Value(int index, float r, float g, float b) {
		SFColor color = (SFColor)getField(index);
		if (color != null)
			color.setValue(r, g, b);
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
		MFColor field = new MFColor();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}