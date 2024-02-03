/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFInt32.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFInt32 extends ConstMField {

	public ConstMFInt32() {
		setType(fieldTypeConstMFInt32);
	}

	public ConstMFInt32(MFInt32 values) {
		setType(fieldTypeConstMFInt32);
		copy(values);
	}

	public ConstMFInt32(ConstMFInt32 values) {
		setType(fieldTypeConstMFInt32);
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
	//	Index
	////////////////////////////////////////////////
	
	public int getNIndices() {
		int nTotal = 0;
		for (int n=0; n<getSize(); n++) {
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
		for (int n=0; n<getSize(); n++) {
			if (get1Value(n) == -1)
				nUnit++;
		}
		return nUnit;
	}

	public int getNTriangleIndexUnits() {
		int nUnit = 0;
		int nIndices = 0;
		for (int n=0; n<getSize(); n++) {
			if (get1Value(n) == -1) {
				if (2 < nIndices)
					nUnit += nIndices - 2;
				nIndices = 0;
			}
			else
				nIndices++;
		}
		return nUnit;
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		MFInt32 field = new MFInt32();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}