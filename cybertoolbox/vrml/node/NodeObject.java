/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NodeObject.java
*
******************************************************************/

package vrml.node;

public interface NodeObject {
	public boolean initialize(Node node);
	public boolean uninitialize(Node node);
	public boolean update(Node node);
	public boolean add(Node node);
	public boolean remove(Node node);
}
