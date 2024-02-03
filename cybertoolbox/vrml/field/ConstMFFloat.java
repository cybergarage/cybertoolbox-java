/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFFloat.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFFloat extends ConstMField {

	public ConstMFFloat() {
		setType(fieldTypeConstMFFloat);
	}

	public ConstMFFloat(MFFloat values) {
		setType(fieldTypeConstMFFloat);
		copy(values);
	}

	public ConstMFFloat(ConstMFFloat values) {
		setType(fieldTypeConstMFFloat);
		copy(values);
	}

	public void addValue(float value) {
		SFFloat sfvalue = new SFFloat(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFFloat sfvalue = new SFFloat(value);
		add(sfvalue);
	}
	
	public void addValue(SFFloat sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFFloat sfvalue = new SFFloat(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, float value) {
		SFFloat sfvalue = new SFFloat(value);
		insert(index, sfvalue);
	}

	public float get1Value(int index) {
		SFFloat sfvalue = (SFFloat)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return 0.0f;		
	}

	public void set1Value(int index, float value) {
		SFFloat sfvalue = (SFFloat)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(value);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		float value[] = new float[3];
		for (int n=0; n<getSize(); n++) {
			if (n < getSize()-1)
				printStream.println(indentString + get1Value(n) + ",");
			else	
				printStream.println(indentString + get1Value(n));
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
		MFFloat field = new MFFloat();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}