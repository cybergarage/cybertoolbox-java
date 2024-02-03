/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFString.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFString extends ConstMField {

	public ConstMFString() {
		setType(fieldTypeConstMFString);
	}

	public ConstMFString(MFString strings) {
		setType(fieldTypeConstMFString);
		copy(strings);
	}

	public ConstMFString(ConstMFString strings) {
		setType(fieldTypeConstMFString);
		copy(strings);
	}


	public void addValue(String value) {
		SFString sfvalue = new SFString(value);
		add(sfvalue);
	}

	public void addValue(SFString sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFString sfvalue = new SFString(value);
		insert(index, sfvalue);
	}

	public String get1Value(int index) {
		SFString sfvalue = (SFString)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return null;
	}

	public void set1Value(int index, String value) {
		SFString sfvalue = (SFString)getField(index);
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
				printStream.println(indentString + "\"" + get1Value(n) + "\"" + ",");
			else	
				printStream.println(indentString + "\"" + get1Value(n) + "\"");
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
		MFString field = new MFString();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}