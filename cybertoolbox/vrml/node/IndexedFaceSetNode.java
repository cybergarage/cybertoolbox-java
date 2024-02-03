/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: IndexdFaceSet.java
*
******************************************************************/

package vrml.node;

import java.util.*;
import java.io.PrintWriter;

import vrml.*;
import vrml.util.*;
import vrml.field.*;

public class IndexedFaceSetNode extends GeometryNode {
	
	//// Field ////////////////
	private String	ccwFieldName				= "ccw";
	private String	colorPerVertexFieldName		= "colorPerVertex";
	private String	normalPerVertexFieldName	= "normalPerVertex";
	private String	solidFieldName				= "solid";
	private String	convexFieldName				= "convex";
	private String	creaseAngleFieldName		= "creaseAngle";
	private String	coordIndexFieldName			= "coordIndex";
	private String	texCoordIndexFieldName		= "texCoordIndex";
	private String	colorIndexFieldName			= "colorIndex";
	private String	normalIndexFieldName		= "normalIndex";

	//// ExposedField ////////////////
	private String	colorExposedFieldName		= "color";
	private String	coordExposedFieldName		= "coord";
	private String	normalExposedFieldName		= "normal";
	private String	texCoordExposedFieldName	= "texCoord";

	//// EventIn ////////////////
	private String	coordIndexEventInName		= "coordIndex";
	private String	texCoordIndexEventInName	= "texCoordIndex";
	private String	colorIndexEventInName		= "colorIndex";
	private String	normalIndexEventInName		= "normalIndex";

	public IndexedFaceSetNode() {
		setHeaderFlag(false);
		setType(indexedFaceSetTypeName);

		///////////////////////////
		// Field 
		///////////////////////////

		// ccw  field
		SFBool ccw = new SFBool(true);
		ccw.setName(ccwFieldName);
		addField(ccw);

		// colorPerVertex  field
		SFBool colorPerVertex = new SFBool(true);
		colorPerVertex.setName(colorPerVertexFieldName);
		addField(colorPerVertex);

		// normalPerVertex  field
		SFBool normalPerVertex = new SFBool(true);
		normalPerVertex.setName(normalPerVertexFieldName);
		addField(normalPerVertex);

		// solid  field
		SFBool solid = new SFBool(true);
		solid.setName(solidFieldName);
		addField(solid);

		// convex  field
		SFBool convex = new SFBool(true);
		convex.setName(convexFieldName);
		addField(convex);

		// creaseAngle  field
		SFFloat creaseAngle = new SFFloat(0.0f);
		creaseAngle.setName(creaseAngleFieldName);
		addField(creaseAngle);

		// coordIndex  field
		MFInt32 coordIndex = new MFInt32();
		coordIndex.setName(coordIndexFieldName);
		addField(coordIndex);

		// texCoordIndex  field
		MFInt32 texCoordIndex = new MFInt32();
		texCoordIndex.setName(texCoordIndexFieldName);
		addField(texCoordIndex);

		// colorIndex  field
		MFInt32 colorIndex = new MFInt32();
		colorIndex.setName(colorIndexFieldName);
		addField(colorIndex);

		// normalIndex  field
		MFInt32 normalIndex = new MFInt32();
		normalIndex.setName(normalIndexFieldName);
		addField(normalIndex);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// color field
		SFNode color = new SFNode();
		addExposedField(colorExposedFieldName, color);

		// coord field
		SFNode coord = new SFNode();
		addExposedField(coordExposedFieldName, coord);

		// normal field
		SFNode normal = new SFNode();
		addExposedField(normalExposedFieldName, normal);

		// texCoord field
		SFNode texCoord = new SFNode();
		addExposedField(texCoordExposedFieldName, texCoord);

		///////////////////////////
		// EventIn
		///////////////////////////

		// coordIndex  EventIn
		coordIndex = new MFInt32();
		coordIndex.setName(coordIndexEventInName);
		addEventIn(coordIndex);

		// texCoordIndex  EventIn
		texCoordIndex = new MFInt32();
		texCoordIndex.setName(texCoordIndexEventInName);
		addEventIn(texCoordIndex);

		// colorIndex  EventIn
		colorIndex = new MFInt32();
		colorIndex.setName(colorIndexEventInName);
		addEventIn(colorIndex);

		// normalIndex  EventIn
		normalIndex = new MFInt32();
		normalIndex.setName(normalIndexEventInName);
		addEventIn(normalIndex);
	}
	
	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public IndexedFaceSet next() {
		return (IndexedFaceSet)next(getType());
	}

	public IndexedFaceSet nextTraversal() {
		return (IndexedFaceSet)nextTraversalByType(getType());
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
	//	Normal
	////////////////////////////////////////////////

	public SFNode getNormalField() {
		return (SFNode)getExposedField(normalExposedFieldName);
	}
	
	public void updateNormalField() {
		getNormalField().setValue(getNormalNodes());
	}

	////////////////////////////////////////////////
	//	texCoord
	////////////////////////////////////////////////

	public SFNode getTexCoordField() {
		return (SFNode)getExposedField(texCoordExposedFieldName);
	}
	
	public void updateTexCoordField() {
		getTexCoordField().setValue(getTextureCoordinateNodes());
	}

	////////////////////////////////////////////////
	//	CCW
	////////////////////////////////////////////////
	
	public void setCCW(boolean value) {
		SFBool ccw = (SFBool)getField(ccwFieldName);
		ccw.setValue(value);
	}

	public boolean getCCW() {
		SFBool ccw = (SFBool)getField(ccwFieldName);
		return ccw.getValue();
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
	//	NormalPerVertex
	////////////////////////////////////////////////
	
	public void setNormalPerVertex(boolean value) {
		SFBool normalPerVertex = (SFBool)getField(normalPerVertexFieldName);
		normalPerVertex.setValue(value);
	}

	public boolean getNormalPerVertex() {
		SFBool normalPerVertex = (SFBool)getField(normalPerVertexFieldName);
		return normalPerVertex.getValue();
	}

	public boolean isNormalPerVertex() {
		return getNormalPerVertex();
	}
	
	////////////////////////////////////////////////
	//	Solid
	////////////////////////////////////////////////
	
	public void setSolid(boolean value) {
		SFBool solid = (SFBool)getField(solidFieldName);
		solid.setValue(value);
	}

	public boolean getSolid() {
		SFBool solid = (SFBool)getField(solidFieldName);
		return solid.getValue();
	}

	////////////////////////////////////////////////
	//	Convex
	////////////////////////////////////////////////
	
	public void setConvex(boolean value) {
		SFBool convex = (SFBool)getField(convexFieldName);
		convex.setValue(value);
	}

	public boolean getConvex() {
		SFBool convex = (SFBool)getField(convexFieldName);
		return convex.getValue();
	}

	////////////////////////////////////////////////
	//	CreaseAngle
	////////////////////////////////////////////////
	
	public void setCreaseAngle(float value) {
		SFFloat creaseAngle = (SFFloat)getField(creaseAngleFieldName);
		creaseAngle.setValue(value);
	}

	public float getCreaseAngle() {
		SFFloat creaseAngle = (SFFloat)getField(creaseAngleFieldName);
		return creaseAngle.getValue();
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
	
	public int getNTriangleCoordIndices() {
		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		return coordIndex.getNTriangleIndices();
	}
	
	////////////////////////////////////////////////
	// TexCoordIndex
	////////////////////////////////////////////////

	public void addTexCoordIndex(int value) {
		MFInt32 coordIndex = (MFInt32)getField(texCoordIndexFieldName);
		coordIndex.addValue(value);
	}
	public int getNTexCoordIndices() {
		MFInt32 coordIndex = (MFInt32)getField(texCoordIndexFieldName);
		return coordIndex.getSize();
	}
	public void setTexCoordIndex(int index, int value) {
		MFInt32 coordIndex = (MFInt32)getField(texCoordIndexFieldName);
		coordIndex.set1Value(index, value);
	}
	public int getTexCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getField(texCoordIndexFieldName);
		return coordIndex.get1Value(index);
	}
	public void removeTexCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getField(texCoordIndexFieldName);
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
	// NormalIndex
	////////////////////////////////////////////////

	public void addNormalIndex(int value) {
		MFInt32 normalIndex = (MFInt32)getField(normalIndexFieldName);
		normalIndex.addValue(value);
	}
	public int getNNormalIndices() {
		MFInt32 normalIndex = (MFInt32)getField(normalIndexFieldName);
		return normalIndex.getSize();
	}
	public void setNormalIndex(int index, int value) {
		MFInt32 normalIndex = (MFInt32)getField(normalIndexFieldName);
		normalIndex.set1Value(index, value);
	}
	public int getNormalIndex(int index) {
		MFInt32 normalIndex = (MFInt32)getField(normalIndexFieldName);
		return normalIndex.get1Value(index);
	}
	public void removeNormalIndex(int index) {
		MFInt32 normalIndex = (MFInt32)getField(normalIndexFieldName);
		normalIndex.removeValue(index);
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
	// set_texCoordIndex
	////////////////////////////////////////////////

	public void addSetTexCoordIndex(int value) {
		MFInt32 coordIndex = (MFInt32)getEventIn(texCoordIndexEventInName);
		coordIndex.addValue(value);
	}
	public int getNSetTexCoordIndices() {
		MFInt32 coordIndex = (MFInt32)getEventIn(texCoordIndexEventInName);
		return coordIndex.getSize();
	}
	public void setSetTexCoordIndex(int index, int value) {
		MFInt32 coordIndex = (MFInt32)getEventIn(texCoordIndexEventInName);
		coordIndex.set1Value(index, value);
	}
	public int getSetTexCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getEventIn(texCoordIndexEventInName);
		return coordIndex.get1Value(index);
	}
	public void removeSetTexCoordIndex(int index) {
		MFInt32 coordIndex = (MFInt32)getEventIn(texCoordIndexEventInName);
		coordIndex.removeValue(index);
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
	// set_normalIndex
	////////////////////////////////////////////////

	public void addSetNormalIndex(int value) {
		MFInt32 normalIndex = (MFInt32)getEventIn(normalIndexEventInName);
		normalIndex.addValue(value);
	}
	public int getNSetNormalIndices() {
		MFInt32 normalIndex = (MFInt32)getEventIn(normalIndexEventInName);
		return normalIndex.getSize();
	}
	public void setNSetormalIndex(int index, int value) {
		MFInt32 normalIndex = (MFInt32)getEventIn(normalIndexEventInName);
		normalIndex.set1Value(index, value);
	}
	public int getNSetormalIndex(int index) {
		MFInt32 normalIndex = (MFInt32)getEventIn(normalIndexEventInName);
		return normalIndex.get1Value(index);
	}
	public void removeSetNormalIndex(int index) {
		MFInt32 normalIndex = (MFInt32)getEventIn(normalIndexEventInName);
		normalIndex.removeValue(index);
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isColorNode() || node.isCoordinateNode() || node.isNormalNode() || node.isTextureCoordinateNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();

		SceneGraph sg = getSceneGraph();
		if (sg == null)
			return;

		if (isInitialized() == false) {
			if (sg.getOption(sg.NORMAL_GENERATION))
				generateNormals();
			if (sg.getOption(sg.TEXTURE_GENERATION)) {
				Node parentNode = getParentNode();
				if (parentNode != null) {
					AppearanceNode appearance = parentNode.getAppearanceNodes();
					if (appearance != null) {
						if (appearance.getTextureNode() != null)
							generateTextureCoordinate();
					}
				}
			}
			calculateBoundingBox();
			setInitializationFlag(true);
		}

		updateColorField();
		updateCoordField();
		updateNormalField();
		updateTexCoordField();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateColorField();
		//updateCoordField();
		//updateNormalField();
		//updateTexCoordField();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool convex = (SFBool)getField(convexFieldName);
		SFBool solid = (SFBool)getField(solidFieldName);
		SFBool normalPerVertex = (SFBool)getField(normalPerVertexFieldName);
		SFBool colorPerVertex = (SFBool)getField(colorPerVertexFieldName);
		SFBool ccw = (SFBool)getField(ccwFieldName);

		printStream.println(indentString + "\t" + "ccw " + ccw);
		printStream.println(indentString + "\t" + "colorPerVertex " + colorPerVertex);
		printStream.println(indentString + "\t" + "normalPerVertex " + normalPerVertex);
		printStream.println(indentString + "\t" + "convex " + convex);
		printStream.println(indentString + "\t" + "creaseAngle " + getCreaseAngle());
		printStream.println(indentString + "\t" + "solid " + solid);

		NormalNode normal = getNormalNodes();
		if (normal != null) {
			if (normal.isInstanceNode() == false) {
				String nodeName = normal.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "normal DEF " + normal.getName() + " Normal {");
				else
					printStream.println(indentString + "\t" + "normal Normal {");
				normal.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "normal USE " + normal.getName());
		}

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

		TextureCoordinateNode texCoord = getTextureCoordinateNodes();
		if (texCoord != null) {
			if (texCoord.isInstanceNode() == false) {
				String nodeName = texCoord.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "texCoord DEF " + texCoord.getName() + " TextureCoordinate {");
				else
					printStream.println(indentString + "\t" + "texCoord TextureCoordinate {");
				texCoord.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "texCoord USE " + texCoord.getName());
		}

		MFInt32 coordIndex = (MFInt32)getField(coordIndexFieldName);
		printStream.println(indentString + "\t" + "coordIndex [");
		coordIndex.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
		
		MFInt32 texCoordIndex = (MFInt32)getField(texCoordIndexFieldName);
		printStream.println(indentString + "\t" + "texCoordIndex [");
		texCoordIndex.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
		
		MFInt32 colorIndex = (MFInt32)getField(colorIndexFieldName);
		printStream.println(indentString + "\t" + "colorIndex [");
		colorIndex.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
		
		MFInt32 normalIndex = (MFInt32)getField(normalIndexFieldName);
		printStream.println(indentString + "\t" + "normalIndex [");
		normalIndex.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}

	////////////////////////////////////////////////////////////
	//	generateNormals
	////////////////////////////////////////////////////////////

	public void getNormalFromVertices(float vpoint[][], float vector[])	{
		vector[0] = (vpoint[1][1] - vpoint[0][1])*(vpoint[2][2] - vpoint[1][2]) - (vpoint[1][2] - vpoint[0][2])*(vpoint[2][1] - vpoint[1][1]);
		vector[1] = (vpoint[1][2] - vpoint[0][2])*(vpoint[2][0] - vpoint[1][0]) - (vpoint[1][0] - vpoint[0][0])*(vpoint[2][2] - vpoint[1][2]);
		vector[2] = (vpoint[1][0] - vpoint[0][0])*(vpoint[2][1] - vpoint[1][1]) - (vpoint[1][1] - vpoint[0][1])*(vpoint[2][0] - vpoint[1][0]);
		float mag = (float)Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2]);
		vector[0] /= mag;
		vector[1] /= mag;
		vector[2] /= mag;
	}

	public boolean generateNormals() 
	{
		NormalNode normal = getNormalNodes();
		if (normal != null)
			return false;

		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return false;

		normal = new NormalNode();

		int		nPolygon = 0;
		int		nVertex = 0;
		float	vpoint[][] = new float[3][3];
		float	vector[] = new float[3];

		int		nCoordIndexes = getNCoordIndices();

		for (int nCoordIndex=0; nCoordIndex<nCoordIndexes; nCoordIndex++) {
			int coordIndex = getCoordIndex(nCoordIndex);
			if (coordIndex != -1) {
				if (nVertex < 3)
					coordinate.getPoint(coordIndex, vpoint[nVertex]);
				nVertex++;
			}
			else {
				getNormalFromVertices(vpoint, vector);
				normal.addVector(vector);
				
				nVertex = 0;
				nPolygon++;
			}
		}

		addChildNode(normal);
		setNormalPerVertex(false);

		return true;
	}

	////////////////////////////////////////////////////////////
	//	generateTextureCoordinate
	////////////////////////////////////////////////////////////

	public void setExtents(SFVec3f maxExtents, SFVec3f minExtents, float vpoint[])
	{
		if (maxExtents.getX() < vpoint[0])
			maxExtents.setX(vpoint[0]);
		if (maxExtents.getY() < vpoint[1])
			maxExtents.setY(vpoint[1]);
		if (maxExtents.getZ() < vpoint[2])
			maxExtents.setZ(vpoint[2]);
		if (minExtents.getX() > vpoint[0])
			minExtents.setX(vpoint[0]);
		if (minExtents.getY() > vpoint[1])
			minExtents.setY(vpoint[1]);
		if (minExtents.getZ() > vpoint[2])
			minExtents.setZ(vpoint[2]);
	}

	public int getNPolygons() 
	{
		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return 0;

		int nCoordIndex = 0;
		for (int n=0; n<getNCoordIndices(); n++) {
			if (getCoordIndex(n) == -1 || n == (getNCoordIndices()-1))
				nCoordIndex++;
		}
		return nCoordIndex;
	}

	public void getRotateMatrixFromNormal(float normal[], SFMatrix matrix) {
		SFMatrix	mx = new SFMatrix();
		SFMatrix	my = new SFMatrix();
		float		mxValue[][] = new float[4][4];
		float		myValue[][] = new float[4][4];

		mx.getValue(mxValue);
		my.getValue(myValue);

		float d = (float)Math.sqrt(normal[1]*normal[1] + normal[2]*normal[2]);

		if (d != 0.0f) {
			float cosa = normal[2] / d;
			float sina = normal[1] / d;
			mxValue[0][0] = 1.0f;
			mxValue[0][1] = 0.0f;
			mxValue[0][2] = 0.0f;
			mxValue[1][0] = 0.0f;
			mxValue[1][1] = cosa;
			mxValue[1][2] = sina;
			mxValue[2][0] = 0.0f;
			mxValue[2][1] = -sina;
			mxValue[2][2] = cosa;
		}
	
		float cosb = d;
		float sinb = normal[0];
	
		myValue[0][0] = cosb;
		myValue[0][1] = 0.0f;
		myValue[0][2] = sinb;
		myValue[1][0] = 0.0f;
		myValue[1][1] = 1.0f;
		myValue[1][2] = 0.0f;
		myValue[2][0] = -sinb;
		myValue[2][1] = 0.0f;
		myValue[2][2] = cosb;

		mx.setValue(mxValue);
		my.setValue(myValue);

		matrix.init();
		matrix.add(my);
		matrix.add(mx);
	}

	public boolean generateTextureCoordinate() {
		TextureCoordinateNode texCoord = getTextureCoordinateNodes();
		if (texCoord != null)
			return false;

		CoordinateNode coordinate = getCoordinateNodes();
		if (coordinate == null)
			return false;

		texCoord = new TextureCoordinateNode();

		int nPolygon = getNPolygons();

		float	normal[][] = new float[nPolygon][3];
		SFVec3f	center[] = new SFVec3f[nPolygon];
		SFVec3f	maxExtents[] = new SFVec3f[nPolygon];
		SFVec3f	minExtents[] = new SFVec3f[nPolygon];
		for (int n=0; n<nPolygon; n++) {
			center[n] = new SFVec3f();
			maxExtents[n] = new SFVec3f();
			minExtents[n] = new SFVec3f();
		}

		boolean	bPolygonBegin;
		int		polyn;

		float	vpoint[][] = new float[3][3];
		float	coord[] = new float[3];

		int		vertexn = 0;

		bPolygonBegin = true;
		polyn = 0;

		int nCoordIndexes = getNCoordIndices();

		for (int n=0; n<nCoordIndexes; n++) {
			int coordIndex = getCoordIndex(n);
			if (coordIndex != -1) {

				if (vertexn < 3)
					coordinate.getPoint(coordIndex, vpoint[vertexn]);

				float point[] = new float[3];
				coordinate.getPoint(coordIndex, point);
				if (bPolygonBegin) {
					maxExtents[polyn].setValue(point);
					minExtents[polyn].setValue(point);
					center[polyn].setValue(point);
					bPolygonBegin = false;
				}
				else {
					setExtents(maxExtents[polyn], minExtents[polyn], point);
					center[polyn].add(point);
				}

				vertexn++;
			}
			else {
				getNormalFromVertices(vpoint, normal[polyn]);
				center[polyn].scale(1.0f / (float)vertexn);
				maxExtents[polyn].sub(center[polyn]);
				minExtents[polyn].sub(center[polyn]);
				vertexn = 0;
				bPolygonBegin = true;
				polyn++;
			}
		}

		float		minx, miny, maxx, maxy, xlength, ylength;
		SFMatrix	matrix = new SFMatrix();

		bPolygonBegin = true;
		polyn = 0;
		minx = miny = maxx = maxy = xlength = ylength = 0.0f;

		for (int n=0; n<nCoordIndexes; n++) {
			int coordIndex = getCoordIndex(n);
			if (coordIndex != -1) {

				if (bPolygonBegin) {
					getRotateMatrixFromNormal(normal[polyn], matrix);
					matrix.multi(minExtents[polyn]);
					matrix.multi(maxExtents[polyn]);
					minx = minExtents[polyn].getX();
					miny = minExtents[polyn].getY();
					maxx = maxExtents[polyn].getX();
					maxy = maxExtents[polyn].getY();
					xlength = (float)Math.abs(maxExtents[polyn].getX() - minExtents[polyn].getX());
					ylength = (float)Math.abs(maxExtents[polyn].getY() - minExtents[polyn].getY());

					if (xlength == 0.0f || ylength == 0.0f)
						return false;

					bPolygonBegin = false;
				}

				coordinate.getPoint(coordIndex, coord);
	
				coord[0] -= center[polyn].getX();
				coord[1] -= center[polyn].getY();
				coord[2] -= center[polyn].getZ();

				matrix.multi(coord);

				coord[0] = (float)Math.abs(coord[0] - minx) / xlength;
				coord[1] = (float)Math.abs(coord[1] - miny) / ylength;

				texCoord.addPoint(coord);
			}
			else {
				bPolygonBegin = true;
				polyn++;
			}
		}

		addChildNode(texCoord);

		return true;
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