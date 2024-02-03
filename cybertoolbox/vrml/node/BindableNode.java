/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: BindableNode.java
*
******************************************************************/

package vrml.node;

import vrml.*;
import vrml.field.*;

public abstract class BindableNode extends Node {

	private String	setBindFieldString			= "set_bind";
	private String	bindTimeFieldString			= "bindTime";
	private String	isBoundFieldString			= "isBound";

	public BindableNode() {
		setHeaderFlag(false);

		// set_bind
		SFBool setBind = new SFBool(true);
		addEventIn(setBindFieldString, setBind);

		// cybleInterval exposed field
		ConstSFTime bindTime = new ConstSFTime(1.0);
		addEventOut(bindTimeFieldString, bindTime);

		// isBind
		ConstSFBool isBound = new ConstSFBool(true);
		addEventOut(isBoundFieldString, isBound);
	}

	////////////////////////////////////////////////
	//	bind
	////////////////////////////////////////////////

	public void setBind(boolean value) {
		SFBool bind = (SFBool)getEventIn(setBindFieldString);
		bind.setValue(value);
	}
	public boolean getBind() {
		SFBool bind = (SFBool)getEventIn(setBindFieldString);
		return bind.getValue();
	}
	public Field getBindField() {
		return getEventIn(setBindFieldString);
	}

	////////////////////////////////////////////////
	//	bindTime
	////////////////////////////////////////////////
	
	public void setBindTime(double value) {
		ConstSFTime cycle = (ConstSFTime)getEventOut(bindTimeFieldString);
		cycle.setValue(value);
	}
	public double getBindTime() {
		ConstSFTime cycle = (ConstSFTime)getEventOut(bindTimeFieldString);
		return cycle.getValue();
	}
	public Field getBindTimeField() {
		return getEventOut(bindTimeFieldString);
	}

	////////////////////////////////////////////////
	//	isBound
	////////////////////////////////////////////////

	public void setIsBound(boolean  value) {
		ConstSFBool isBound = (ConstSFBool)getEventOut(isBoundFieldString);
		isBound.setValue(value);
	}
	public boolean getIsBound() {
		ConstSFBool isBound = (ConstSFBool)getEventOut(isBoundFieldString);
		return isBound.getValue();
	}
	public boolean isBound() {
		return getIsBound();
	}
	public Field getIsBoundField() {
		return getEventOut(isBoundFieldString);
	}
}

