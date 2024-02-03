/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFTime.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFTime extends ConstMField {

	public ConstMFTime() {
		setType(fieldTypeConstMFTime);
	}

	public ConstMFTime(MFTime times) {
		setType(fieldTypeConstMFTime);
		copy(times);
	}

	public ConstMFTime(ConstMFTime times) {
		setType(fieldTypeConstMFTime);
		copy(times);
	}

	public void addValue(double value) {
		SFTime sfvalue = new SFTime(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFTime sfvalue = new SFTime(value);
		add(sfvalue);
	}
	
	public void addValue(SFTime sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFTime sfvalue = new SFTime(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, double value) {
		SFTime sfvalue = new SFTime(value);
		insert(index, sfvalue);
	}

	public double get1Value(int index) {
		SFTime sfvalue = (SFTime)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return 0.0;
	}

	public void set1Value(int index, double value) {
		SFTime sfvalue = (SFTime)getField(index);
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
		MFTime field = new MFTime();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}