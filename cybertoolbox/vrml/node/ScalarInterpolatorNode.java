/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ScalarInterpolator.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class ScalarInterpolatorNode extends Node {

	private String	keyFieldName		= "key";
	private String	keyValueFieldName	= "keyValue";

	public ScalarInterpolatorNode() {
		setHeaderFlag(false);
		setType(scalarInterpolatorTypeName);

		// key exposed field
		MFFloat key = new MFFloat();
		addExposedField(keyFieldName, key);

		// keyValue exposed field
		MFFloat keyValue = new MFFloat();
		addExposedField(keyValueFieldName, keyValue);

		// set_fraction eventIn field
		SFFloat setFraction = new SFFloat(0.0f);
		addEventIn(fractionFieldName, setFraction);

		// value_changed eventOut field
		ConstSFFloat valueChanged = new ConstSFFloat(0.0f);
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
	
	public void addKeyValue(float value) {
		MFFloat keyValue = (MFFloat)getExposedField(keyValueFieldName);
		keyValue.addValue(value);
	}

	public int getNKeyValues() {
		MFFloat keyValue = (MFFloat)getExposedField(keyValueFieldName);
		return keyValue.getSize();
	}
	
	public void setKeyValue(int index, float value) {
		MFFloat keyValue = (MFFloat)getExposedField(keyValueFieldName);
		keyValue.set1Value(index, value);
	}

	public float getKeyValue(int index) {
		MFFloat keyValue = (MFFloat)getExposedField(keyValueFieldName);
		return keyValue.get1Value(index);
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
	
	public void setValue(float vector) {
		ConstSFFloat value = (ConstSFFloat)getEventOut(valueFieldName);
		value.setValue(vector);
	}

	public float getValue() {
		ConstSFFloat value = (ConstSFFloat)getEventOut(valueFieldName);
		return value.getValue();
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


		float value1 = getKeyValue(index);
		float value2 = getKeyValue(index+1);
		float valueOut = value1 + (value2 - value1)*scale;

		setValue(valueOut);
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
	
		printStream.println(indentString + "\tkeyValue [");
		for (int n=0; n<getNKeyValues(); n++) {
			if (n < getNKeyValues()-1)
				printStream.println(indentString + "\t\t" + getKeyValue(n));
			else	
				printStream.println(indentString + "\t\t" + getKeyValue(n));
		}
		printStream.println(indentString + "\t]");
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public ScalarInterpolator next() {
		return (ScalarInterpolator)next(getType());
	}

	public ScalarInterpolator nextTraversal() {
		return (ScalarInterpolator)nextTraversalByType(getType());
	}
*/

}