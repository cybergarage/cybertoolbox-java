/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : NormalInterpolator.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class NormalInterpolatorNode extends Node {

	private String	keyFieldName		= "key";
	private String	keyValueFieldName	= "keyValue";

	public NormalInterpolatorNode() {
		setHeaderFlag(false);
		setType(normalInterpolatorTypeName);

		// key exposed field
		MFFloat key = new MFFloat();
		addExposedField(keyFieldName, key);

		// keyValue exposed field
		MFVec3f keyValue = new MFVec3f();
		addExposedField(keyValueFieldName, keyValue);

		// set_fraction eventIn field
		SFFloat setFraction = new SFFloat(0.0f);
		addEventIn(fractionFieldName, setFraction);

		// value_changed eventOut field
		ConstSFVec3f valueChanged = new ConstSFVec3f(0.0f, 0.0f, 0.0f);
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
	
	public void addKeyValue(float vector[]) {
		MFVec3f keyValue = (MFVec3f)getExposedField(keyValueFieldName);
		keyValue.addValue(vector);
	}

	public void addKeyValue(float x, float y, float z) {
		MFVec3f keyValue = (MFVec3f)getExposedField(keyValueFieldName);
		keyValue.addValue(x, y, z);
	}

	public int getNKeyValues() {
		MFVec3f keyValue = (MFVec3f)getExposedField(keyValueFieldName);
		return keyValue.getSize();
	}
	
	public void setKeyValue(int index, float vector[]) {
		MFVec3f keyValue = (MFVec3f)getExposedField(keyValueFieldName);
		keyValue.set1Value(index, vector);
	}

	public void setKeyValue(int index, float x, float y, float z) {
		MFVec3f keyValue = (MFVec3f)getExposedField(keyValueFieldName);
		keyValue.set1Value(index, x, y, z);
	}

	public void getKeyValue(int index, float vector[]) {
		MFVec3f keyValue = (MFVec3f)getExposedField(keyValueFieldName);
		keyValue.get1Value(index, vector);
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
	
	public void setValue(float vector[]) {
		ConstSFVec3f value = (ConstSFVec3f)getEventOut(valueFieldName);
		value.setValue(vector);
	}

	public void getValue(float vector[]) {
		ConstSFVec3f value = (ConstSFVec3f)getEventOut(valueFieldName);
		value.getValue(vector);
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

		float vector1[] = new float[3];
		float vector2[] = new float[3];
		float vectorOut[] = new float[3];

		getKeyValue(index, vector1);
		getKeyValue(index+1, vector2);
		for (int n=0; n<3; n++)
			vectorOut[n] = vector1[n] + (vector2[n] - vector1[n])*scale;

		setValue(vectorOut);
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
	
		float vector[] = new float[3];
		printStream.println(indentString + "\tkeyValue [");
		for (int n=0; n<getNKeyValues(); n++) {
			getKeyValue(n, vector);
			if (n < getNKeyValues()-1)
				printStream.println(indentString + "\t\t" + vector[X] + " " + vector[Y] + " " + vector[Z] + ",");
			else	
				printStream.println(indentString + "\t\t" + vector[X] + " " + vector[Y] + " " + vector[Z]);
		}
		printStream.println(indentString + "\t]");
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public NormalInterpolator next() {
		return (NormalInterpolator)next(getType());
	}

	public NormalInterpolator nextTraversal() {
		return (NormalInterpolator)nextTraversalByType(getType());
	}
*/

}