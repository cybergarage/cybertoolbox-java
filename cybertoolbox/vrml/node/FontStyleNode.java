/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : FontStyle.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.lang.String;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class FontStyleNode extends Node {
	
	//// Field ////////////////
	private String	familyFieldName			= "family";
	private String	styleFieldName			= "style";
	private String	languageFieldName		= "language";
	private String	justifyFieldName		= "justify";
	private String	sizeFieldName			= "size";
	private String	spacingFieldName		= "spacing";
	private String	horizontalFieldName		= "horizontal";
	private String	leftToRightFieldName	= "leftToRight";
	private String	topToBottomFieldName	= "topToBottom";

	public FontStyleNode() {
		setHeaderFlag(false);
		setType(fontStyleTypeName);

		///////////////////////////
		// Field 
		///////////////////////////

		// family field
		MFString family = new MFString();
		family.setName(familyFieldName);
		addField(family);

		// style field
		SFString style = new SFString("PLAIN");
		style.setName(styleFieldName);
		addField(style);

		// language field
		SFString language = new SFString("");
		language.setName(languageFieldName);
		addField(language);

		// justify field
		MFString justify = new MFString();
		justify.setName(justifyFieldName);
		addField(justify);

		// size field
		SFFloat size = new SFFloat(1);
		addField(sizeFieldName, size);

		// spacing field
		SFFloat spacing = new SFFloat(1);
		addField(spacingFieldName, spacing);

		// horizontal field
		SFBool horizontal = new SFBool(true);
		addField(horizontalFieldName, horizontal);

		// leftToRight field
		SFBool leftToRight = new SFBool(true);
		addField(leftToRightFieldName, leftToRight);

		// topToBottom field
		SFBool topToBottom = new SFBool(true);
		addField(topToBottomFieldName, topToBottom);
	}

	////////////////////////////////////////////////
	//	Size
	////////////////////////////////////////////////

	public void setSize(float value) {
		SFFloat size = (SFFloat)getField(sizeFieldName);
		size.setValue(value);
	}
	public float getSize() {
		SFFloat size = (SFFloat)getField(sizeFieldName);
		return size.getValue();
	}

	////////////////////////////////////////////////
	// Family
	////////////////////////////////////////////////

	public void addFamily(String value) {
		MFString family = (MFString)getField(familyFieldName);
		family.addValue(value);
	}
	public int getNFamilies() {
		MFString family = (MFString)getField(familyFieldName);
		return family.getSize();
	}
	public void setFamily(int index, String value) {
		MFString family = (MFString)getField(familyFieldName);
		family.set1Value(index, value);
	}
	public String getFamily(int index) {
		MFString family = (MFString)getField(familyFieldName);
		return family.get1Value(index);
	}
	public void removeFamily(int index) {
		MFString family = (MFString)getField(familyFieldName);
		family.removeValue(index);
	}

	////////////////////////////////////////////////
	//	Style
	////////////////////////////////////////////////
	
	public void setStyle(String value) {
		SFString style = (SFString)getField(styleFieldName);
		style.setValue(value);
	}
	public String getStyle() {
		SFString style = (SFString)getField(styleFieldName);
		return style.getValue();
	}

	////////////////////////////////////////////////
	//	Language
	////////////////////////////////////////////////
	
	public void setLanguage(String value) {
		SFString language = (SFString)getField(languageFieldName);
		language.setValue(value);
	}
	public String getLanguage() {
		SFString language = (SFString)getField(languageFieldName);
		return language.getValue();
	}

	////////////////////////////////////////////////
	//	Horizontal
	////////////////////////////////////////////////
	
	public void setHorizontal(boolean value) {
		SFBool horizontal = (SFBool)getField(horizontalFieldName);
		horizontal.setValue(value);
	}
	public boolean getHorizontal() {
		SFBool horizontal = (SFBool)getField(horizontalFieldName);
		return horizontal.getValue();
	}

	////////////////////////////////////////////////
	//	LeftToRight
	////////////////////////////////////////////////
	
	public void setLeftToRight(boolean value) {
		SFBool leftToRight = (SFBool)getField(leftToRightFieldName);
		leftToRight.setValue(value);
	}
	public boolean getLeftToRight() {
		SFBool leftToRight = (SFBool)getField(leftToRightFieldName);
		return leftToRight.getValue();
	}

	////////////////////////////////////////////////
	//	TopToBottom
	////////////////////////////////////////////////
	
	public void setTopToBottom(boolean value) {
		SFBool topToBottom = (SFBool)getField(topToBottomFieldName);
		topToBottom.setValue(value);
	}
	public boolean getTopToBottom() {
		SFBool topToBottom = (SFBool)getField(topToBottomFieldName);
		return topToBottom.getValue();
	}

	////////////////////////////////////////////////
	// Justify
	////////////////////////////////////////////////

	public void addJustify(String value) {
		MFString justify = (MFString)getField(justifyFieldName);
		justify.addValue(value);
	}
	public int getNJustifies() {
		MFString justify = (MFString)getField(justifyFieldName);
		return justify.getSize();
	}
	public void setJustify(int index, String value) {
		MFString justify = (MFString)getField(justifyFieldName);
		justify.set1Value(index, value);
	}
	public String getJustify(int index) {
		MFString justify = (MFString)getField(justifyFieldName);
		return justify.get1Value(index);
	}
	public void removeJustify(int index) {
		MFString justify = (MFString)getField(justifyFieldName);
		justify.removeValue(index);
	}

	////////////////////////////////////////////////
	//	Spacing
	////////////////////////////////////////////////

	public void setSpacing(float value) {
		SFFloat spacing = (SFFloat)getField(spacingFieldName);
		spacing.setValue(value);
	}
	public float getSpacing() {
		SFFloat spacing = (SFFloat)getField(spacingFieldName);
		return spacing.getValue();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public FontStyle next() {
		return (FontStyle)next(getType());
	}

	public FontStyle nextTraversal() {
		return (FontStyle)nextTraversalByType(getType());
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
	//	Justifymation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool horizontal = (SFBool)getField(horizontalFieldName);
		SFBool leftToRight = (SFBool)getField(leftToRightFieldName);
		SFBool topToBottom = (SFBool)getField(topToBottomFieldName);
		SFString style = (SFString)getField(styleFieldName);
		SFString language = (SFString)getField(languageFieldName);

		printStream.println(indentString + "\t" + "size " + getSize() );
		printStream.println(indentString + "\t" + "style " + style );
		printStream.println(indentString + "\t" + "horizontal " + horizontal );
		printStream.println(indentString + "\t" + "leftToRight " + leftToRight );
		printStream.println(indentString + "\t" + "topToBottom " + topToBottom );
		printStream.println(indentString + "\t" + "language " + language );
		printStream.println(indentString + "\t" + "spacing " + getSpacing() );

		MFString family = (MFString)getField(familyFieldName);
		printStream.println(indentString + "\t" + "family [");
		family.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString justify = (MFString)getField(justifyFieldName);
		printStream.println(indentString + "\t" + "justify [");
		justify.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
