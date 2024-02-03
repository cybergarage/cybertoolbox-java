/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFInt32.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;
import vrml.util.Debug;

public class MFInt32 extends MField {

	public MFInt32() {
		setType(fieldTypeMFInt32);
	}

	public MFInt32(MFInt32 values) {
		setType(fieldTypeMFInt32);
		copy(values);
	}

	public MFInt32(ConstMFInt32 values) {
		setType(fieldTypeMFInt32);
		copy(values);
	}

	public void addValue(int value) {
		SFInt32 sfvalue = new SFInt32(value);
		add(sfvalue);
	}

	public void addValue(String value) {
		SFInt32 sfvalue = new SFInt32(value);
		add(sfvalue);
	}

	public void addValue(SFInt32 sfvalue) {
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFInt32 sfvalue = new SFInt32(value);
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, int value) {
		SFInt32 sfvalue = new SFInt32(value);
		insert(index, sfvalue);
	}

	public int get1Value(int index) {
		SFInt32 sfvalue = (SFInt32)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return 0;
	}

	public void set1Value(int index, int value) {
		SFInt32 sfvalue = (SFInt32)getField(index);
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

	////////////////////////////////////////////////
	//	Index
	////////////////////////////////////////////////
	
	public int getNIndices() {
		int nTotal = 0;
		int size = getSize();
		for (int n=0; n<size; n++) {
			if (get1Value(n) != -1)
				nTotal++;
		}
		return nTotal;
	}

	public int getNTriangleIndices() {
		return getNTriangleIndexUnits() * 3;
	}
		
	public int getNIndexUnits() {
		int nUnit = 0;
		int size = getSize();
		for (int n=0; n<size; n++) {
			if (get1Value(n) == -1 || n == (size - 1))
				nUnit++;
		}
		return nUnit;
	}

	public int getNTriangleIndexUnits() {
		int nUnit = 0;
		int nIndices = 0;
		int size = getSize();
		for (int n=0; n<size; n++) {
			if (get1Value(n) == -1 || n == (size - 1)) {
				if (n == (size - 1))
					nIndices++;
				if (2 < nIndices)
					nUnit += nIndices - 2;
				nIndices = 0;
			}
			else
				nIndices++;
		}
		Debug.message("MFInt32::getNTriangleIndexUnits = " + nUnit);
		return nUnit;
	}
}