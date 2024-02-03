/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstMFNode.java
*
******************************************************************/

package vrml.field;

import java.io.PrintWriter;
import vrml.*;

public class ConstMFNode extends ConstMField {

	public ConstMFNode() {
		setType(fieldTypeConstMFNode);
	}

	public ConstMFNode(MFNode nodes) {
		setType(fieldTypeConstMFNode);
		copy(nodes);
	}

	public ConstMFNode(ConstMFNode nodes) {
		setType(fieldTypeConstMFNode);
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

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		MFNode field = new MFNode();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}