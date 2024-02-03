/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFColor.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class SFColor extends Field {

	private float mValue[] = new float[3]; 

	public SFColor() {
		setType(fieldTypeSFColor);
		setValue(1.0f, 1.0f, 1.0f);
	}

	public SFColor(SFColor color) {
		setType(fieldTypeSFColor);
		setValue(color);
	}

	public SFColor(ConstSFColor color) {
		setType(fieldTypeSFColor);
		setValue(color);
	}

	public SFColor(float r, float g, float b) {
		setType(fieldTypeSFColor);
		setValue(r, g, b);
	}

	public SFColor(float value[]) {
		setType(fieldTypeSFColor);
		setValue(value);
	}

	public SFColor(String value) {
		setType(fieldTypeSFColor);
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

	public float getRed() {
		float value;
		synchronized (mValue) {
			value = mValue[0];
		}
		return value;
	}

	public float getGreen() {
		float value;
		synchronized (mValue) {
			value = mValue[1];
		}
		return value;
	}

	public float getBlue() {
		float value;
		synchronized (mValue) {
			value = mValue[2];
		}
		return value;
	}

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setValue(float r, float g, float b) {
		synchronized (mValue) {
			mValue[0] = r;
			mValue[1] = g;
			mValue[2] = b;
		}
	}

	public void setValue(float value[]) {
		synchronized (mValue) {
			mValue[0] = value[0];
			mValue[1] = value[1];
			mValue[2] = value[2];
		}
	}

	public void setValue(SFColor color) {
		setValue(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void setValue(ConstSFColor color) {
		setValue(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void setValue(String string) {
		StringValue value = new StringValue(string);
		String token[] = value.getTokens();
		if (token != null) {
			if (token.length == 3) {
				try {
					Float r = new Float(token[0]);
					Float g = new Float(token[1]);
					Float b = new Float(token[2]);
					setValue(r.floatValue(), g.floatValue(), b.floatValue());
				}
				catch (NumberFormatException e) {}
			}
		}
	}
	
	////////////////////////////////////////////////
	//	add value
	////////////////////////////////////////////////

	public void add(float x, float y, float z) {
		synchronized (mValue) {
			mValue[0] += x;
			mValue[1] += y;
			mValue[2] += z;
			mValue[0] /= 2.0f;
			mValue[1] /= 2.0f;
			mValue[2] /= 2.0f;
		}
		check();
	}

	public void add(float value[]) {
		add(value[0], value[1], value[2]);
	}

	public void add(SFColor value) {
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
			mValue[0] /= 2.0f;
			mValue[1] /= 2.0f;
			mValue[2] /= 2.0f;
		}
		check();
	}

	public void sub(float value[]) {
		sub(value[0], value[1], value[2]);
	}

	public void sub(SFColor value) {
		sub(value.getValue());
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		String string;
		synchronized (mValue) {
			string = mValue[0] + " " + mValue[1] + " " + mValue[2];
		}
		return string;
	}

	////////////////////////////////////////////////
	//	scale
	////////////////////////////////////////////////

	public void scale(float scale) {
		synchronized (mValue) {
			for (int n=0; n<3; n++) 
				mValue[n] *= scale;
		}
		check();
	}

	////////////////////////////////////////////////
	//	check
	////////////////////////////////////////////////

	public void check() {
		synchronized (mValue) {
			for (int n=0; n<3; n++) {
				if (1.0f < mValue[n])
					mValue[n] = 1.0f;
				if (mValue[n] < 0.0f)
					mValue[n] = 0.0f;
			}
		}
	}

}