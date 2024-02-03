/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFString.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class MFString extends MField {

	public MFString() {
		setType(fieldTypeMFString);
	}

	public MFString(MFString strings) {
		setType(fieldTypeMFString);
		copy(strings);
	}

	public MFString(ConstMFString strings) {
		setType(fieldTypeMFString);
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
}