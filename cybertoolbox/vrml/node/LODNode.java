/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : LOD.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class LODNode extends GroupingNode {

	private String	centerFieldName		= "center";
	private String	rangeFieldName			= "range";
	private String	levelExposedField		= "level";
	
	public LODNode() {
		super(false);
		setHeaderFlag(false);
		setType(lodTypeName);

		// center field
		SFVec3f center = new SFVec3f(0.0f, 0.0f, 0.0f);
		addField(centerFieldName, center);

		// range field
		MFFloat range = new MFFloat();
		addField(rangeFieldName, range);

		// level exposedField
		MFNode level = new MFNode();
		addExposedField(levelExposedField, level);
	}

	////////////////////////////////////////////////
	//	center
	////////////////////////////////////////////////

	public void setCenter(float value[]) {
		SFVec3f center = (SFVec3f)getField(centerFieldName);
		center.setValue(value);
	}

	public void setCenter(float x, float y, float z) {
		SFVec3f center = (SFVec3f)getField(centerFieldName);
		center.setValue(x, y, z);
	}

	public void getCenter(float value[]) {
		SFVec3f center = (SFVec3f)getField(centerFieldName);
		center.getValue(value);
	}

	////////////////////////////////////////////////
	//	range 
	////////////////////////////////////////////////

	public void addRange(float value) {
		MFFloat range = (MFFloat)getField(rangeFieldName);
		range.addValue(value);
	}

	public int getNRanges() {
		MFFloat range = (MFFloat)getField(rangeFieldName);
		return range.getSize();
	}

	public void setRange(int index, float value) {
		MFFloat range = (MFFloat)getField(rangeFieldName);
		range.set1Value(index, value);
	}

	public float getRange(int index) {
		MFFloat range = (MFFloat)getField(rangeFieldName);
		return range.get1Value(index);
	}

	public void removeRange(int index) {
		MFFloat range = (MFFloat)getField(rangeFieldName);
		range.removeValue(index);
	}

	////////////////////////////////////////////////
	//	level
	////////////////////////////////////////////////

	public MFNode getLevelField() {
		return (MFNode)getExposedField(levelExposedField);
	}

	public void updateLevelField() {
		MFNode levelField = getLevelField();
		levelField.removeAllValues();
		for (Node node=getChildNodes(); node != null; node=node.next()) 
			levelField.addValue(node);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public LOD next() {
		return (LOD)next(getType());
	}

	public LOD nextTraversal() {
		return (LOD)nextTraversalByType(getType());
	}
*/

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
		updateLevelField();
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
		SFVec3f center = (SFVec3f)getField(centerFieldName);
		printStream.println(indentString + "\t" + "center " + center);

		MFFloat range = (MFFloat)getField(rangeFieldName);
		printStream.println(indentString + "\t" + "range [");
		range.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
