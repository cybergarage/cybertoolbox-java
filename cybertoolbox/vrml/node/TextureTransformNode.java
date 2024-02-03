/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TextureTransform.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class TextureTransformNode extends Node {

	private String	translationFieldName		= "translation";
	private String	scaleFieldName				= "scale";
	private String	centerFieldName				= "center";
	private String	rotationFieldName			= "rotation";
	
	public TextureTransformNode() {
		setHeaderFlag(false);
		setType(textureTransformTypeName);

		// translation exposed field
		SFVec2f translation = new SFVec2f(0.0f, 0.0f);
		translation.setName(translationFieldName);
		addExposedField(translation);

		// scale exposed field
		SFVec2f scale = new SFVec2f(1.0f, 1.0f);
		scale.setName(scaleFieldName);
		addExposedField(scale);

		// center exposed field
		SFVec2f center = new SFVec2f(0.0f, 0.0f);
		center.setName(centerFieldName);
		addExposedField(center);

		// rotation exposed field
		SFFloat rotation = new SFFloat(0.0f);
		rotation.setName(rotationFieldName);
		addExposedField(rotation);
	}

	////////////////////////////////////////////////
	//	Translation
	////////////////////////////////////////////////

	public void setTranslation(float value[]) {
		SFVec2f translation = (SFVec2f)getExposedField(translationFieldName);
		translation.setValue(value);
	}
	public void setTranslation(float x, float y) {
		SFVec2f translation = (SFVec2f)getExposedField(translationFieldName);
		translation.setValue(x, y);
	}
	public void getTranslation(float value[]) {
		SFVec2f translation = (SFVec2f)getExposedField(translationFieldName);
		translation.getValue(value);
	}

	////////////////////////////////////////////////
	//	Scale
	////////////////////////////////////////////////

	public void setScale(float value[]) {
		SFVec2f scale = (SFVec2f)getExposedField(scaleFieldName);
		scale.setValue(value);
	}
	public void setScale(float x, float y) {
		SFVec2f scale = (SFVec2f)getExposedField(scaleFieldName);
		scale.setValue(x, y);
	}
	public void getScale(float value[]) {
		SFVec2f scale = (SFVec2f)getExposedField(scaleFieldName);
		scale.getValue(value);
	}

	////////////////////////////////////////////////
	//	Center
	////////////////////////////////////////////////

	public void setCenter(float value[]) {
		SFVec2f center = (SFVec2f)getExposedField(centerFieldName);
		center.setValue(value);
	}
	public void setCenter(float x, float y) {
		SFVec2f center = (SFVec2f)getExposedField(centerFieldName);
		center.setValue(x, y);
	}
	public void getCenter(float value[]) {
		SFVec2f center = (SFVec2f)getExposedField(centerFieldName);
		center.getValue(value);
	}

	////////////////////////////////////////////////
	//	Rotation
	////////////////////////////////////////////////

	public void setRotation(float value) {
		SFFloat rotation = (SFFloat)getExposedField(rotationFieldName);
		rotation.setValue(value);
	}
	public float getRotation() {
		SFFloat rotation = (SFFloat)getExposedField(rotationFieldName);
		return rotation.getValue();
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public TextureTransform next() {
		return (TextureTransform)next(getType());
	}

	public TextureTransform nextTraversal() {
		return (TextureTransform)nextTraversalByType(getType());
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
		float vec[] = new float[2];
		getTranslation(vec);		printStream.println(indentString + "\t" + "translation " + vec[X] + " "+ vec[Y]);
		printStream.println(indentString + "\t" + "rotation " + getRotation());
		getScale(vec);				printStream.println(indentString + "\t" + "scale " + vec[X] + " "+ vec[Y]);
		getCenter(vec);				printStream.println(indentString + "\t" + "center " + vec[X] + " "+ vec[Y]);
	}
}
