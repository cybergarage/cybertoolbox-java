/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Shape.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class ShapeNode extends Node {

	//// ExposedField ////////////////
	private String	appearanceExposedFieldName		= "appearance";
	private String	geometryExposedFieldName		= "geometry";

	public ShapeNode() {
		setHeaderFlag(false);
		setType(shapeTypeName);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// appearance field
		SFNode app = new SFNode();
		addExposedField(appearanceExposedFieldName, app);

		// geometry field
		SFNode geom = new SFNode();
		addExposedField(geometryExposedFieldName, geom);
	}

	////////////////////////////////////////////////
	//	Appearance
	////////////////////////////////////////////////

	public SFNode getAppearanceField() {
		return (SFNode)getExposedField(appearanceExposedFieldName);
	}
	
	public void updateAppearanceField() {
		getAppearanceField().setValue(getAppearanceNodes());
	}

	////////////////////////////////////////////////
	//	Geometry
	////////////////////////////////////////////////

	public SFNode getGeometryField() {
		return (SFNode)getExposedField(geometryExposedFieldName);
	}
	
	public void updateGeometryField() {
		getGeometryField().setValue(getGeometryNode());
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Shape next() {
		return (Shape)next(getType());
	}

	public Shape nextTraversal() {
		return (Shape)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isAppearanceNode() || node.isGeometryNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
		updateAppearanceField();
		updateGeometryField();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateAppearanceField();
		//updateGeometryField();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		AppearanceNode appearance = getAppearanceNodes();
		if (appearance != null) {
			if (appearance.isInstanceNode() == false) {
				String nodeName = appearance.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "appearance DEF " + appearance.getName() + " Appearance {");
				else
					printStream.println(indentString + "\t" + "appearance Appearance {");
				appearance.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "appearance USE " + appearance.getName());
		}
		
		Node geonode = getGeometryNode();
		if (geonode != null) {
			if (geonode.isInstanceNode() == false) {
				String nodeName = geonode.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "geometry DEF " + geonode.getName() + " " + geonode.getType() + " {");
				else
					printStream.println(indentString + "\t" + "geometry " + geonode.getType() + " {");
				geonode.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "geometry USE " + geonode.getName());
		}
	}
}