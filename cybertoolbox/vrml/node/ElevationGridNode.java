/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: ElevataionGrid.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;

import vrml.*;
import vrml.field.*;
import vrml.util.Debug;

public class ElevationGridNode extends GeometryNode {

	private String	set_heightEventInName		= "height";
	private String	colorFieldName				= "color";
	private String	normalFieldName				= "normal";
	private String	texCoordFieldName			= "texCoord";
	private String	xDimensionFieldName			= "xDimension";
	private String	zDimensionFieldName			= "zDimension";
	private String	xSpacingFieldName			= "xSpacing";
	private String	zSpacingFieldName			= "zSpacing";
	private String	colorPerVertexFieldName		= "colorPerVertex";
	private String	normalPerVertexFieldName	= "normalPerVertex";
	private String	heightFieldName				= "height";
	private String	ccwFieldName				= "ccw";
	private String	solidFieldName				= "solid";
	private String	creaseAngleFieldName		= "creaseAngle";
	
	public ElevationGridNode() {

		setHeaderFlag(false);
		setType(elevationGridTypeName);

		// height exposed field
		MFFloat set_height = new MFFloat();
		addEventIn(set_heightEventInName, set_height);

		// color field
		SFNode color = new SFNode();
		addExposedField(colorFieldName, color);

		// normal field
		SFNode normal = new SFNode();
		addExposedField(normalFieldName, normal);

		// texCoord field
		SFNode texCoord = new SFNode();
		addExposedField(texCoordFieldName, texCoord);

		// xSpacing field
		SFFloat xSpacing = new SFFloat(0.0f);
		addField(xSpacingFieldName, xSpacing);

		// zSpacing field
		SFFloat zSpacing = new SFFloat(0.0f);
		addField(zSpacingFieldName, zSpacing);

		// xDimension field
		SFInt32 xDimension = new SFInt32(0);
		addField(xDimensionFieldName, xDimension);

		// zDimension field
		SFInt32 zDimension = new SFInt32(0);
		addField(zDimensionFieldName, zDimension);

		// colorPerVertex exposed field
		SFBool colorPerVertex = new SFBool(true);
		colorPerVertex.setName(colorPerVertexFieldName);
		addField(colorPerVertex);

		// normalPerVertex exposed field
		SFBool normalPerVertex = new SFBool(true);
		normalPerVertex.setName(normalPerVertexFieldName);
		addField(normalPerVertex);

		// ccw exposed field
		SFBool ccw = new SFBool(true);
		ccw.setName(ccwFieldName);
		addField(ccw);

		// solid exposed field
		SFBool solid = new SFBool(true);
		solid.setName(solidFieldName);
		addField(solid);

		// creaseAngle exposed field
		SFFloat creaseAngle = new SFFloat(0.0f);
		creaseAngle.setName(creaseAngleFieldName);
		addField(creaseAngle);

		// height exposed field
		MFFloat height = new MFFloat();
		addField(heightFieldName, height);

		// height eventIn
		MFFloat setHeight = new MFFloat();
		addEventIn(heightFieldName, setHeight);
	}

	////////////////////////////////////////////////
	// set_height
	////////////////////////////////////////////////

	public void addSetHeight(float value) {
		MFFloat set_height = (MFFloat)getEventIn(set_heightEventInName);
		set_height.addValue(value);
	}
	public int getNSetHeights() {
		MFFloat set_height = (MFFloat)getEventIn(set_heightEventInName);
		return set_height.getSize();
	}
	public void setSetHeight(int index, float value) {
		MFFloat set_height = (MFFloat)getEventIn(set_heightEventInName);
		set_height.set1Value(index, value);
	}
	public float getSetHeight(int index) {
		MFFloat set_height = (MFFloat)getEventIn(set_heightEventInName);
		return set_height.get1Value(index);
	}
	public void removeSetHeight(int index) {
		MFFloat set_height = (MFFloat)getEventIn(set_heightEventInName);
		set_height.removeValue(index);
	}

	////////////////////////////////////////////////
	//	Color
	////////////////////////////////////////////////

	public SFNode getColorField() {
		return (SFNode)getExposedField(colorFieldName);
	}
	
	public void updateColorField() {
		getColorField().setValue(getColorNodes());
	}

	////////////////////////////////////////////////
	//	Normal
	////////////////////////////////////////////////

	public SFNode getNormalField() {
		return (SFNode)getExposedField(normalFieldName);
	}
	
	public void updateNormalField() {
		getNormalField().setValue(getNormalNodes());
	}

	////////////////////////////////////////////////
	//	texCoord
	////////////////////////////////////////////////

	public SFNode getTexCoordField() {
		return (SFNode)getExposedField(texCoordFieldName);
	}
	
	public void updateTexCoordField() {
		getTexCoordField().setValue(getTextureCoordinateNodes());
	}

	////////////////////////////////////////////////
	//	xSpacing
	////////////////////////////////////////////////

	public void setXSpacing(float value) {
		SFFloat space = (SFFloat)getField(xSpacingFieldName);
		space.setValue(value);
	}
	public float getXSpacing() {
		SFFloat space = (SFFloat)getField(xSpacingFieldName);
		return space.getValue();
	}

	////////////////////////////////////////////////
	//	zSpacing
	////////////////////////////////////////////////

	public void setZSpacing(float value) {
		SFFloat space = (SFFloat)getField(zSpacingFieldName);
		space.setValue(value);
	}
	public float getZSpacing() {
		SFFloat space = (SFFloat)getField(zSpacingFieldName);
		return space.getValue();
	}

	////////////////////////////////////////////////
	//	xDimension
	////////////////////////////////////////////////

	public void setXDimension(int value) {
		SFInt32 dimension = (SFInt32)getField(xDimensionFieldName);
		dimension.setValue(value);
	}
	public int getXDimension() {
		SFInt32 dimension = (SFInt32)getField(xDimensionFieldName);
		return dimension.getValue();
	}

	////////////////////////////////////////////////
	//	zDimension
	////////////////////////////////////////////////

	public void setZDimension(int value) {
		SFInt32 dimension = (SFInt32)getField(zDimensionFieldName);
		dimension.setValue(value);
	}
	public int getZDimension() {
		SFInt32 dimension = (SFInt32)getField(zDimensionFieldName);
		return dimension.getValue();
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
	// height
	////////////////////////////////////////////////

	public void addHeight(float value) {
		MFFloat height = (MFFloat)getField(heightFieldName);
		height.addValue(value);
	}
	public int getNHeights() {
		MFFloat height = (MFFloat)getField(heightFieldName);
		return height.getSize();
	}
	public void setHeight(int index, float value) {
		MFFloat height = (MFFloat)getField(heightFieldName);
		height.set1Value(index, value);
	}
	public float getHeight(int index) {
		MFFloat height = (MFFloat)getField(heightFieldName);
		return height.get1Value(index);
	}
	public void removeHeight(int index) {
		MFFloat height = (MFFloat)getField(heightFieldName);
		height.removeValue(index);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public ElevationGrid next() {
		return (ElevationGrid)next(getType());
	}

	public ElevationGrid nextTraversal() {
		return (ElevationGrid)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isColorNode() || node.isNormalNode() || node.isTextureCoordinateNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
		updateColorField();
		updateNormalField();
		updateTexCoordField();
		calculateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
		//updateColorField();
		//updateNormalField();
		//updateTexCoordField();
		//calculateBoundingBox();
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool ccw = (SFBool)getField(ccwFieldName);
		SFBool solid = (SFBool)getField(solidFieldName);
		SFBool colorPerVertex = (SFBool)getField(colorPerVertexFieldName);
		SFBool normalPerVertex = (SFBool)getField(normalPerVertexFieldName);

		printStream.println(indentString + "\t" + "xDimension " + getXDimension());
		printStream.println(indentString + "\t" + "xSpacing " + getXSpacing());
		printStream.println(indentString + "\t" + "zDimension " + getZDimension());
		printStream.println(indentString + "\t" + "zSpacing " + getZSpacing());

		MFFloat height = (MFFloat)getField(heightFieldName);
		printStream.println(indentString + "\t" + "height [");
		height.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		printStream.println(indentString + "\t" + "colorPerVertex " + colorPerVertex);
		printStream.println(indentString + "\t" + "normalPerVertex " + normalPerVertex);
		printStream.println(indentString + "\t" + "ccw " + ccw);
		printStream.println(indentString + "\t" + "solid " + solid);
		printStream.println(indentString + "\t" + "creaseAngle " + getCreaseAngle());
		
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

	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void calculateBoundingBox() {
		float xSize = (getXDimension()-1) * getXSpacing();
		float zSize = (getZDimension()-1) * getZSpacing();
		float minHeight = 0.0f;
		float maxHeight = 0.0f;
		int nHeights = getNHeights();
		if (0 < nHeights) {
			minHeight = maxHeight = getHeight(0);
			for (int n=1; n<nHeights; n++) {
				float height = getHeight(n);
				if (height < minHeight)
					minHeight = height;
				if (maxHeight < height)
					maxHeight = height;
			}
		}
		setBoundingBoxCenter(xSize/2.0f, (maxHeight+minHeight)/2.0f, zSize/2.0f);
		setBoundingBoxSize(xSize/2.0f, (float)Math.abs(maxHeight+minHeight)/2.0f, zSize/2.0f);
		
		float bboxCenter[] = getBoundingBoxCenter();
		float bboxSize[] = getBoundingBoxSize();
		Debug.message("ElevatoinGridNode::calculateBoundingBox");
		Debug.message("\tbboxCenter = " + bboxCenter[0] + ", " + bboxCenter[1] + ", " + bboxCenter[2]);
		Debug.message("\tbboxSize   = " + bboxSize[0] + ", " + bboxSize[1] + ", " + bboxSize[2]);
	}

	////////////////////////////////////////////////
	//	For Java3D object
	////////////////////////////////////////////////
	
	public int getVertexCount() {
		return getXDimension() * getZDimension();
	}
	
	public int getNTriangleCoordIndices() {
		return (3 * 2 * (getXDimension()-1) * (getZDimension()-1));
	}
}
