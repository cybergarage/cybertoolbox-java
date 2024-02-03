/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFVec3f.java
*
******************************************************************/

package vrml.field;

import java.util.StringTokenizer;
import vrml.*;

public class SFVec3f extends Field {

	private float mValue[] = new float[3]; 

	public SFVec3f() {
		setType(fieldTypeSFVec3f);
		setValue(0.0f, 0.0f, 0.0f);
	}

	public SFVec3f(SFVec3f vector) {
		setType(fieldTypeSFVec3f);
		setValue(vector);
	}

	public SFVec3f(ConstSFVec3f vector) {
		setType(fieldTypeSFVec3f);
		setValue(vector);
	}

	public SFVec3f(float x, float y, float z) {
		setType(fieldTypeSFVec3f);
		setValue(x, y, z);
	}

	public SFVec3f(float value[]) {
		setType(fieldTypeSFVec3f);
		setValue(value);
	}

	public SFVec3f(String value) {
		setType(fieldTypeSFVec3f);
		setValue(value);
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (float[])object;
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
	//	get value
	////////////////////////////////////////////////

	public void getValue(float value[]) {
		synchronized (mValue) {
			value[0] = mValue[0];
			value[1] = mValue[1];
			value[2] = mValue[2];
		}
	}

	public float[] getValue() {
		float value[] = new float[3];
		getValue(value);
		return value;
	}

	public float getX() {
		float x;
		synchronized (mValue) {
			x = mValue[0];
		}
		return x;
	}

	public float getY() {
		float y;
		synchronized (mValue) {
			y = mValue[1];
		}
		return y;
	}

	public float getZ() {
		float z;
		synchronized (mValue) {
			z = mValue[2];
		}
		return z;
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float x, float y, float z) {
		synchronized (mValue) {
			mValue[0] = x;
			mValue[1] = y;
			mValue[2] = z;
		}
	}

	public void setValue(float value[]) {
		synchronized (mValue) {
			mValue[0] = value[0];
			mValue[1] = value[1];
			mValue[2] = value[2];
		}
	}

	public void setValue(SFVec3f vector) {
		setValue(vector.getX(), vector.getY(), vector.getZ());
	}

	public void setValue(ConstSFVec3f vector) {
		setValue(vector.getX(), vector.getY(), vector.getZ());
	}

	public void setValue(String string) {
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 3) {
				try {
					Float x = new Float(token[0]);
					Float y = new Float(token[1]);
					Float z = new Float(token[2]);
					setValue(x.floatValue(), y.floatValue(), z.floatValue());
				}
				catch (NumberFormatException e) {}
			}
		}
	}

	public void setX(float x) {
		setValue(x, getY(), getZ());
	}

	public void setY(float y) {
		setValue(getX(), y, getZ());
	}

	public void setZ(float z) {
		setValue(getX(), getY(), z);
	}

	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float x, float y, float z) {
		synchronized (mValue) {
			mValue[0] += x;
			mValue[1] += y;
			mValue[2] += z;
		}
	}

	public void add(float value[]) {
		synchronized (mValue) {
			mValue[0] += value[0];
			mValue[1] += value[1];
			mValue[2] += value[2];
		}
	}

	public void add(SFVec3f value) {
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float x, float y, float z) {
		synchronized (mValue) {
			mValue[0] -= x;
			mValue[1] -= y;
			mValue[2] -= z;
		}
	}

	public void sub(float value[]) {
		synchronized (mValue) {
			mValue[0] -= value[0];
			mValue[1] -= value[1];
			mValue[2] -= value[2];
		}
	}

	public void sub(SFVec3f value) {
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(float scale) {
		synchronized (mValue) {
			mValue[0] *= scale;
			mValue[1] *= scale;
			mValue[2] *= scale;
		}
	}

	////////////////////////////////////////////////
	//	invert
	////////////////////////////////////////////////

	public void invert() {
		synchronized (mValue) {
			mValue[0] = -mValue[0];
			mValue[1] = -mValue[1];
			mValue[2] = -mValue[2];
		}
	}

	////////////////////////////////////////////////
	//	scalar
	////////////////////////////////////////////////

	public float getScalar()
	{
		float scalar;
		synchronized (mValue) {
			scalar = (float)Math.sqrt(mValue[0]*mValue[0]+mValue[1]*mValue[1]+mValue[2]*mValue[2]);
		}
		return scalar;
	}

	////////////////////////////////////////////////
	//	normalize
	////////////////////////////////////////////////

	public void normalize()
	{
		float scale = getScalar();
		if (scale != 0.0f) {
			synchronized (mValue) {
				mValue[0] /= scale;
				mValue[1] /= scale;
				mValue[2] /= scale;
			}
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return getX() + " " + getY() + " " + getZ();
	}

}