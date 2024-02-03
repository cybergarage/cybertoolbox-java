/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Group.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;

public class GroupNode extends GroupingNode {

	public GroupNode() {
		super();

		setHeaderFlag(false);
		setType(groupTypeName);
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isCommonNode() || node.isBindableNode() ||node.isInterpolatorNode() || node.isSensorNode() || node.isGroupingNode() || node.isSpecialGroupNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
		updateChildrenField();
		calculateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateChildrenField();
		//calculateBoundingBox();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Group next() {
		return (Group)next(getType());
	}

	public Group nextTraversal() {
		return (Group)nextTraversalByType(getType());
	}
*/

}
