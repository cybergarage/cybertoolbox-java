/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ProtoParameterList.java
*
******************************************************************/

package vrml.parser;

import vrml.util.*;

public class ProtoParameterList extends LinkedList {

	public ProtoParameterList() {
	}
	
	////////////////////////////////////////////////
	//	Parameter
	////////////////////////////////////////////////

	public void addParameter(String type, String name, String defalutValue) {
		ProtoParameter param = new ProtoParameter(type, name, defalutValue);
		addNode(param);
	}

	public ProtoParameter getParameters() {
		return (ProtoParameter)getNodes();
	}
	
	public ProtoParameter getParameter(String name) {
		for (ProtoParameter param=getParameters(); param != null; param=param.next()) {
			String paramName = param.getName();
			if (paramName.compareTo(name) == 0)
				return param;
		}
		return null;
	}
}
