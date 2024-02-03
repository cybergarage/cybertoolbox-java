/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : BaseNode.java
*
******************************************************************/

package vrml;

import java.util.Vector;
import java.io.*;

import vrml.util.*;
import vrml.node.*;
import vrml.route.*;
import vrml.field.*;
import vrml.field.SFMatrix;

public abstract class BaseNode extends LinkedListNode  implements Constants {

	public BaseNode() {
		setHeaderFlag(true);
		setType(null);
		setName(null);
	}
	
	////////////////////////////////////////////////
	//	Name / Type
	////////////////////////////////////////////////

	protected String	mName;
	protected String	mType;

	public String checkName(String name) {
		if (name == null)
			return null;
		StringBuffer newName = new StringBuffer(name);
		for (int n=0; n<newName.length(); n++) {
			char c = newName.charAt(n);
			if (c == ' ')
				newName.setCharAt(n, '_');
		}
		return newName.toString();
	}
	
	public void setName(String name) {
		mName = checkName(name);
	}

	public String getName() {
		return mName;
	}

	public boolean hasName() {
		String name = getName();
		if (name == null)
			return false;
		if (name.length() == 0)
			return false;
		return true;
	}
	
	public void setType(String name) {
		mType = name;
	}

	public String getType() {
		return mType;
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		String name = getName();
		if (name != null) {
			if (0 < name.length() )
				return getType() + " - " + name;
			else
				return getType();
		}
		else
			return getType();
	}
}
