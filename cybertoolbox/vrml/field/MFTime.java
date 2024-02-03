/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFTime.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class MFTime extends MField {
	
	public MFTime() {
		setType(fieldTypeMFTime);
	}

	public MFTime(MFTime times) {
		setType(fieldTypeMFTime);
		copy(times);
	}

	public MFTime(ConstMFTime times) {
		setType(fieldTypeMFTime);
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
}