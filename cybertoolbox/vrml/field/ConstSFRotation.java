/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstSFRotation.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class ConstSFRotation extends ConstField {

	private RotationValue mValue= new RotationValue();

	public ConstSFRotation() {
		setType(fieldTypeConstSFRotation);
		setValue(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public ConstSFRotation(SFRotation rotation) {
		setType(fieldTypeConstSFRotation);
		setValue(rotation);
	}

	public ConstSFRotation(ConstSFRotation rotation) {
		setType(fieldTypeConstSFRotation);
		setValue(rotation);
	}

	public ConstSFRotation(float x, float y, float z, float rot) {
		setType(fieldTypeConstSFRotation);
		setValue(x, y, z, rot);
	}

	public ConstSFRotation(double x, double y, double z, double rot) {
		setType(fieldTypeConstSFRotation);
		setValue(x, y, z, rot);
	}

	public ConstSFRotation(float value[]) {
		setType(fieldTypeConstSFRotation);
		setValue(value);
	}

	public ConstSFRotation(double value[]) {
		setType(fieldTypeConstSFRotation);
		setValue(value);
	}

	public ConstSFRotation(float vector[], float angle) {
		setType(fieldTypeConstSFRotation);
		setValue(vector, angle);
	}

	public ConstSFRotation(double vector[], double angle) {
		setType(fieldTypeConstSFRotation);
		setValue(vector[0], vector[1], vector[2], angle);
	}

	public ConstSFRotation(String value) {
		setType(fieldTypeConstSFRotation);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (RotationValue)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mValue) {
			object = mValue;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(double x, double y, double z, double rot) {
		mValue.setValue((float)x, (float)y, (float)z, (float)rot);
	}

	public void setValue(double value[]) {
		setValue(value[0], value[1], value[2], value[3]);
	}

	public void setValue(float value[]) {
		setValue(value[0], value[1], value[2], value[3]);
	}

	public void setValue(float vector[], float angle) {
		setValue(vector[0], vector[1], vector[2], angle);
	}

	public void setValue(double vector[], float angle) {
		setValue(vector[0], vector[1], vector[2], angle);
	}

	public void setValue(SFRotation rotation) {
		setValue(rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getAngle());
	}

	public void setValue(ConstSFRotation rotation) {
		setValue(rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getAngle());
	}

	public void setValue(String string) {
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 4) {
				try {
					Float x = new Float(token[0]);
					Float y = new Float(token[1]);
					Float z = new Float(token[2]);
					Float a = new Float(token[3]);
					setValue(x.floatValue(), y.floatValue(), z.floatValue(), a.floatValue());
				}
				catch (NumberFormatException e) {}
			}
		}
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public void getValue(float value[]) {
		mValue.getValue(value);
	}

	public float[] getValue() {
		float value[] = new float[4];
		getValue(value);
		return value;
	}

	public void getVector(float vector[]) {
		mValue.getVector(vector);
	}

	public float[] getVector() {
		float vector[] = new float[3];
		getVector(vector);
		return vector;
	}

	public float getX() {
		return mValue.getX();
	}

	public float getY() {
		return mValue.getY();
	}

	public float getZ() {
		return mValue.getZ();
	}

	public float getAngle() {
		return mValue.getAngle();
	}

	////////////////////////////////////////////////
	//	add 
	////////////////////////////////////////////////

	public void add(SFRotation rot)
	{
		SFMatrix m1 = new SFMatrix();
		getSFMatrix(m1);

		SFMatrix m2 = new SFMatrix();
		rot.getSFMatrix(m2);
		
		m1.add(m2);

		SFRotation newRotation = new SFRotation();
		m1.getSFRotation(newRotation);

		setValue(newRotation);
	}

	public void add(float rotationValue[]) {
		SFRotation rotation = new SFRotation(rotationValue);
		add(rotation);
	}

	public void add(float x, float y, float z, float rot) {
		SFRotation rotation = new SFRotation(x, y, z, rot);
		add(rotation);
	}

	////////////////////////////////////////////////
	//	multi 
	////////////////////////////////////////////////

	public void multi(float vector[])
	{
		SFMatrix m = new SFMatrix();
		getSFMatrix(m);
		m.multi(vector);
	}

	public void multi(double vector[])
	{
		SFMatrix m = new SFMatrix();
		getSFMatrix(m);
		m.multi(vector);
	}

	public void multi(SFVec3f vector)
	{
		SFMatrix m = new SFMatrix();
		getSFMatrix(m);
		m.multi(vector);
	}

	////////////////////////////////////////////////
	//	convert
	////////////////////////////////////////////////

	public void getSFMatrix(SFMatrix matrix)
	{
		float vector[] = getVector();
		float rotation = getAngle();

		double k = (1.0 - Math.cos(rotation));
		double s = Math.sin(rotation);
		double c = Math.cos(rotation);
		double ax = vector[0];
		double ay = vector[1];
		double az = vector[2];
		double ax2 = ax * ax;
		double ay2 = ay * ay;
		double az2 = az * az;

		double ma[][] = new double[4][4];
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++)
				ma[i][j] = 0.0f;
		}

		ma[0][0] = k*ax2 + c;
		ma[0][1] = k*ax*ay + s*az;
		ma[0][2] = k*ax*az - s*ay;
		ma[1][0] = k*ax*ay - s*az;
		ma[1][1] = k*ay2 + c;
		ma[1][2] = k*ay*az + s*ax;
		ma[2][0] = k*ax*az + s*ay;
		ma[2][1] = k*ay*az - s*ax;
		ma[2][2] = k*az2 + c;

		matrix.setValue(ma);
	}

	////////////////////////////////////////////////
	//	invert
	////////////////////////////////////////////////

	public void invert() {
		mValue.setAngle(-mValue.getAngle());
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		float vector[] = new float[3];
		getVector(vector);
		return vector[0] + " " + vector[1] + " " + vector[2] + " " + getAngle();
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		SFRotation field = new SFRotation();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}