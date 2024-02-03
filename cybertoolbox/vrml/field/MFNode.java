/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MFNode.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class MFNode extends MField {

	public MFNode() {
		setType(fieldTypeMFNode);
	}

	public MFNode(MFNode nodes) {
		setType(fieldTypeMFNode);
		copy(nodes);
	}

	public MFNode(ConstMFNode nodes) {
		setType(fieldTypeMFNode);
		copy(nodes);
	}

	public void addValue(String value) {
		SFNode sfvalue = new SFNode();
		add(sfvalue);
	}

	public void addValue(BaseNode node) {
		SFNode sfvalue = new SFNode(node);
		add(sfvalue);
	}

	public void insertValue(int index, String value) {
		SFNode sfvalue = new SFNode();
		insert(index, sfvalue);
	}
	
	public void insertValue(int index, BaseNode node) {
		SFNode sfvalue = new SFNode(node);
		insert(index, sfvalue);
	}

	public BaseNode get1Value(int index) {
		SFNode sfvalue = (SFNode)getField(index);
		if (sfvalue != null)
			return sfvalue.getValue();
		return null;
	}

	public void set1Value(int index, BaseNode node) {
		SFNode sfvalue = (SFNode)getField(index);
		if (sfvalue != null)
			sfvalue.setValue(node);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return null;
	}
}