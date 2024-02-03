/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Inline.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class InlineNode extends GroupingNode {

	private String	urlFieldName				= "url";
	
	public InlineNode() {
		super(false);
		setHeaderFlag(false);
		setType(inlineTypeName);

		// url exposed field
		MFString url = new MFString();
		addExposedField(urlFieldName, url);
	}

	////////////////////////////////////////////////
	// Url
	////////////////////////////////////////////////

	public void addUrl(String value) {
		MFString url = (MFString)getExposedField(urlFieldName);
		url.addValue(value);
	}
	public int getNUrls() {
		MFString url = (MFString)getExposedField(urlFieldName);
		return url.getSize();
	}
	public void setUrl(int index, String value) {
		MFString url = (MFString)getExposedField(urlFieldName);
		url.set1Value(index, value);
	}
	public String getUrl(int index) {
		MFString url = (MFString)getExposedField(urlFieldName);
		return url.get1Value(index);
	}
	public void removeUrl(int index) {
		MFString url = (MFString)getExposedField(urlFieldName);
		url.removeValue(index);
	}


	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Inline next() {
		return (Inline)next(getType());
	}

	public Inline nextTraversal() {
		return (Inline)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		MFString url = (MFString)getExposedField(urlFieldName);
		printStream.println(indentString + "\t" + "url [");
		url.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
