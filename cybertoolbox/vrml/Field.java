/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Field.java
*
******************************************************************/

package vrml;

public abstract class Field implements Cloneable, Constants {

	private String	mName = null;
	private int		mType = 0;

	public Field() {
	}

	public Field(Field field) {
		setObject(field.getObject());
	}
	
	public Field(ConstField constField) {
		setObject(constField.getObject());
	}

	public void setType(int type) {
		mType = type;
	}

	public void setType(String type) {
		if (type.equals("SFBool"))
			setType(fieldTypeSFBool);
		else if (type.equals("SFColor"))
			setType(fieldTypeSFColor);
		else if (type.equals("SFFloat"))
			setType(fieldTypeSFFloat);
		else if (type.equals("SFInt32"))
			setType(fieldTypeSFInt32);
		else if (type.equals("SFRotation"))
			setType(fieldTypeSFRotation);
		else if (type.equals("SFString"))
			setType(fieldTypeSFString);
		else if (type.equals("SFTime"))
			setType(fieldTypeSFTime);
		else if (type.equals("SFVec2f"))
			setType(fieldTypeSFVec2f);
		else if (type.equals("SFVec3f"))
			setType(fieldTypeSFVec3f);
		else if (type.equals("SFNode"))
			setType(fieldTypeSFNode);
		else if (type.equals("MFBool"))
			setType(fieldTypeMFBool);
		else if (type.equals("MFColor"))
			setType(fieldTypeMFColor);
		else if (type.equals("MFFloat"))
			setType(fieldTypeMFFloat);
		else if (type.equals("MFInt32"))
			setType(fieldTypeMFInt32);
		else if (type.equals("MFRotation"))
			setType(fieldTypeMFRotation);
		else if (type.equals("MFString"))
			setType(fieldTypeMFString);
		else if (type.equals("MFTime"))
			setType(fieldTypeMFTime);
		else if (type.equals("MFVec2f"))
			setType(fieldTypeMFVec2f);
		else if (type.equals("MFVec3f"))
			setType(fieldTypeMFVec3f);
		else if (type.equals("MFNode"))
			setType(fieldTypeMFNode);
		else if (type.equals("ConstSFBool"))
			setType(fieldTypeConstSFBool);
		else if (type.equals("ConstSFColor"))
			setType(fieldTypeConstSFColor);
		else if (type.equals("ConstSFFloat"))
			setType(fieldTypeConstSFFloat);
		else if (type.equals("ConstSFInt32"))
			setType(fieldTypeConstSFInt32);
		else if (type.equals("ConstSFRotation"))
			setType(fieldTypeConstSFRotation);
		else if (type.equals("ConstSFString"))
			setType(fieldTypeConstSFString);
		else if (type.equals("ConstSFTime"))
			setType(fieldTypeConstSFTime);
		else if (type.equals("ConstSFVec2f"))
			setType(fieldTypeConstSFVec2f);
		else if (type.equals("ConstSFVec3f"))
			setType(fieldTypeConstSFVec3f);
		else if (type.equals("ConstSFNode"))
			setType(fieldTypeConstSFNode);
		else if (type.equals("ConstMFBool"))
			setType(fieldTypeConstMFBool);
		else if (type.equals("ConstMFColor"))
			setType(fieldTypeConstMFColor);
		else if (type.equals("ConstMFFloat"))
			setType(fieldTypeConstMFFloat);
		else if (type.equals("ConstMFInt32"))
			setType(fieldTypeConstMFInt32);
		else if (type.equals("ConstMFRotation"))
			setType(fieldTypeConstMFRotation);
		else if (type.equals("ConstMFString"))
			setType(fieldTypeConstMFString);
		else if (type.equals("ConstMFTime"))
			setType(fieldTypeConstMFTime);
		else if (type.equals("ConstMFVec2f"))
			setType(fieldTypeConstMFVec2f);
		else if (type.equals("ConstMFVec3f"))
			setType(fieldTypeConstMFVec3f);
		else if (type.equals("ConstMFNode"))
			setType(fieldTypeConstMFNode);
		else
			setType(fieldTypeNone);
	}

	public int getType() {
		return mType;
	}

	public String getTypeName() {
		switch (getType()) {
		// Field
		case fieldTypeSFBool		: return "SFBool";
		case fieldTypeSFColor		: return "SFColor";
		case fieldTypeSFFloat		: return "SFFloat";
		case fieldTypeSFInt32		: return "SFInt32";
		case fieldTypeSFRotation	: return "SFRotation";
		case fieldTypeSFString		: return "SFString";
		case fieldTypeSFTime		: return "SFTime";
	  	case fieldTypeSFVec2f		: return "SFVec2f";
	  	case fieldTypeSFVec3f		: return "SFVec3f";
	  	case fieldTypeSFNode		: return "SFNode";
		case fieldTypeMFBool		: return "MFBool";
		case fieldTypeMFColor		: return "MFColor";
		case fieldTypeMFFloat		: return "MFFloat";
		case fieldTypeMFInt32		: return "MFInt32";
		case fieldTypeMFRotation	: return "MFRotation";
		case fieldTypeMFString		: return "MFString";
		case fieldTypeMFTime		: return "MFTime";
	  	case fieldTypeMFVec2f		: return "MFVec2f";
	  	case fieldTypeMFVec3f		: return "MFVec3f";
	  	case fieldTypeMFNode		: return "MFNode";
		// ConstField
		case fieldTypeConstSFBool		: return "ConstSFBool";
		case fieldTypeConstSFColor		: return "ConstSFColor";
		case fieldTypeConstSFFloat		: return "ConstSFFloat";
		case fieldTypeConstSFInt32		: return "ConstSFInt32";
		case fieldTypeConstSFRotation	: return "ConstSFRotation";
		case fieldTypeConstSFString		: return "ConstSFString";
		case fieldTypeConstSFTime		: return "ConstSFTime";
	  	case fieldTypeConstSFVec2f		: return "ConstSFVec2f";
	  	case fieldTypeConstSFVec3f		: return "ConstSFVec3f";
	  	case fieldTypeConstSFNode		: return "ConstSFNode";
		case fieldTypeConstMFBool		: return "ConstMFBool";
		case fieldTypeConstMFColor		: return "ConstMFColor";
		case fieldTypeConstMFFloat		: return "ConstMFFloat";
		case fieldTypeConstMFInt32		: return "ConstMFInt32";
		case fieldTypeConstMFRotation	: return "ConstMFRotation";
		case fieldTypeConstMFString		: return "ConstMFString";
		case fieldTypeConstMFTime		: return "ConstMFTime";
	  	case fieldTypeConstMFVec2f		: return "ConstMFVec2f";
	  	case fieldTypeConstMFVec3f		: return "ConstMFVec3f";
	  	case fieldTypeConstMFNode		: return "ConstMFNode";
		}
		return null;
	}

	public boolean isSameType(Field field) {
		return (getType() == field.getType() ? true : false);
	}

	public boolean isSameValueType(Field field) {
		return ((getType() & field.getType()) != 0 ? true : false);
	}
	
	public boolean isSingleField() {
		int type = getType();
		
		if (type == fieldTypeSFBool)
			return true;
		if (type == fieldTypeSFColor)
			return true;
		if (type == fieldTypeSFFloat)
			return true;
		if (type == fieldTypeSFInt32)
			return true;
		if (type == fieldTypeSFRotation)
			return true;
		if (type == fieldTypeSFString)
			return true;
		if (type == fieldTypeSFTime)
			return true;
		if (type == fieldTypeSFVec2f)
			return true;
		if (type == fieldTypeSFVec3f)
			return true;
		if (type == fieldTypeSFNode)
			return true;
		if (type == fieldTypeSFImage)
			return true;

		if (type == fieldTypeConstSFBool)
			return true;
		if (type == fieldTypeConstSFColor)
			return true;
		if (type == fieldTypeConstSFFloat)
			return true;
		if (type == fieldTypeConstSFInt32)
			return true;
		if (type == fieldTypeConstSFRotation)
			return true;
		if (type == fieldTypeConstSFString)
			return true;
		if (type == fieldTypeConstSFTime)
			return true;
		if (type == fieldTypeConstSFVec2f)
			return true;
		if (type == fieldTypeConstSFVec3f)
			return true;
		if (type == fieldTypeConstSFNode)
			return true;
		if (type == fieldTypeConstSFImage)
			return true;

		return false;	
	}

	public boolean isMultiField() {
		return !isSingleField();
	}
		
	public void setName(String name) {
		mName = name;	
	}

	public String getName() {
		return mName;
	}

	abstract public void setValue(String value);
	abstract public String toString();

	abstract public void setObject(Object obj);
	abstract public Object getObject();
	
	public Object clone() {
		return this.clone();
	}
}
