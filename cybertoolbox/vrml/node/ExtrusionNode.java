/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: Extrusion.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.util.*;
import vrml.field.*;

public class ExtrusionNode extends GeometryNode {

	//// Field ////////////////
	private String	beginCapFieldName			= "beginCap";
	private String	endCapFieldName			= "endCap";
	private String	ccwFieldName				= "ccw";
	private String	convexFieldName			= "convex";
	private String	creaseAngleFieldName		= "creaseAngle";
	private String	solidFieldName				= "solid";
	private String	orientationFieldName		= "orientation";
	private String	scaleFieldName				= "scale";
	private String	crossSectionFieldName	= "crossSection";
	private String	spineFieldName				= "spine";

	//// EventIn ////////////////
	private String	crossSectionEventInName	= "crossSection";
	private String	orientationEventInName	= "orientation";
	private String	scaleEventInName			= "scale";
	private String	spineEventInName			= "spine";
	
	public ExtrusionNode() {

		setHeaderFlag(false);
		setType(extrusionTypeName);

		///////////////////////////
		// Field 
		///////////////////////////
		
		// beginCap field
		SFBool beginCap = new SFBool(true);
		addField(beginCapFieldName, beginCap);

		// endCap field
		SFBool endCap = new SFBool(true);
		addField(endCapFieldName, endCap);

		// ccw field
		SFBool ccw = new SFBool(true);
		ccw.setName(ccwFieldName);
		addField(ccw);

		// convex field
		SFBool convex = new SFBool(true);
		convex.setName(convexFieldName);
		addField(convex);

		// creaseAngle field
		SFFloat creaseAngle = new SFFloat(0.0f);
		creaseAngle.setName(creaseAngleFieldName);
		addField(creaseAngle);

		// solid field
		SFBool solid = new SFBool(true);
		solid.setName(solidFieldName);
		addField(solid);

		// orientation field
		MFRotation orientation = new MFRotation();
		orientation.setName(orientationFieldName);
		addField(orientation);

		// scale field
		MFVec2f scale = new MFVec2f();
		scale.setName(scaleFieldName);
		addField(scale);

		// crossSection field
		MFVec2f crossSection = new MFVec2f();
		addField(crossSectionFieldName, crossSection);

		// spine field
		MFVec3f spine = new MFVec3f();
		addField(spineFieldName, spine);

		///////////////////////////
		// EventIn
		///////////////////////////

		// orientation EventIn
		orientation = new MFRotation();
		orientation.setName(orientationEventInName);
		addEventIn(orientation);

		// scale EventIn
		scale = new MFVec2f();
		scale.setName(scaleEventInName);
		addEventIn(scale);

		// crossSection EventIn
		crossSection = new MFVec2f();
		addEventIn(crossSectionEventInName, crossSection);

		// spine EventIn
		spine = new MFVec3f();
		addEventIn(spineEventInName, spine);
	}

	////////////////////////////////////////////////
	//	BeginCap
	////////////////////////////////////////////////
	
	public void setBeginCap(boolean value) {
		SFBool beginCap = (SFBool)getField(beginCapFieldName);
		beginCap.setValue(value);
	}

	public boolean getBeginCap() {
		SFBool beginCap = (SFBool)getField(beginCapFieldName);
		return beginCap.getValue();
	}

	////////////////////////////////////////////////
	//	EndCap
	////////////////////////////////////////////////
	
	public void setEndCap(boolean value) {
		SFBool endCap = (SFBool)getField(endCapFieldName);
		endCap.setValue(value);
	}

	public boolean getEndCap() {
		SFBool endCap = (SFBool)getField(endCapFieldName);
		return endCap.getValue();
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
	// orientation
	////////////////////////////////////////////////

	public void addOrientation(float value[]) {
		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		orientation.addValue(value);
	}
	public void addOrientation(float x, float y, float z, float angle) {
		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		orientation.addValue(x, y, z, angle);
	}
	public int getNOrientations() {
		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		return orientation.getSize();
	}
	public void setOrientation(int index, float value[]) {
		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		orientation.set1Value(index, value);
	}
	public void setOrientation(int index, float x, float y, float z, float angle) {
		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		orientation.set1Value(index, x, y, z, angle);
	}
	public void getOrientation(int index, float value[]) {
		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		orientation.get1Value(index, value);
	}
	public float[] getOrientation(int index) {
		float value[] = new float[4];
		getOrientation(index, value);
		return value;
	}
	public void removeOrientation(int index) {
		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		orientation.removeValue(index);
	}

	////////////////////////////////////////////////
	// scale
	////////////////////////////////////////////////

	public void addScale(float value[]) {
		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		scale.addValue(value);
	}
	public void addScale(float x, float y) {
		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		scale.addValue(x, y);
	}
	public int getNScales() {
		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		return scale.getSize();
	}
	public void setScale(int index, float value[]) {
		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		scale.set1Value(index, value);
	}
	public void setScale(int index, float x, float y) {
		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		scale.set1Value(index, x, y);
	}
	public void getScale(int index, float value[]) {
		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		scale.get1Value(index, value);
	}
	public float[] getScale(int index) {
		float value[] = new float[2];
		getScale(index, value);
		return value;
	}
	public void removeScale(int index) {
		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		scale.removeValue(index);
	}

	////////////////////////////////////////////////
	// crossSection
	////////////////////////////////////////////////

	public void addCrossSection(float value[]) {
		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		crossSection.addValue(value);
	}
	public void addCrossSection(float x, float y) {
		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		crossSection.addValue(x, y);
	}
	public int getNCrossSections() {
		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		return crossSection.getSize();
	}
	public void setCrossSection(int index, float value[]) {
		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		crossSection.set1Value(index, value);
	}
	public void setCrossSection(int index, float x, float y) {
		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		crossSection.set1Value(index, x, y);
	}
	public void getCrossSection(int index, float value[]) {
		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		crossSection.get1Value(index, value);
	}
	public float[] getCrossSection(int index) {
		float value[] = new float[2];
		getCrossSection(index, value);
		return value;
	}
	public void removeCrossSection(int index) {
		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		crossSection.removeValue(index);
	}

	////////////////////////////////////////////////
	// spine
	////////////////////////////////////////////////

	public void addSpine(float value[]) {
		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		spine.addValue(value);
	}
	public void addSpine(float x, float y, float z) {
		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		spine.addValue(x, y, z);
	}
	public int getNSpines() {
		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		return spine.getSize();
	}
	public void setSpine(int index, float value[]) {
		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		spine.set1Value(index, value);
	}
	public void setSpine(int index, float x, float y, float z) {
		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		spine.set1Value(index, x, y, z);
	}
	public void getSpine(int index, float value[]) {
		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		spine.get1Value(index, value);
	}
	public float[] getSpine(int index) {
		float value[] = new float[3];
		getSpine(index, value);
		return value;
	}
	public void removeSpine(int index) {
		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		spine.removeValue(index);
	}

	////////////////////////////////////////////////
	// set_orientation
	////////////////////////////////////////////////

	public void addSetSetOrientation(float value[]) {
		MFRotation orientation = (MFRotation)getEventIn(orientationEventInName);
		orientation.addValue(value);
	}
	public void addSetSetOrientation(float x, float y, float z, float angle) {
		MFRotation orientation = (MFRotation)getEventIn(orientationEventInName);
		orientation.addValue(x, y, z, angle);
	}
	public int getNSetSetOrientations() {
		MFRotation orientation = (MFRotation)getEventIn(orientationEventInName);
		return orientation.getSize();
	}
	public void setSetSetOrientation(int index, float value[]) {
		MFRotation orientation = (MFRotation)getEventIn(orientationEventInName);
		orientation.set1Value(index, value);
	}
	public void setSetSetOrientation(int index, float x, float y, float z, float angle) {
		MFRotation orientation = (MFRotation)getEventIn(orientationEventInName);
		orientation.set1Value(index, x, y, z, angle);
	}
	public void getSetSetOrientation(int index, float value[]) {
		MFRotation orientation = (MFRotation)getEventIn(orientationEventInName);
		orientation.get1Value(index, value);
	}
	public float[] getSetSetOrientation(int index) {
		float value[] = new float[4];
		getSetSetOrientation(index, value);
		return value;
	}
	public void removeSetSetOrientation(int index) {
		MFRotation orientation = (MFRotation)getEventIn(orientationEventInName);
		orientation.removeValue(index);
	}

	////////////////////////////////////////////////
	// set_scale
	////////////////////////////////////////////////

	public void addSetScale(float value[]) {
		MFVec2f scale = (MFVec2f)getEventIn(scaleEventInName);
		scale.addValue(value);
	}
	public void addSetScale(float x, float y) {
		MFVec2f scale = (MFVec2f)getEventIn(scaleEventInName);
		scale.addValue(x, y);
	}
	public int getNSetScales() {
		MFVec2f scale = (MFVec2f)getEventIn(scaleEventInName);
		return scale.getSize();
	}
	public void setSetScale(int index, float value[]) {
		MFVec2f scale = (MFVec2f)getEventIn(scaleEventInName);
		scale.set1Value(index, value);
	}
	public void setSetScale(int index, float x, float y) {
		MFVec2f scale = (MFVec2f)getEventIn(scaleEventInName);
		scale.set1Value(index, x, y);
	}
	public void getSetScale(int index, float value[]) {
		MFVec2f scale = (MFVec2f)getEventIn(scaleEventInName);
		scale.get1Value(index, value);
	}
	public float[] getSetScale(int index) {
		float value[] = new float[2];
		getSetScale(index, value);
		return value;
	}
	public void removeSetScale(int index) {
		MFVec2f scale = (MFVec2f)getEventIn(scaleEventInName);
		scale.removeValue(index);
	}

	////////////////////////////////////////////////
	// set_crossSection
	////////////////////////////////////////////////

	public void addSetCrossSection(float value[]) {
		MFVec2f crossSection = (MFVec2f)getEventIn(crossSectionEventInName);
		crossSection.addValue(value);
	}
	public void addSetCrossSection(float x, float y) {
		MFVec2f crossSection = (MFVec2f)getEventIn(crossSectionEventInName);
		crossSection.addValue(x, y);
	}
	public int getNSetCrossSections() {
		MFVec2f crossSection = (MFVec2f)getEventIn(crossSectionEventInName);
		return crossSection.getSize();
	}
	public void setSetCrossSection(int index, float value[]) {
		MFVec2f crossSection = (MFVec2f)getEventIn(crossSectionEventInName);
		crossSection.set1Value(index, value);
	}
	public void setSetCrossSection(int index, float x, float y) {
		MFVec2f crossSection = (MFVec2f)getEventIn(crossSectionEventInName);
		crossSection.set1Value(index, x, y);
	}
	public void getSetCrossSection(int index, float value[]) {
		MFVec2f crossSection = (MFVec2f)getEventIn(crossSectionEventInName);
		crossSection.get1Value(index, value);
	}
	public float[] getSetCrossSection(int index) {
		float value[] = new float[2];
		getSetCrossSection(index, value);
		return value;
	}
	public void removeSetCrossSection(int index) {
		MFVec2f crossSection = (MFVec2f)getEventIn(crossSectionEventInName);
		crossSection.removeValue(index);
	}

	////////////////////////////////////////////////
	// set_spine
	////////////////////////////////////////////////

	public void addSetSpine(float value[]) {
		MFVec3f spine = (MFVec3f)getEventIn(spineEventInName);
		spine.addValue(value);
	}
	public void addSetSpine(float x, float y, float z) {
		MFVec3f spine = (MFVec3f)getEventIn(spineEventInName);
		spine.addValue(x, y, z);
	}
	public int getNSetSpines() {
		MFVec3f spine = (MFVec3f)getEventIn(spineEventInName);
		return spine.getSize();
	}
	public void setSetSpine(int index, float value[]) {
		MFVec3f spine = (MFVec3f)getEventIn(spineEventInName);
		spine.set1Value(index, value);
	}
	public void setSetSpine(int index, float x, float y, float z) {
		MFVec3f spine = (MFVec3f)getEventIn(spineEventInName);
		spine.set1Value(index, x, y, z);
	}
	public void getSetSpine(int index, float value[]) {
		MFVec3f spine = (MFVec3f)getEventIn(spineEventInName);
		spine.get1Value(index, value);
	}
	public float[] getSetSpine(int index) {
		float value[] = new float[3];
		getSetSpine(index, value);
		return value;
	}
	public void removeSetSpine(int index) {
		MFVec3f spine = (MFVec3f)getEventIn(spineEventInName);
		spine.removeValue(index);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Extrusion next() {
		return (Extrusion)next(getType());
	}

	public Extrusion nextTraversal() {
		return (Extrusion)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void addDefaultParameters() {
		if (getNCrossSections() == 0) {
			addCrossSection(1.0f, 1.0f);
			addCrossSection(1.0f, -1.0f);
			addCrossSection(-1.0f, -1.0f);
			addCrossSection(-1.0f, 1.0f);
			addCrossSection(1.0f, 1.0f);
		}
		if (getNSpines() == 0) {
			addSpine(0.0f, 0.0f, 0.0f);
			addSpine(0.0f, 1.0f, 0.0f);
		}
	}
	
	public void initialize() {
		super.initialize();
		calculateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		SFBool beginCap = (SFBool)getField(beginCapFieldName);
		SFBool endCap = (SFBool)getField(endCapFieldName);
		SFBool ccw = (SFBool)getField(ccwFieldName);
		SFBool convex = (SFBool)getField(convexFieldName);
		SFBool solid = (SFBool)getField(solidFieldName);

		printStream.println(indentString + "\t" + "beginCap " + beginCap);
		printStream.println(indentString + "\t" + "endCap " + endCap);
		printStream.println(indentString + "\t" + "solid " + solid);
		printStream.println(indentString + "\t" + "ccw " + ccw);
		printStream.println(indentString + "\t" + "convex " + convex);
		printStream.println(indentString + "\t" + "creaseAngle " + getCreaseAngle());

		MFVec2f crossSection = (MFVec2f)getField(crossSectionFieldName);
		printStream.println(indentString + "\t" + "crossSection [");
		crossSection.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFRotation orientation = (MFRotation)getField(orientationFieldName);
		printStream.println(indentString + "\t" + "orientation [");
		orientation.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFVec2f scale = (MFVec2f)getField(scaleFieldName);
		printStream.println(indentString + "\t" + "scale [");
		scale.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFVec3f spine = (MFVec3f)getField(spineFieldName);
		printStream.println(indentString + "\t" + "spine [");
		spine.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void calculateBoundingBox() {
		setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
		setBoundingBoxSize(-1.0f, -1.0f, -1.0f);
	}

	////////////////////////////////////////////////
	//	For Java3D object
	////////////////////////////////////////////////
	
	public int getVertexCount() {
		return getNCrossSections() * getNSpines();
	}
	
	public int getNTriangleCoordIndices() {
		int nCoordinateIndices = (getNCrossSections()*2) * (getNSpines()-1) * 3;
		
		if (getConvex() == true) {
			if (getBeginCap() == true) 
				nCoordinateIndices += getNCrossSections() * 3;
			if (getEndCap() == true) 
				nCoordinateIndices += getNCrossSections() * 3;
		}
		
		return nCoordinateIndices;
	}
}
