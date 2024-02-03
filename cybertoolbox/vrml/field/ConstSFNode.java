/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstSFNode.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class ConstSFNode extends ConstField {

	private BaseNode mValue; 

	public ConstSFNode() {
		setType(fieldTypeConstSFNode);
		mValue = null;
	}

	public ConstSFNode(SFNode node) {
		setType(fieldTypeConstSFNode);
		setValue(node);
	}

	public ConstSFNode(ConstSFNode node) {
		setType(fieldTypeConstSFNode);
		setValue(node);
	}

	public ConstSFNode(BaseNode node) {
		setType(fieldTypeConstSFNode);
		setValue(node);
	}

	public void setValue(BaseNode node) {
		if (mValue != null) {
			synchronized (mValue) {
				mValue = node;
			}
		}
		else
			mValue = node;
	}

	public void setValue(SFNode node) {
		setValue(node.getValue());
	}

	public void setValue(ConstSFNode node) {
		setValue(node.getValue());
	}

	public void setValue(String string) {
	}

	public BaseNode getValue() {
		BaseNode value;
		if (mValue != null) {
			synchronized (mValue) {
				value = mValue;
			}
		}
		else
			value = mValue;
		
		return value;
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (BaseNode)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mValue) {
			object = mValue;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		BaseNode node = getValue();
		if (node == null)
			return null;
		return node.toString();
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		SFNode field = new SFNode();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}
