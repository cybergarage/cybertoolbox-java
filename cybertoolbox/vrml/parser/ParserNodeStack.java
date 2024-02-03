/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ParserNodeStack.java
*
******************************************************************/

package vrml.parser;

import vrml.*;
import vrml.node.*;
import vrml.util.*;

public class ParserNodeStack extends LinkedListNode {
	private Node	mNode;
	private int		mType;

	public ParserNodeStack(Node node, int type) { 
		setHeaderFlag(false); 
		mNode = node; 
		mType = type;
	}
	
	Node getObject() { 
		return mNode; 
	}
	
	int getType() { 
		return mType; 
	}
};
