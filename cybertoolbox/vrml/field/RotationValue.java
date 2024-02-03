/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : RotationValue.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class RotationValue extends Object {

	private SFVec3f		mVector = new SFVec3f(0.0f, 0.0f, 1.0f);
	private FloatValue	mAngle	= new FloatValue(0.0f);

	public RotationValue() {
		setValue(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public RotationValue(float x, float y, float z, float rot) {
		setValue(x, y, z, rot);
	}

	public RotationValue(float value[]) {
		setValue(value);
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float x, float y, float z, float rot) {
		mVector.setValue(x, y, z);
		mVector.normalize();
		setAngle(rot);
	}

	public void setValue(float value[]) {
		mVector.setValue(value);
		mVector.normalize();
		setAngle(value[3]);
	}

	public void setAngle(float angle) {
		synchronized (mAngle) {
			mAngle.setValue(angle);
		}
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(float value[]) {
		mVector.getValue(value);
		synchronized (mAngle) {
			value[3] = mAngle.getValue();
		}
	}

	public float[] getValue() {
		float value[] = new float[4];
		getValue(value);
		return value;
	}

	public void getVector(float vector[]) {
		mVector.getValue(vector);
	}

	public float[] getVector() {
		float vector[] = new float[3];
		mVector.getValue(vector);
		return vector;
	}

	float getX() {
		return mVector.getX();
	}

	float getY() {
		return mVector.getY();
	}

	float getZ() {
		return mVector.getZ();
	}

	public float getAngle() {
		float angle;
		synchronized (mAngle) {
			angle = mAngle.getValue();
		}
		return angle;
	}
}