/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ColorInterpolator.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class ColorInterpolatorNode extends Node {

	private String	keyFieldName		= "key";
	private String	keyValueFieldName	= "keyValue";

	public ColorInterpolatorNode() {
		setHeaderFlag(false);
		setType(colorInterpolatorTypeName);

		// key exposed field
		MFFloat key = new MFFloat();
		addExposedField(keyFieldName, key);

		// keyValue exposed field
		MFColor keyValue = new MFColor();
		addExposedField(keyValueFieldName, keyValue);

		// set_fraction eventIn field
		SFFloat setFraction = new SFFloat(0.0f);
		addEventIn(fractionFieldName, setFraction);

		// value_changed eventOut field
		ConstSFColor valueChanged = new ConstSFColor(0.0f, 0.0f, 0.0f);
		addEventOut(valueFieldName, valueChanged);
	}

	////////////////////////////////////////////////
	//	key
	////////////////////////////////////////////////
	
	public void addKey(float value) {
		MFFloat key = (MFFloat)getExposedField(keyFieldName);
		key.addValue(value);
	}
	public int getNKeys() {
		MFFloat key = (MFFloat)getExposedField(keyFieldName);
		return key.getSize();
	}
	public void setKey(int index, float value) {
		MFFloat key = (MFFloat)getExposedField(keyFieldName);
		key.set1Value(index, value);
	}
	public float getKey(int index) {
		MFFloat key = (MFFloat)getExposedField(keyFieldName);
		return key.get1Value(index);
	}
	public void removeKey(int index) {
		MFFloat key = (MFFloat)getExposedField(keyFieldName);
		key.removeValue(index);
	}
	public Field getKeyField() {
		return getExposedField(keyFieldName);
	}

	////////////////////////////////////////////////
	//	keyValue
	////////////////////////////////////////////////
	
	public void addKeyValue(float color[]) {
		MFColor keyValue = (MFColor)getExposedField(keyValueFieldName);
		keyValue.addValue(color);
	}

	public void addKeyValue(float r, float g, float b) {
		MFColor keyValue = (MFColor)getExposedField(keyValueFieldName);
		keyValue.addValue(r, g, b);
	}

	public int getNKeyValues() {
		MFColor keyValue = (MFColor)getExposedField(keyValueFieldName);
		return keyValue.getSize();
	}
	
	public void setKeyValue(int index, float color[]) {
		MFColor keyValue = (MFColor)getExposedField(keyValueFieldName);
		keyValue.set1Value(index, color);
	}

	public void setKeyValue(int index, float r, float g, float b) {
		MFColor keyValue = (MFColor)getExposedField(keyValueFieldName);
		keyValue.set1Value(index, r, g, b);
	}

	public void getKeyValue(int index, float color[]) {
		MFColor keyValue = (MFColor)getExposedField(keyValueFieldName);
		keyValue.get1Value(index, color);
	}

	public float[] getKeyValue(int index) {
		float value[] = new float[3];
		getKeyValue(index, value);
		return value;
	}

	public void removeKeyValue(int index) {
		MFVec3f keyValue = (MFVec3f)getExposedField(keyValueFieldName);
		keyValue.removeValue(index);
	}

	public Field getKeyValueField() {
		return getExposedField(keyValueFieldName);
	}

	////////////////////////////////////////////////
	//	fraction
	////////////////////////////////////////////////
	
	public void setFraction(float value) {
		SFFloat fraction = (SFFloat)getEventIn(fractionFieldName);
		fraction.setValue(value);
	}

	public float getFraction() {
		SFFloat fraction = (SFFloat)getEventIn(fractionFieldName);
		return fraction.getValue();
	}

	public Field getFractionField() {
		return getEventIn(fractionFieldName);
	}

	////////////////////////////////////////////////
	//	value
	////////////////////////////////////////////////
	
	public void setValue(float color[]) {
		ConstSFColor value = (ConstSFColor)getEventOut(valueFieldName);
		value.setValue(color);
	}

	public void getValue(float color[]) {
		ConstSFColor value = (ConstSFColor)getEventOut(valueFieldName);
		value.getValue(color);
	}

	public Field getValueField() {
		return getEventOut(valueFieldName);
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

		float fraction = getFraction();
		int index = -1;
		for (int n=0; n<(getNKeys()-1); n++) {
			if (getKey(n) <= fraction && fraction <= getKey(n+1)) {
				index = n;
				break;
			}
		}
		if (index == -1)
			return;

		float scale = (fraction - getKey(index)) / (getKey(index+1) - getKey(index));

		float color1[] = new float[3];
		float color2[] = new float[3];
		float colorOut[] = new float[3];

		getKeyValue(index, color1);
		getKeyValue(index+1, color2);
		for (int n=0; n<3; n++)
			colorOut[n] = color1[n] + (color2[n] - color1[n])*scale;

		setValue(colorOut);
		sendEvent(getValueField());
	}

	////////////////////////////////////////////////
	//	Output
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {

		printStream.println(indentString + "\tkey [");
		for (int n=0; n<getNKeys(); n++) {
			if (n < getNKeys()-1)
				printStream.println(indentString + "\t\t" + getKey(n));
			else	
				printStream.println(indentString + "\t\t" + getKey(n));
		}
		printStream.println(indentString + "\t]");
	
		float color[] = new float[3];
		printStream.println(indentString + "\tkeyValue [");
		for (int n=0; n<getNKeyValues(); n++) {
			getKeyValue(n, color);
			if (n < getNKeyValues()-1)
				printStream.println(indentString + "\t\t" + color[X] + " " + color[Y] + " " + color[Z] + ",");
			else	
				printStream.println(indentString + "\t\t" + color[X] + " " + color[Y] + " " + color[Z]);
		}
		printStream.println(indentString + "\t]");
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public ColorInterpolator next() {
		return (ColorInterpolator)next(getType());
	}

	public ColorInterpolator nextTraversal() {
		return (ColorInterpolator)nextTraversalByType(getType());
	}
*/

}