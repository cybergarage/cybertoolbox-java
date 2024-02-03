/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PointSet.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.util.*;
import vrml.field.*;

public class PointSetNode extends GeometryNode {

	//// ExposedField ////////////////
	private String	colorExposedFieldName		= "color";
	private String	coordExposedFieldName		= "coord";
	
	public PointSetNode() {
		setHeaderFlag(false);
		setType(pointSetTypeName);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// color field
		SFNode color = new SFNode();
		addExposedField(colorExposedFieldName, color);

		// coord field
		SFNode coord = new SFNode();
		addExposedField(coordExposedFieldName, coord);
	}

	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public SFNode getColorField() {
		return (SFNode)getExposedField(colorExposedFieldName);
	}
	
	public void updateColorField() {
		getColorField().setValue(getColorNodes());
	}

	////////////////////////////////////////////////
	//	Coord
	////////////////////////////////////////////////

	public SFNode getCoordField() {
		return (SFNode)getExposedField(coordExposedFieldName);
	}
	
	public void updateCoordField() {
		getCoordField().setValue(getCoordinateNodes());
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public PointSet next() {
		return (PointSet)next(getType());
	}

	public PointSet nextTraversal() {
		return (PointSet)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isCoordinateNode() || node.isColorNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();

		if (isInitialized() == false) {
			calculateBoundingBox();
			setInitializationFlag(true);
		}

		updateColorField();
		updateCoordField();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateColorField();
		//updateCoordField();
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void calculateBoundingBox() {
		if (isInitialized() == true)
			return;
			
		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null) {
			setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
			setBoundingBoxSize(-1.0f, -1.0f, -1.0f);
			return;
		}
		
		BoundingBox bbox = new BoundingBox(); 
		
		float point[] = new float[3];
		int nCoordinatePoint = coordinate.getNPoints();

		for (int n=0; n<nCoordinatePoint; n++) {
			coordinate.getPoint(n, point);
			bbox.addPoint(point);
		}
		
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		ColorNode color = getColorNodes();
		if (color != null) {
			if (color.isInstanceNode() == false) {
				String nodeName = color.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "color DEF " + color.getName() + " Color {");
				else
					printStream.println(indentString + "\t" + "color Color {");
				color.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "color USE " + color.getName());
		}

		CoordinateNode coord = getCoordinateNodes();
		if (coord != null) {
			if (coord.isInstanceNode() == false) {
				String nodeName = coord.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "coord DEF " + coord.getName() + " Coordinate {");
				else
					printStream.println(indentString + "\t" + "coord Coordinate {");
				coord.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "coord USE " + coord.getName());
		}
	}
}
