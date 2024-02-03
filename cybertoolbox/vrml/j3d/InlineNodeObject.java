/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InlineNodeObject.java
*
******************************************************************/

package vrml.j3d;

import java.io.*;

import javax.media.j3d.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;
import vrml.util.Debug;

public class InlineNodeObject extends GroupNodeObject implements NodeObject {

	public InlineNodeObject(InlineNode node) {
		initialize(node);
	}
	
	public boolean initialize(vrml.node.Node node) {
	
		Debug.message("InlineNodeObject.initialize");
	
		removeAllChildren();
		
		SceneGraph sg = node.getSceneGraph();
		if (sg == null)
			return false;
		
		SceneGraphJ3dObject sgObject = (SceneGraphJ3dObject)sg.getObject();
		if (sgObject == null)
			return false;
			
		InlineNode inlineNode = (InlineNode)node;
		
		if (inlineNode.getNUrls() <= 0)
			return false;
			
		String urlName = inlineNode.getUrl(0);
		
		if (urlName == null)
			return true;
			
		SceneGraph sgLoad = new SceneGraph();
		SceneGraphJ3dObject sgObjectLoad = new SceneGraphJ3dObject(sgLoad);
		sgLoad.setObject(sgObjectLoad);
		
		if (sgLoad.load(urlName) == false) {
			String dir = sg.getDirectory();
			if (sgLoad.load(new File(dir, urlName)) == false) {
				Debug.message("\tLoading is Bad !!");
				return false;
			}
		}
		
		Debug.message("\tLoading is OK !!");
		
		sgLoad.initialize();
		inlineNode.setBoundingBoxCenter(sgLoad.getBoundingBoxCenter());
		inlineNode.setBoundingBoxSize(sgLoad.getBoundingBoxSize());
		
		int nLoadNodes = sgLoad.getNNodes();
		Debug.message("\tLoadNodes = " + nLoadNodes);
		if (nLoadNodes <= 0)
			return true;
			
		BranchGroup branchGroup = sgObjectLoad.getBranchGroup();
		Debug.message("\taddChild = " + branchGroup);
		addChild(branchGroup);

		return true;
	}

	public boolean uninitialize(vrml.node.Node node) {
		return true;
	}
			
	public boolean update(vrml.node.Node node) {
		return true;
	}
}
