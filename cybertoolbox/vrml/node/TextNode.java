/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Text.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;

public class TextNode extends GeometryNode {
	
	//// ExposedField ////////////////
	private String	maxExtentExposedFieldName	= "maxExtent";
	private String	stringExposedFieldName		= "string";
	private String	lengthExposedFieldName		= "length";

	public TextNode() {
		setHeaderFlag(false);
		setType(textTypeName);

		///////////////////////////
		// ExposedField 
		///////////////////////////

		// maxExtent exposed field
		SFFloat maxExtent = new SFFloat(1);
		addExposedField(maxExtentExposedFieldName, maxExtent);

		// length exposed field
		MFFloat length = new MFFloat();
		addExposedField(lengthExposedFieldName, length);

		// string exposed field
		MFString string = new MFString();
		addExposedField(stringExposedFieldName, string);
	}

	////////////////////////////////////////////////
	//	MaxExtent
	////////////////////////////////////////////////
	
	public void setMaxExtent(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(maxExtentExposedFieldName);
		sffloat.setValue(value);
	}
	public float getMaxExtent() {
		SFFloat sffloat = (SFFloat)getExposedField(maxExtentExposedFieldName);
		return sffloat.getValue();
	} 

	////////////////////////////////////////////////
	// String
	////////////////////////////////////////////////

	public void addString(String value) {
		MFString string = (MFString)getExposedField(stringExposedFieldName);
		string.addValue(value);
	}
	public int getNStrings() {
		MFString string = (MFString)getExposedField(stringExposedFieldName);
		return string.getSize();
	}
	public void setString(int index, String value) {
		MFString string = (MFString)getExposedField(stringExposedFieldName);
		string.set1Value(index, value);
	}
	public String getString(int index) {
		MFString string = (MFString)getExposedField(stringExposedFieldName);
		return string.get1Value(index);
	}
	public void removeString(int index) {
		MFString string = (MFString)getExposedField(stringExposedFieldName);
		string.removeValue(index);
	}

	////////////////////////////////////////////////
	// length
	////////////////////////////////////////////////

	public void addLength(float value) {
		MFFloat length = (MFFloat)getExposedField(lengthExposedFieldName);
		length.addValue(value);
	}
	public int getNLengths() {
		MFFloat length = (MFFloat)getExposedField(lengthExposedFieldName);
		return length.getSize();
	}
	public void setLength(int index, float value) {
		MFFloat length = (MFFloat)getExposedField(lengthExposedFieldName);
		length.set1Value(index, value);
	}
	public float getLength(int index) {
		MFFloat length = (MFFloat)getExposedField(lengthExposedFieldName);
		return length.get1Value(index);
	}
	public void removeLength(int index) {
		MFFloat length = (MFFloat)getExposedField(lengthExposedFieldName);
		length.removeValue(index);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Text next() {
		return (Text)next(getType());
	}

	public Text nextTraversal() {
		return (Text)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isFontStyleNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	public void calculateBoundingBox() {
		// Not implemented
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		printStream.println(indentString + "\t" + "maxExtent " + getMaxExtent() );

		MFString string = (MFString)getExposedField(stringExposedFieldName);
		printStream.println(indentString + "\t" + "string [");
		string.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFFloat length = (MFFloat)getExposedField(lengthExposedFieldName);
		printStream.println(indentString + "\t" + "length [");
		length.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		FontStyleNode fontStyle = getFontStyleNodes();
		if (fontStyle != null) {
			if (fontStyle.isInstanceNode() == false) {
				String nodeName = fontStyle.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "fontStyle DEF " + fontStyle.getName() + " FontStyle {");
				else
					printStream.println(indentString + "\t" + "fontStyle FontStyle {");
				fontStyle.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "fontStyle USE " + fontStyle.getName());
		}
	}
}
