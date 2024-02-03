/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstSFVec2f.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class ConstSFVec2f extends ConstField {

	private float mValue[] = new float[2]; 

	public ConstSFVec2f() {
		setType(fieldTypeConstSFVec2f);
		setValue(0.0f, 0.0f);
	}

	public ConstSFVec2f(SFVec2f vector) {
		setType(fieldTypeConstSFVec2f);
		setValue(vector);
	}

	public ConstSFVec2f(ConstSFVec2f vector) {
		setType(fieldTypeConstSFVec2f);
		setValue(vector);
	}

	public ConstSFVec2f(float x, float y) {
		setType(fieldTypeConstSFVec2f);
		setValue(x, y);
	}

	public ConstSFVec2f(float value[]) {
		setType(fieldTypeConstSFVec2f);
		setValue(value);
	}

	public ConstSFVec2f(String value) {
		setType(fieldTypeConstSFVec2f);
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

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float x, float y) {
		synchronized (mValue) {
			mValue[0] = x;
			mValue[1] = y;
		}
	}

	public void setValue(float value[]) {
		synchronized (mValue) {
			mValue[0] = value[0];
			mValue[1] = value[1];
		}
	}

	public void setValue(SFVec2f vector) {
		setValue(vector.getX(), vector.getY());
	}

	public void setValue(ConstSFVec2f vector) {
		setValue(vector.getX(), vector.getY());
	}

	public void setX(float x) {
		setValue(x, getY());
	}

	public void setY(float y) {
		setValue(getX(), y);
	}

	public void setValue(String string) {
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 2) {
				try {
					Float x = new Float(token[0]);
					Float y = new Float(token[1]);
					setValue(x.floatValue(), y.floatValue());
				}
				catch (NumberFormatException e) {}
			}
		}
	}

	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float x, float y) {
		synchronized (mValue) {
			mValue[0] += x;
			mValue[1] += y;
		}
	}

	public void add(float value[]) {
		synchronized (mValue) {
			mValue[0] += value[0];
			mValue[1] += value[1];
		}
	}

	public void add(SFVec2f value) {
		add(value.getValue());
	}

	////////////////////////////////////////////////
	//	sub value
	////////////////////////////////////////////////

	public void sub(float x, float y) {
		synchronized (mValue) {
			mValue[0] -= x;
			mValue[1] -= y;
		}
	}

	public void sub(float value[]) {
		synchronized (mValue) {
			mValue[0] -= value[0];
			mValue[1] -= value[1];
		}
	}

	public void sub(SFVec2f value) {
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(float scale) {
		synchronized (mValue) {
			mValue[0] *= scale;
			mValue[1] *= scale;
		}
	}	
	
	////////////////////////////////////////////////
	//	invert
	////////////////////////////////////////////////

	public void invert() {
		synchronized (mValue) {
			mValue[0] = -mValue[0];
			mValue[1] = -mValue[1];
		}
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public float getScalar()
	{
		float scalar;
		synchronized (mValue) {
			scalar = (float)Math.sqrt(mValue[0]*mValue[0]+mValue[1]*mValue[1]);
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
			}
		}
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return getX() + " " + getY();
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		SFVec2f field = new SFVec2f();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}