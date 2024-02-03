/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Anchor.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class AnchorNode extends GroupingNode {

	//// ExposedField ////////////////
	private String	descriptionExposedFieldName		= "description";
	private String	parameterExposedFieldName			= "parameter";
	private String	urlExposedFieldName				= "url";
	
	public AnchorNode() {
		super();

		setHeaderFlag(false);
		setType(anchorTypeName);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// description exposed field
		SFString description = new SFString("");
		addExposedField(descriptionExposedFieldName, description);

		// parameter exposed field
		MFString parameter = new MFString();
		addExposedField(parameterExposedFieldName, parameter);

		// url exposed field
		MFString url = new MFString();
		addExposedField(urlExposedFieldName, url);
	}

	////////////////////////////////////////////////
	//	Description
	////////////////////////////////////////////////

	public void setDescription(String value) {
		SFString description = (SFString)getExposedField(descriptionExposedFieldName);
		description.setValue(value);
	}

	public String getDescription() {
		SFString description = (SFString)getExposedField(descriptionExposedFieldName);
		return description.getValue();
	}

	////////////////////////////////////////////////
	// Parameter
	////////////////////////////////////////////////

	public void addParameter(String value) {
		MFString parameter = (MFString)getExposedField(parameterExposedFieldName);
		parameter.addValue(value);
	}
	public int getNParameters() {
		MFString parameter = (MFString)getExposedField(parameterExposedFieldName);
		return parameter.getSize();
	}
	public void setParameter(int index, String value) {
		MFString parameter = (MFString)getExposedField(parameterExposedFieldName);
		parameter.set1Value(index, value);
	}
	public String getParameter(int index) {
		MFString parameter = (MFString)getExposedField(parameterExposedFieldName);
		return parameter.get1Value(index);
	}
	public void removeParameter(int index) {
		MFString parameter = (MFString)getExposedField(parameterExposedFieldName);
		parameter.removeValue(index);
	}

	////////////////////////////////////////////////
	// Url
	////////////////////////////////////////////////

	public void addUrl(String value) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		url.addValue(value);
	}
	public int getNUrls() {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		return url.getSize();
	}
	public void setUrl(int index, String value) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		url.set1Value(index, value);
	}
	public String getUrl(int index) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		return url.get1Value(index);
	}
	public void removeUrl(int index) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		url.removeValue(index);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Anchor next() {
		return (Anchor)next(getType());
	}

	public Anchor nextTraversal() {
		return (Anchor)nextTraversalByType(getType());
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
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		SFString description = (SFString)getExposedField(descriptionExposedFieldName);
		printStream.println(indentString + "\t" + "description " + description);
		
		MFString parameter = (MFString)getExposedField(parameterExposedFieldName);
		printStream.println(indentString + "\t" + "parameter [");
		parameter.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString url = (MFString)getExposedField(urlExposedFieldName);
		printStream.println(indentString + "\t" + "url [");
		url.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
