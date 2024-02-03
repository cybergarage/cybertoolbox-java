/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: IndexedLineSet.java
*
******************************************************************/

package vrml.node;

import java.util.*;
import java.io.PrintWriter;
import vrml.*;
import vrml.util.*;
import vrml.field.*;

public class IndexedLineSetNode extends GeometryNode {
	
	//// Field ////////////////
	private String	colorPerVertexFieldName		= "colorPerVertex";
	private String	coordIndexFieldName			= "coordIndex";
	private String	colorIndexFieldName			= "colorIndex";

	//// ExposedField ////////////////
	private String	colorExposedFieldName		= "color";
	private String	coordExposedFieldName		= "coord";

	//// EventIn ////////////////
	private String	coordIndexEventInName		= "coordIndex";
	private String	colorIndexEventInName		= "colorIndex";

	public IndexedLineSetNode() {
		setHeaderFlag(false);
		setType(indexedLineSetTypeName);

		///////////////////////////
		// Field 
		///////////////////////////

		// colorPerVertex  field
		SFBool colorPerVertex = new SFBool(true);
		colorPerVertex.setName(colorPerVertexFieldName);
		addField(colorPerVertex);

		// coordIndex  field
		MFInt32 coordIndex = new MFInt32();
		coordIndex.setName(coordIndexFieldName);
		addField(coordIndex);

		// colorIndex  field
		MFInt32 colorIndex = new MFInt32();
		colorIndex.setName(colorIndexFieldName);
		addField(colorIndex);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// color field
		SFNode color = new SFNode();
		addExposedField(colorExposedFieldName, color);

		// coord field
		SFNode coord = new SFNode();
		addExposedField(coordExposedFieldName, coord);


		///////////////////////////
		// EventIn
		///////////////////////////

		// coordIndex  EventIn
		coordIndex = new MFInt32();
		coordIndex.setName(coordIndexEventInName);
		addEventIn(coordIndex);

		// colorIndex  EventIn
		colorIndex = new MFInt32();
		colorIndex.setName(colorIndexEventInName);
		addEventIn(colorIndex);
	}
	
	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public IndexedLineSet next() {
		return (IndexedLineSet)next(getType());
	}

	public IndexedLineSet nextTraversal() {
		return (IndexedLineSet)nextTraversalByType(getType());
	}
*/

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
	//	ColorPerVertex
	////////////////////////////////////////////////
	
	public void setColorPerVertex(boolean value) {
		SFBool colorPerVertex = (SFBool)getField(colorPerVertexFieldName);
		colorPerVertex.setValue(value);
	}

	public boolean getColorPerVertex() {
		SFBool colorPerVertex = (SFBool)getField(colorPerVertexFieldName);
		return colorPerVertex.getValue();
	}

	public boolean isColorPerVertex() {
		return getColorPerVertex();
	}
	
	////////////////////////////////////////////////
	// CoordIndex
	////////////////////////////////////////////////

	public void addCoordIndex(int value) {
		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		coordIndex.addValue(value);
	}
	public int getNCoordIndices() {
		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		return coordIndex.getSize();
	}
	public void setCoordIndex(int index, int value) {
		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		coordIndex.set1Value(index, value);
	}
	public int getCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		return coordIndex.get1Value(index);
	}
	public void removeCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		coordIndex.removeValue(index);
	}
	
	////////////////////////////////////////////////
	// ColorIndex
	////////////////////////////////////////////////

	public void addColorIndex(int value) {
		MFInt32 colorIndex = (MFInt32)getField(colorIndexFieldName);
		colorIndex.addValue(value);
	}
	public int getNColorIndices() {
		MFInt32 colorIndex = (MFInt32)getField(colorIndexFieldName);
		return colorIndex.getSize();
	}
	public void setColorIndex(int index, int value) {
		MFInt32 colorIndex = (MFInt32)getField(colorIndexFieldName);
		colorIndex.set1Value(index, value);
	}
	public int getColorIndex(int index) {
		MFInt32 colorIndex = (MFInt32)getField(colorIndexFieldName);
		return colorIndex.get1Value(index);
	}
	public void removeColorIndex(int index) {
		MFInt32 colorIndex = (MFInt32)getField(colorIndexFieldName);
		colorIndex.removeValue(index);
	}

	////////////////////////////////////////////////
	// set_coordIndex
	////////////////////////////////////////////////

	public void addSetCoordIndex(int value) {
		MFInt32 coordIndex = (MFInt32)getEventIn(coordIndexEventInName);
		coordIndex.addValue(value);
	}
	public int getNSetCoordIndices() {
		MFInt32 coordIndex = (MFInt32)getEventIn(coordIndexEventInName);
		return coordIndex.getSize();
	}
	public void setSetCoordIndex(int index, int value) {
		MFInt32 coordIndex = (MFInt32)getEventIn(coordIndexEventInName);
		coordIndex.set1Value(index, value);
	}
	public int getSetCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getEventIn(coordIndexEventInName);
		return coordIndex.get1Value(index);
	}
	public void removeSetCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getEventIn(coordIndexEventInName);
		coordIndex.removeValue(index);
	}
	
	public int getNSetTriangleCoordIndices() {
		MFInt32 coordIndex = (MFInt32)getEventIn(coordIndexEventInName);
		return coordIndex.getNTriangleIndices();
	}
	
	////////////////////////////////////////////////
	// set_colorIndex
	////////////////////////////////////////////////

	public void addSetColorIndex(int value) {
		MFInt32 colorIndex = (MFInt32)getEventIn(colorIndexEventInName);
		colorIndex.addValue(value);
	}
	public int getNSetColorIndices() {
		MFInt32 colorIndex = (MFInt32)getEventIn(colorIndexEventInName);
		return colorIndex.getSize();
	}
	public void setSetColorIndex(int index, int value) {
		MFInt32 colorIndex = (MFInt32)getEventIn(colorIndexEventInName);
		colorIndex.set1Value(index, value);
	}
	public int getSetColorIndex(int index) {
		MFInt32 colorIndex = (MFInt32)getEventIn(colorIndexEventInName);
		return colorIndex.get1Value(index);
	}
	public void removeSetColorIndex(int index) {
		MFInt32 colorIndex = (MFInt32)getEventIn(colorIndexEventInName);
		colorIndex.removeValue(index);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isColorNode() || node.isCoordinateNode())
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
	//	Line
	////////////////////////////////////////////////

	public int getNLines() 
	{
		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return 0;

		int nTotalCoordIndex = 0;
		int nCoordIndex = 0;
		for (int n=0; n<getNCoordIndices(); n++) {
			if (getCoordIndex(n) != -1) 
				nCoordIndex++;
			if (getCoordIndex(n) == -1 || n == (getNCoordIndices()-1)) {
				if (2 <= nCoordIndex)
					nTotalCoordIndex += nCoordIndex - 1;
				nCoordIndex = 0;
			}
		}
			
		return nTotalCoordIndex;
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool colorPerVertex = (SFBool)getField(colorPerVertexFieldName);

		printStream.println(indentString + "\t" + "colorPerVertex " + colorPerVertex);

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

		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		printStream.println(indentString + "\t" + "coordIndex [");
		coordIndex.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
		
		MFInt32 colorIndex = (MFInt32)getField(colorIndexFieldName);
		printStream.println(indentString + "\t" + "colorIndex [");
		colorIndex.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
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
}