/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Transform.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class TransformNode extends GroupingNode {

	private String	translationFieldName		= "translation";
	private String	scaleFieldName				= "scale";
	private String	centerFieldName				= "center";
	private String	rotationFieldName			= "rotation";
	private String	scaleOrientationFieldName	= "scaleOrientation";
	
	public TransformNode() {
		super();

		setHeaderFlag(false);
		setType(transformTypeName);

		// translation exposed field
		SFVec3f translation = new SFVec3f(0.0f, 0.0f, 0.0f);
		translation.setName(translationFieldName);
		addExposedField(translation);

		// scale exposed field
		SFVec3f scale = new SFVec3f(1.0f, 1.0f, 1.0f);
		scale.setName(scaleFieldName);
		addExposedField(scale);

		// center exposed field
		SFVec3f center = new SFVec3f(0.0f, 0.0f, 0.0f);
		center.setName(centerFieldName);
		addExposedField(center);

		// rotation exposed field
		SFRotation rotation = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		rotation.setName(rotationFieldName);
		addExposedField(rotation);

		// scaleOrientation exposed field
		SFRotation scaleOrientation = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		scaleOrientation.setName(scaleOrientationFieldName);
		addExposedField(scaleOrientation);
	}

	////////////////////////////////////////////////
	//	Translation
	////////////////////////////////////////////////

	public void setTranslation(float value[]) {
		SFVec3f translation = (SFVec3f)getExposedField(translationFieldName);
		translation.setValue(value);
	}
	public void setTranslation(float x, float y, float z) {
		SFVec3f translation = (SFVec3f)getExposedField(translationFieldName);
		translation.setValue(x, y, z);
	}
	public void getTranslation(float value[]) {
		SFVec3f translation = (SFVec3f)getExposedField(translationFieldName);
		translation.getValue(value);
	}
	public void addTranslation(float value[]) {
		SFVec3f translation = (SFVec3f)getExposedField(translationFieldName);
		translation.add(value);
	}
	public void addTranslation(float x, float y, float z) {
		SFVec3f translation = (SFVec3f)getExposedField(translationFieldName);
		translation.add(x, y, z);
	}

	////////////////////////////////////////////////
	//	Scale
	////////////////////////////////////////////////

	public void setScale(float value[]) {
		SFVec3f scale = (SFVec3f)getExposedField(scaleFieldName);
		scale.setValue(value);
	}
	public void setScale(float x, float y, float z) {
		SFVec3f scale = (SFVec3f)getExposedField(scaleFieldName);
		scale.setValue(x, y, z);
	}
	public void getScale(float value[]) {
		SFVec3f scale = (SFVec3f)getExposedField(scaleFieldName);
		scale.getValue(value);
	}
	public void addScale(float value[]) {
		SFVec3f scale = (SFVec3f)getExposedField(scaleFieldName);
		scale.add(value);
	}
	public void addScale(float x, float y, float z) {
		SFVec3f scale = (SFVec3f)getExposedField(scaleFieldName);
		scale.add(x, y, z);
	}

	////////////////////////////////////////////////
	//	Center
	////////////////////////////////////////////////

	public void setCenter(float value[]) {
		SFVec3f center = (SFVec3f)getExposedField(centerFieldName);
		center.setValue(value);
	}
	public void setCenter(float x, float y, float z) {
		SFVec3f center = (SFVec3f)getExposedField(centerFieldName);
		center.setValue(x, y, z);
	}
	public void getCenter(float value[]) {
		SFVec3f center = (SFVec3f)getExposedField(centerFieldName);
		center.getValue(value);
	}
	public void addCenter(float value[]) {
		SFVec3f center = (SFVec3f)getExposedField(centerFieldName);
		center.add(value);
	}
	public void addCenter(float x, float y, float z) {
		SFVec3f center = (SFVec3f)getExposedField(centerFieldName);
		center.add(x, y, z);
	}

	////////////////////////////////////////////////
	//	Rotation
	////////////////////////////////////////////////

	public void setRotation(float value[]) {
		SFRotation rotation = (SFRotation)getExposedField(rotationFieldName);
		rotation.setValue(value);
	}
	public void setRotation(float x, float y, float z, float w) {
		SFRotation rotation = (SFRotation)getExposedField(rotationFieldName);
		rotation.setValue(x, y, z, w);
	}
	public void getRotation(float value[]) {
		SFRotation rotation = (SFRotation)getExposedField(rotationFieldName);
		rotation.getValue(value);
	}
	public void addRotation(float value[]) {
		SFRotation rotation = (SFRotation)getExposedField(rotationFieldName);
		rotation.add(value);
	}
	public void addRotation(float x, float y, float z, float w) {
		SFRotation rotation = (SFRotation)getExposedField(rotationFieldName);
		rotation.add(x, y, z, w);
	}

	////////////////////////////////////////////////
	//	ScaleOrientation
	////////////////////////////////////////////////

	public void setScaleOrientation(float value[]) {
		SFRotation scaleOrientation = (SFRotation)getExposedField(scaleOrientationFieldName);
		scaleOrientation.setValue(value);
	}
	public void setScaleOrientation(float x, float y, float z, float w) {
		SFRotation scaleOrientation = (SFRotation)getExposedField(scaleOrientationFieldName);
		scaleOrientation.setValue(x, y, z, w);
	}
	public void getScaleOrientation(float value[]) {
		SFRotation scaleOrientation = (SFRotation)getExposedField(scaleOrientationFieldName);
		scaleOrientation.getValue(value);
	}
	public void addScaleOrientation(float value[]) {
		SFRotation scaleOrientation = (SFRotation)getExposedField(scaleOrientationFieldName);
		scaleOrientation.add(value);
	}
	public void addScaleOrientation(float x, float y, float z, float w) {
		SFRotation scaleOrientation = (SFRotation)getExposedField(scaleOrientationFieldName);
		scaleOrientation.add(x, y, z, w);
	}

	////////////////////////////////////////////////
	//	Matrix
	////////////////////////////////////////////////

	public void getSFMatrix(SFMatrix mOut)
	{
		float	center[]	= new float[3];
		float	rotation[]	= new float[4];
		float	scale[]		= new float[3];
		float	scaleOri[]	= new float[4];
		float	trans[]		= new float[3];

		SFMatrix	mSRI	= new SFMatrix();
		SFMatrix	mSR		= new SFMatrix();
		SFMatrix	mCI		= new SFMatrix();
		SFMatrix	mC		= new SFMatrix();
		SFMatrix	mT		= new SFMatrix();
		SFMatrix	mR		= new SFMatrix();
		SFMatrix	mS		= new SFMatrix();

		getTranslation(trans); 
		mT.setValueAsTranslation(trans);

		getCenter(center); 
		mC.setValueAsTranslation(center);

		getRotation(rotation);
		mR.setValueAsRotation(rotation);

		getScaleOrientation(scaleOri); 
		mSR.setValueAsRotation(scaleOri);

		getScale(scale);
		mS.setValueAsScaling(scale);

		getScaleOrientation(scaleOri); 
		scaleOri[3] = -scaleOri[3]; 
		mSRI.setValueAsRotation(scaleOri);

		getCenter(center); 
		center[X] = -center[X]; 
		center[Y] = -center[Y]; 
		center[Z] = -center[Z]; 
		mCI.setValueAsTranslation(center);

		mOut.add(mT);
		mOut.add(mC);
		mOut.add(mR);
		mOut.add(mSR);
		mOut.add(mS);
		mOut.add(mSRI);
		mOut.add(mCI);
	}

	public SFMatrix getSFMatrix() {
		SFMatrix mx = new SFMatrix();
		getSFMatrix(mx);
		return mx;
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Transform next() {
		return (Transform)next(getType());
	}

	public Transform nextTraversal() {
		return (Transform)nextTraversalByType(getType());
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
		float vec[] = new float[3];
		float rot[] = new float[4];
		getTranslation(vec);		printStream.println(indentString + "\t" + "translation " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getRotation(rot);			printStream.println(indentString + "\t" + "rotation " + rot[X] + " "+ rot[Y] + " " + rot[Z] + " " + rot[W] );
		getScale(vec);				printStream.println(indentString + "\t" + "scale " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getScaleOrientation(rot);	printStream.println(indentString + "\t" + "scaleOrientation " + rot[X] + " "+ rot[Y] + " " + rot[Z] + " " + rot[W] );
		getCenter(vec);				printStream.println(indentString + "\t" + "center " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
	}
}
