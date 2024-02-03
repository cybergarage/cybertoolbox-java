/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFNode.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class SFNode extends Field {

	private BaseNode mValue; 

	public SFNode() {
		setType(fieldTypeSFNode);
		mValue = null;
	}

	public SFNode(SFNode node) {
		setType(fieldTypeSFNode);
		setValue(node);
	}

	public SFNode(ConstSFNode node) {
		setType(fieldTypeSFNode);
		setValue(node);
	}

	public SFNode(BaseNode node) {
		setType(fieldTypeSFNode);
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
}
