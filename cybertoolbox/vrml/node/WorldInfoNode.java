/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : WorldInfo.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.lang.String;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class WorldInfoNode extends Node {
	
	private String	titleExposedFieldName		= "title";
	private String	infoExposedFieldName		= "info";

	public WorldInfoNode() {
		setHeaderFlag(false);
		setType(worldInfoTypeName);

		// title exposed field
		SFString title = new SFString("");
		addExposedField(titleExposedFieldName, title);

		// info exposed field
		MFString info = new MFString();
		addExposedField(infoExposedFieldName, info);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public WorldInfo next() {
		return (WorldInfo)next(getType());
	}

	public WorldInfo nextTraversal() {
		return (WorldInfo)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	Title
	////////////////////////////////////////////////
	
	public void setTitle(String value) {
		SFString title = (SFString)getExposedField(titleExposedFieldName);
		title.setValue(value);
	}
	public String getTitle() {
		SFString title = (SFString)getExposedField(titleExposedFieldName);
		return title.getValue();
	}

	////////////////////////////////////////////////
	// Info
	////////////////////////////////////////////////

	public void addInfo(String value) {
		MFString info = (MFString)getExposedField(infoExposedFieldName);
		info.addValue(value);
	}
	public int getNInfos() {
		MFString info = (MFString)getExposedField(infoExposedFieldName);
		return info.getSize();
	}
	public void setInfo(int index, String value) {
		MFString info = (MFString)getExposedField(infoExposedFieldName);
		info.set1Value(index, value);
	}
	public String getInfo(int index) {
		MFString info = (MFString)getExposedField(infoExposedFieldName);
		return info.get1Value(index);
	}
	public void removeInfo(int index) {
		MFString info = (MFString)getExposedField(infoExposedFieldName);
		info.removeValue(index);
	}

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
		SFString title = (SFString)getExposedField(titleExposedFieldName);
		printStream.println(indentString + "\t" + "title " + title );

		MFString info = (MFString)getExposedField(infoExposedFieldName);
		printStream.println(indentString + "\t" + "info [");
		info.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
